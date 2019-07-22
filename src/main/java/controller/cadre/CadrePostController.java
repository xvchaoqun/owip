package controller.cadre;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.CadrePost;
import domain.cadre.CadrePostExample;
import domain.cadre.CadreView;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreRelate;
import domain.sys.SysUserView;
import domain.unit.Unit;
import domain.unit.UnitPost;
import domain.unit.UnitPostView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.dispatch.DispatchCadreRelateService;
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.ExcelUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CadrePostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 主职、兼职、任职级经历 放在同一个页面
    @RequiresPermissions("cadrePost:list")
    @RequestMapping("/cadrePost_page")
    public String cadrePost_page(HttpServletResponse response,
                                 @RequestParam(defaultValue = "1") Byte type, // “1 任现职情况”和“2 任职经历”
                                 Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 1) {

            // 兼职
            modelMap.put("subCadrePosts", cadrePostService.getSubCadrePosts(cadreId));
        }
        if(type==3){
            modelMap.put("cadre", iCadreMapper.getCadre(cadreId));
            // 任职级经历
            modelMap.put("cadreAdminLevels", cadreAdminLevelService.getCadreAdminLevels(cadreId));
        }
        return "cadre/cadrePost/cadrePost_page";
    }

    @RequiresPermissions("cadrePost:list")
    @RequestMapping("/cadrePost_data")
    public void cadrePost_data(HttpServletResponse response,
                                    int cadreId,
                                    @RequestParam(required = false, defaultValue = "0") Boolean isMainPost,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePostExample example = new CadrePostExample();
        example.createCriteria()
                .andCadreIdEqualTo(cadreId).andIsMainPostEqualTo(isMainPost);
        example.setOrderByClause("is_first_main_post desc, sort_order desc");

        long count = cadrePostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePost> CadrePosts = cadrePostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", CadrePosts);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, baseMixins);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;

    }
    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadrePost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_au(CadrePost record,
                               Boolean isCpc,
                               HttpServletRequest request) {

        Integer id = record.getId();

        if(record.getUnitPostId()!=null) {
            CadrePost byUnitPostId = cadrePostService.getByUnitPostId(record.getUnitPostId());
            if(byUnitPostId!=null && (id==null || id!=byUnitPostId.getId().intValue())){
                return failed("岗位已被{0}({1})使用。",
                        byUnitPostId.getCadre().getRealname(),
                        byUnitPostId.getIsMainPost()?"主职":"兼职");
            }
        }

        if(BooleanUtils.isNotTrue(record.getIsMainPost()))
            record.setIsCpc(BooleanUtils.isTrue(isCpc));

        record.setIsMainPost(BooleanUtils.isTrue(record.getIsMainPost()));
        record.setIsFirstMainPost(BooleanUtils.isTrue(record.getIsFirstMainPost()));

        if (id == null) {
            cadrePostService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加现任职务：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));
        } else {
            cadrePostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新现任职务：%s", JSONUtils.toString(record, MixinUtils.baseMixins(), false)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadrePost_au")
    public String cadrePost_au(Integer id,
                               @RequestParam(defaultValue = "0") boolean isMainPost,
                               int cadreId, ModelMap modelMap) {

        if(isMainPost){
            // 第一主职
            modelMap.put("mainCadrePost", cadrePostService.getCadreMainCadrePost(cadreId));
        }

        if (id != null) {
            CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePost", cadrePost);

            if(cadrePost!=null && cadrePost.getUnitPostId()!=null) {
                UnitPostView unitPost = iUnitMapper.getUnitPost(cadrePost.getUnitPostId());
                modelMap.put("unitPost", unitPost);
            }

            modelMap.put("unit", unitService.findAll().get(cadrePost.getUnitId()));
        }
        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);

        return isMainPost?"cadre/cadrePost/mainCadrePost_au":"cadre/cadrePost/subCadrePost_au";
    }

/*    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadrePost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            cadrePostMapper.deleteByPrimaryKey(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除现任职务：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadrePost:del")
    @RequestMapping(value = "/cadrePost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cadrePostService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除现任职务：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping("/cadrePost_addDispatchs")
    public String cadrePost_addDispatchs(HttpServletResponse response,
                                         String type,
                                         int id, int cadreId, ModelMap modelMap) {


        Set<Integer> dispatchCadreIdSet = new HashSet<>(); // 已关联的干部任免文件ID
        List<DispatchCadre> relateDispatchCadres = new ArrayList<>(); // 已关联的干部任免文件

        DispatchCadreRelateService dispatchCadreRelateService = CmTag.getBean(DispatchCadreRelateService.class);
        List<DispatchCadreRelate> dispatchCadreRelates =
                dispatchCadreRelateService.findDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            Integer dispatchCadreId = dispatchCadreRelate.getDispatchCadreId();
            DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
            dispatchCadreIdSet.add(dispatchCadreId);
            relateDispatchCadres.add(dispatchCadre);
        }

        modelMap.put("dispatchCadreIdSet", dispatchCadreIdSet);

        if (relateDispatchCadres.size() == 0 || StringUtils.equalsIgnoreCase(type, "edit")) {
            modelMap.put("type", "edit");

            List<DispatchCadre> dispatchCadres = iDispatchMapper.selectDispatchCadreList(cadreId, DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT);
            modelMap.put("dispatchCadres", dispatchCadres);

            // 去掉：主职和兼职的任免文件改为可以重复，因为有时主职和兼职是放在一个文件中的
            //Set<Integer> otherDispatchCadreRelateSet = dispatchCadreRelateService.findOtherDispatchCadreRelateSet(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
            //modelMap.put("otherDispatchCadreRelateSet", otherDispatchCadreRelateSet);
        } else {
            modelMap.put("type", "add");
            modelMap.put("dispatchCadres", relateDispatchCadres);
        }

        return "cadre/cadrePost/cadrePost_addDispatchs";
    }

    @RequiresPermissions("cadrePost:edit")
    @RequestMapping(value = "/cadrePost_addDispatchs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_addDispatchs(HttpServletRequest request,
                                         int id,
                                         @RequestParam(required = false, value = "ids[]") Integer[] ids, ModelMap modelMap) {

        DispatchCadreRelateService dispatchCadreRelateService = CmTag.getBean(DispatchCadreRelateService.class);
        dispatchCadreRelateService.updateDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST, ids);
        logger.info(addLog(LogConstants.LOG_ADMIN, "修改现任职务%s-关联发文：%s", id, StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:changeOrder")
    @RequestMapping(value = "/cadrePost_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadrePostService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部职务调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePost:import")
    @RequestMapping("/cadrePost_import")
    public String cadrePost_import(Boolean isMainPost, ModelMap modelMap) {

        modelMap.put("isMainPost", isMainPost);
        return "cadre/cadrePost/cadrePost_import";
    }

    @RequiresPermissions("cadrePost:import")
    @RequestMapping(value = "/cadrePost_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePost_import(HttpServletRequest request, Boolean isMainPost) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CadrePost> records = new ArrayList<>();
        int row = 1;
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        if(BooleanUtils.isTrue(isMainPost)) {
            for (Map<Integer, String> xlsRow : xlsRows) {

                row++;
                CadrePost record = new CadrePost();

                String userCode = StringUtils.trim(xlsRow.get(0));
                if (StringUtils.isBlank(userCode)) {
                    throw new OpException("第{0}行工作证号为空", row);
                }
                SysUserView uv = sysUserService.findByCode(userCode);
                if (uv == null) {
                    throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
                }
                int userId = uv.getId();
                CadreView cv = cadreService.dbFindByUserId(userId);
                if (cv == null) {
                    throw new OpException("第{0}行工作证号[{1}]不在干部库中", row, userCode);
                }
                record.setCadreId(cv.getId());

                UnitPost unitPost = null;
                String postCode = StringUtils.trimToNull(xlsRow.get(3));
                if (StringUtils.isNotBlank(postCode)) {

                    unitPost = unitPostService.getByCode(postCode);
                    if (unitPost == null) {
                        throw new OpException("第{0}行关联岗位编码[{1}]不存在", row, postCode);
                    }

                    CadrePost byUnitPostId = cadrePostService.getByUnitPostId(unitPost.getId());
                    if (byUnitPostId != null && byUnitPostId.getCadreId().intValue() != cv.getId()) {
                        return failed("第{0}行关联岗位编码[{1}]已被{2}({3})使用。",
                                row, postCode,
                                byUnitPostId.getCadre().getRealname(),
                                byUnitPostId.getIsMainPost() ? "主职" : "兼职");
                    }

                    record.setUnitPostId(unitPost.getId());
                    record.setPostName(unitPost.getName());
                    record.setAdminLevel(unitPost.getAdminLevel());
                    record.setPostType(unitPost.getPostType());
                    record.setPostClassId(unitPost.getPostClass());
                    record.setUnitId(unitPost.getUnitId());
                }

                if(unitPost==null) {
                    String post = StringUtils.trimToNull(xlsRow.get(4));
                    if (StringUtils.isBlank(post)) {
                        throw new OpException("第{0}行岗位名称为空", row);
                    }
                    record.setPost(post);

                    String _postType = StringUtils.trimToNull(xlsRow.get(5));
                    MetaType postType = CmTag.getMetaTypeByName("mc_post", _postType);
                    if (postType == null && record.getPostType() == null) {
                        throw new OpException("第{0}行职务属性[{1}]不存在", row, _postType);
                    } else if (postType != null) {
                        record.setPostType(postType.getId());
                    }

                    String _postClass = StringUtils.trimToNull(xlsRow.get(7));
                    MetaType postClass = CmTag.getMetaTypeByName("mc_post_class", _postClass);
                    if (postClass == null && record.getPostClassId() == null) {
                        throw new OpException("第{0}行职务类别[{1}]不存在", row, _postClass);
                    } else if (postClass != null) {
                        record.setPostClassId(postClass.getId());
                    }

                    String unitCode = StringUtils.trimToNull(xlsRow.get(9));
                    if (StringUtils.isBlank(unitCode)) {
                        throw new OpException("第{0}行单位编码为空", row);
                    }
                    Unit unit = unitService.findUnitByCode(unitCode);
                    if (unit == null) {
                        throw new OpException("第{0}行单位编码[{1}]不存在", row, unitCode);
                    }
                    record.setUnitId(unit.getId());
                }

                String _adminLevel = StringUtils.trimToNull(xlsRow.get(6));
                MetaType adminLevel = CmTag.getMetaTypeByName("mc_admin_level", _adminLevel);
                if(unitPost==null && adminLevel==null){
                    throw new OpException("第{0}行行政级别[{1}]不存在", row, _adminLevel);
                }else if (adminLevel != null ) {
                    record.setAdminLevel(adminLevel.getId());
                }

                record.setPost(StringUtils.trimToNull(xlsRow.get(10)));
                record.setIsFirstMainPost(StringUtils.equals(StringUtils.trimToNull(xlsRow.get(11)), "是"));

                record.setIsMainPost(true);
                records.add(record);
            }

            int addCount = cadrePostService.batchImportMainPosts(records);
            int totalCount = records.size();
            resultMap.put("successCount", addCount);
            resultMap.put("total", totalCount);

            logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部主职成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));
        }else{

            for (Map<Integer, String> xlsRow : xlsRows) {

                row++;
                CadrePost record = new CadrePost();

                String userCode = StringUtils.trim(xlsRow.get(0));
                if (StringUtils.isBlank(userCode)) {
                    throw new OpException("第{0}行工作证号为空", row);
                }
                SysUserView uv = sysUserService.findByCode(userCode);
                if (uv == null) {
                    throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
                }
                int userId = uv.getId();
                CadreView cv = cadreService.dbFindByUserId(userId);
                if (cv == null) {
                    throw new OpException("第{0}行工作证号[{1}]不在干部库中", row, userCode);
                }
                record.setCadreId(cv.getId());

                UnitPost unitPost = null;
                String postCode = StringUtils.trimToNull(xlsRow.get(3));
                if (StringUtils.isNotBlank(postCode)) {

                    unitPost = unitPostService.getByCode(postCode);
                    if (unitPost == null) {
                        throw new OpException("第{0}行关联岗位编码[{1}]不存在", row, postCode);
                    }

                    CadrePost byUnitPostId = cadrePostService.getByUnitPostId(unitPost.getId());
                    if (byUnitPostId != null && byUnitPostId.getCadreId().intValue() != cv.getId()) {
                        return failed("第{0}行关联岗位编码[{1}]已被{2}({3})使用。",
                                row, postCode,
                                byUnitPostId.getCadre().getRealname(),
                                byUnitPostId.getIsMainPost() ? "主职" : "兼职");
                    }

                    record.setUnitPostId(unitPost.getId());
                    record.setPostName(unitPost.getName());
                    record.setPostType(unitPost.getPostType());
                    record.setAdminLevel(unitPost.getAdminLevel());
                    record.setPostClassId(unitPost.getPostClass());
                    record.setUnitId(unitPost.getUnitId());
                }

                if(unitPost==null) {
                    String post = StringUtils.trimToNull(xlsRow.get(4));
                    if (StringUtils.isBlank(post)) {
                        throw new OpException("第{0}行岗位名称为空", row);
                    }
                    record.setPost(post);

                    String _postType = StringUtils.trimToNull(xlsRow.get(5));
                    MetaType postType = CmTag.getMetaTypeByName("mc_post", _postType);
                    if (postType == null && record.getPostType() == null) {
                        throw new OpException("第{0}行职务属性[{1}]不存在", row, _postType);
                    } else if (postType != null) {
                        record.setPostType(postType.getId());
                    }

                    String _postClass = StringUtils.trimToNull(xlsRow.get(7));
                    MetaType postClass = CmTag.getMetaTypeByName("mc_post_class", _postClass);
                    if (postClass == null && record.getPostClassId() == null) {
                        throw new OpException("第{0}行职务类别[{1}]不存在", row, _postClass);
                    } else if (postClass != null) {
                        record.setPostClassId(postClass.getId());
                    }

                    String unitCode = StringUtils.trimToNull(xlsRow.get(9));
                    if (StringUtils.isBlank(unitCode)) {
                        throw new OpException("第{0}行兼任单位编码为空", row);
                    }
                    Unit unit = unitService.findUnitByCode(unitCode);
                    if (unit == null) {
                        throw new OpException("第{0}行兼任单位编码[{1}]不存在", row, unitCode);
                    }
                    record.setUnitId(unit.getId());
                }

                String _adminLevel = StringUtils.trimToNull(xlsRow.get(6));
                MetaType adminLevel = CmTag.getMetaTypeByName("mc_admin_level", _adminLevel);
                if(unitPost==null && adminLevel==null){
                    throw new OpException("第{0}行行政级别[{1}]不存在", row, _adminLevel);
                }else if (adminLevel != null ) {
                    record.setAdminLevel(adminLevel.getId());
                }

                String _isCpc = StringUtils.trimToNull(xlsRow.get(10));
                record.setIsCpc(StringUtils.equals(_isCpc, "是"));

                record.setIsMainPost(false);
                records.add(record);
            }

            int addCount = cadrePostService.batchImportSubPosts(records);
            int totalCount = records.size();
            resultMap.put("successCount", addCount);
            resultMap.put("total", totalCount);

            logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部兼职成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));
        }

        return resultMap;
    }
}
