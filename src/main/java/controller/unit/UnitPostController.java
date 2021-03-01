package controller.unit;

import bean.MetaClassOption;
import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.sys.SysUserView;
import domain.unit.*;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.unit.UnitPostAllocationInfoBean;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.spring.UserResUtils;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static domain.unit.UnitPostViewExample.Criteria;
import static sys.constants.CadreConstants.CADRE_STATUS_CJ;
import static sys.constants.CadreConstants.CADRE_STATUS_KJ;
import static sys.constants.DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT;
import static sys.constants.SystemConstants.*;

@Controller
public class UnitPostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 历史任职干部
    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPost_cadres")
    public String unitPost_cadres(int unitPostId,
                                  Integer cadreId,
                                  Integer groupId,
                                  @RequestParam(required = false, defaultValue = DISPATCH_CADRE_TYPE_APPOINT+"") Byte type,
                                  Byte displayType,
                                  ModelMap modelMap) {
        UnitPost unitPost=unitPostMapper.selectByPrimaryKey(unitPostId);
        modelMap.put("unitPost", unitPost);

        if(cadreId!=null){
            modelMap.put("cadre", CmTag.getCadreById(cadreId));
        }

        if(groupId!=null){
            UnitPostGroup unitPostGroup = unitPostGroupMapper.selectByPrimaryKey(groupId);
            modelMap.put("unitPostGroup", unitPostGroup);
        }

        if(displayType==null){
            if(unitPost.getGroupId()!=null){
                displayType = 0; // 默认按岗位分组搜索
            }else{
                displayType = 1; // 默认按岗位名称搜索
            }
        }

        modelMap.put("type", type);
        modelMap.put("displayType", displayType);
        return "unit/unitPost/unitPost_cadres";
    }

    @RequiresPermissions("unitPost:import")
    @RequestMapping("/unitPost_import")
    public String unitPost_import(ModelMap modelMap) {

        return "unit/unitPost/unitPost_import";
    }

    @RequiresPermissions("unitPost:import")
    @RequestMapping(value = "/unitPost_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<UnitPost> records = new ArrayList<>();
        int row = 1;
        int addCount = 0;
        int totalCount = 0;
        for (Map<Integer, String> xlsRow : xlsRows) {

            UnitPost record = new UnitPost();
            CadrePost cadrePost = new CadrePost();
            row++;
            String code = StringUtils.trimToNull(xlsRow.get(0));
            String unitCode = StringUtils.trimToNull(xlsRow.get(2));
            if(StringUtils.isBlank(unitCode)){
                throw new OpException("第{0}行单位编码为空", row);
            }
            Unit unit = unitService.findRunUnitByCode(unitCode);
            if(unit==null){
                throw new OpException("第{0}行单位编码[{1}]不存在", row, unitCode);
            }
            if(StringUtils.isBlank(code)){
                code = unitPostService.generateCode(unitCode);
                //throw new OpException("第{0}行岗位编号为空", row);
            }
            record.setCode(code);

            String name = StringUtils.trimToNull(xlsRow.get(1));
             if(StringUtils.isBlank(name)){
                throw new OpException("第{0}行岗位名称为空", row);
            }
            record.setName(name);
            record.setUnitId(unit.getId());

            record.setJob(StringUtils.trimToNull(xlsRow.get(3)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(15)));
            record.setIsPrincipal(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(4)), "是"));
            record.setIsCpc(StringUtils.equalsIgnoreCase(StringUtils.trimToNull(xlsRow.get(8)), "是"));

            String adminLevel = StringUtils.trimToNull(xlsRow.get(5));
            MetaType adminLevelType = CmTag.getMetaTypeByName("mc_admin_level", adminLevel);
            if (adminLevelType == null) throw new OpException("第{0}行行政级别[{1}]不存在", row, adminLevel);
            record.setAdminLevel(adminLevelType.getId());

            String _postType = StringUtils.trimToNull(xlsRow.get(6));
            MetaType postType = CmTag.getMetaTypeByName("mc_post", _postType);
            if (postType == null)throw new OpException("第{0}行职务属性[{1}]不存在", row, _postType);
            record.setPostType(postType.getId());

            String postClass = StringUtils.trimToNull(xlsRow.get(7));
            MetaType postClassType = CmTag.getMetaTypeByName("mc_post_class", postClass);
            if (postClassType == null)throw new OpException("第{0}行职务类别[{1}]不存在", row, postClass);
            record.setPostClass(postClassType.getId());

            String userCode = StringUtils.trim(xlsRow.get(9));
            String userName = StringUtils.trim(xlsRow.get(10));
            if(StringUtils.isNotBlank(userCode)){
                SysUserView uv = sysUserService.findByCode(userCode);
                CadreView cadre = cadreService.dbFindByUserId(uv.getId());
                if (cadre != null) {
                    int cadreId = cadre.getId();
                    cadrePost.setCadreId(cadreId);
                }
            }else if(StringUtils.isNotBlank(userName)){
                    CadreViewExample example = new CadreViewExample();
                    CadreViewExample.Criteria criteria = example.createCriteria();
                    criteria.andRealnameEqualTo(userName);
                    List<CadreView> cvs = cadreViewMapper.selectByExample(example);
                    if (cvs.size()==1){
                        int cadreId = cvs.get(0).getId();
                        cadrePost.setCadreId(cadreId);
                    }
            }
            if (cadrePost.getCadreId() != null) {
                String _adminLevel = StringUtils.trimToNull(xlsRow.get(11));
                MetaType _adminLevelType = CmTag.getMetaTypeByName("mc_admin_level", _adminLevel);
                if (_adminLevelType == null) throw new OpException("第{0}行干部级别[{1}]不存在", row, _adminLevel);
                cadrePost.setAdminLevel(_adminLevelType.getId());

                String isMainPost = StringUtils.trim(xlsRow.get(12));
                if(StringUtils.isBlank(isMainPost)){
                    throw new OpException("第{0}行任职类型为空", row);
                }
                if(isMainPost.equals("第一主职")){
                    cadrePost.setIsMainPost(true);
                    cadrePost.setIsFirstMainPost(true);
                }else if(isMainPost.equals("主职")){
                    cadrePost.setIsMainPost(true);
                    cadrePost.setIsFirstMainPost(false);
                } else if(isMainPost.equals("兼职")){
                    cadrePost.setIsMainPost(false);
                    cadrePost.setIsFirstMainPost(false);
                }

                Date lpWorkTime = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(13)));
                cadrePost.setLpWorkTime(lpWorkTime);
                Date npWorkTime = DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(14)));
                cadrePost.setNpWorkTime(npWorkTime);
            }
            record.setStatus(SystemConstants.UNIT_POST_STATUS_NORMAL);
            addCount += unitPostService.singleImport(record,cadrePost);
            totalCount++;
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部岗位成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequiresPermissions(value={"unitPost:list", "unitPost:allocation"}, logical = Logical.OR)
    @RequestMapping("/unitPosts")
    public String unitPosts(int unitId, int adminLevel, Boolean displayEmpty, ModelMap modelMap) {

        List<UnitPostView> unitPosts = unitPostService.query(unitId, adminLevel, displayEmpty);
        modelMap.put("unitPosts", unitPosts);

        return "unit/unitPost/unitPosts";
    }

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPostList")
    public String unitPostList(@RequestParam(required = false, defaultValue = "1")Byte cls,
                               Integer[] unitTypes,
                               Byte displayType,
                               Integer startNowPostAge,
                               Integer endNowPostAge,
                               ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(displayType!=null) {

            if (unitTypes != null) {
                modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
            }

            if (displayType == 1) {
                // 待补充的岗位列表
                return "unit/unitPost/unitPost_openList";
            } else if (displayType == 2) {
                // 待调整的岗位列表
                return "unit/unitPost/unitPost_openList";
            } else if (displayType == 3) {

                if(startNowPostAge==null && endNowPostAge!=null) startNowPostAge = 0;
                else if(startNowPostAge==null) startNowPostAge = 8;

                modelMap.put("startNowPostAge", startNowPostAge);
                // 超任职年限的干部列表
                return "unit/unitPost/unitPost_cadreList";
            }
        }

        return "unit/unitPost/unitPostList_page";
    }

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPost")
    public String unitPost(@RequestParam(required = false, defaultValue = "1")Byte cls,
                           Integer[] unitTypes,
                           Integer[] adminLevels,
                           Integer cadreId,
                           Integer unitId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(cls==3){
            return "forward:/unitPostGroup";
        }
        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
        }

        if (unitTypes != null) {
            modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
        }
        if (adminLevels != null) {
            modelMap.put("selectAdminLevels", Arrays.asList(adminLevels));
        }

        if(unitId!=null){
            Unit unit = unitService.findAll().get(unitId);
            modelMap.put("unit", unit);
        }

        return "unit/unitPost/unitPost_page";
    }

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPost_data")
    @ResponseBody
    public void unitPost_data(HttpServletResponse response,
                              @RequestParam(required = false, defaultValue = "1")Byte cls,
                                    Integer unitId,
                                    String code,
                                    String name,
                                    Integer cpAdminLevel,
                                    Integer adminLevel,
                                    Integer postType,
                                    Integer postClass,
                              Boolean isPrincipal,
                              Byte leaderType,
                              Boolean isCpc,
                              Boolean isMainPost,
                              // 1: 显示空缺岗位 2: 显示占干部职数的兼职岗位 3: 超任职年限的干部列表
                              Byte displayType,
                              Integer cadreId,
                               Byte gender,
                               Boolean cadreIsPrincipal,
                              Integer cadrePostType,
                              Integer startNowPostAge,
                              Integer endNowPostAge,
                              Integer startNowLevelAge,
                              Integer endNowLevelAge,
                              String sortBy,//自定义排序
                              Integer[] unitTypes, // 部门属性
                              Integer[] adminLevels, // 行政级别
                                Boolean cpIsCpc,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, defaultValue = "0") int exportType,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitPostViewExample example = new UnitPostViewExample();
        Criteria criteria = example.createCriteria();
        String sortStr = "unit_sort_order asc, sort_order asc";
        if (displayType!=null && displayType == 3) {
            if (StringUtils.isNotBlank(sortBy)) {
                switch (sortBy.trim()) {
                    case "npWorkTime_asc":
                        sortStr = "np_work_time asc";
                        break;
                    case "npWorkTime_desc":
                        sortStr = "np_work_time desc";
                        break;
                    case "sWorkTime_asc":
                        sortStr = "s_work_time asc";
                        break;
                    case "sWorkTime_desc":
                        sortStr = "s_work_time desc";
                        break;
                }
            }
        }
        example.setOrderByClause(sortStr);
        if(cls==1){
            criteria.andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL);
        }else if(cls==2){
            criteria.andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_ABOLISH);
        }else if(cls==3){
            criteria.andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_DELETE);
        }else{
            criteria.andIdIsNull();
        }

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (cpAdminLevel!=null) {
            criteria.andCpAdminLevelEqualTo(cpAdminLevel);
        }
        if (adminLevel != null) {
            criteria.andAdminLevelEqualTo(adminLevel);
        }
        if (postType!=null) {
            criteria.andPostTypeEqualTo(postType);
        }
        if (postClass!=null) {
            criteria.andPostClassEqualTo(postClass);
        }

        if (isPrincipal!=null) {
            criteria.andIsPrincipalEqualTo(isPrincipal);
        }
        if(leaderType!=null){
            criteria.andLeaderTypeEqualTo(leaderType);
        }
        if (isCpc!=null) {
            criteria.andIsCpcEqualTo(isCpc);
        }

        if(displayType!=null){
            if(displayType==1) {
                criteria.andCadreIdIsNull();
            }else if(displayType==2){
                criteria.andIsMainPostEqualTo(false);
                criteria.andIsCpcEqualTo(true);
            }else if(displayType==3){
                criteria.andCadreIdIsNotNull();
            }
        }

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if(gender!=null){
            criteria.andGenderEqualTo(gender);
        }
        if (cadrePostType!=null) {
            criteria.andCadrePostTypeEqualTo(cadrePostType);
        }
        if (cadreIsPrincipal!=null) {
            criteria.andCadreIsPrincipalEqualTo(cadreIsPrincipal);
        }

        // 搜索始任年限时只考虑主职
        if (endNowPostAge != null) {
            criteria.andCadrePostYearLessThanOrEqualTo(endNowPostAge);
            isMainPost = true;
        }
        if (startNowPostAge != null) {
            criteria.andCadrePostYearGreaterThanOrEqualTo(startNowPostAge);
            isMainPost = true;
        }
        if (endNowLevelAge != null) {
            criteria.andAdminLevelYearLessThanOrEqualTo(endNowLevelAge);
            isMainPost = true;
        }
        if (startNowLevelAge != null) {
            criteria.andAdminLevelYearGreaterThanOrEqualTo(startNowLevelAge);
            isMainPost = true;
        }

        if(isMainPost!=null){
            criteria.andIsMainPostEqualTo(isMainPost);
        }

        if (unitTypes != null) {
            criteria.andUnitTypeIdIn(Arrays.asList(unitTypes));
        }
        if (adminLevels != null) {
            criteria.andCpAdminLevelIn(Arrays.asList(adminLevels));
        }
        if(cpIsCpc!=null){
            criteria.andCpIsCpcEqualTo(cpIsCpc);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0) {
                criteria.andIdIn(Arrays.asList(ids));
            }

            if (displayType==null || displayType == 1 || displayType == 2) {
                if (exportType == 0) {
                    unitPost_export(example, response);
                    return;
                } else if (exportType == 1) {
                    // 导出空缺或兼职岗位
                    unitPostService.exportOpenList(displayType, example, response);
                    return;
                }
            } else if (displayType == 3) {

                example.setGroupByClause("cadre_id");
                unitPost_cadre_export(example, response);
                return;
            }
        }

         if (displayType!=null && displayType == 3) {
            example.setGroupByClause("cadre_id");
         }

        long count = unitPostViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitPostView> records= unitPostViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(unitPost.class, unitPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_au(UnitPost record, Boolean isSync, Integer cadreId, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsPrincipal(BooleanUtils.isTrue(record.getIsPrincipal()));
        record.setIsCpc(BooleanUtils.isTrue(record.getIsCpc()));
        record.setName(record.getName());
        record.setLabel(StringUtils.trimToEmpty(record.getLabel()));

        CadrePost cadrePost=new CadrePost();
        cadrePost.setCadreId(cadreId);

        if (unitPostService.idDuplicate(id, record.getCode())) {

            UnitPost up = unitPostService.getByCode(record.getCode());
            return failed("岗位编号重复，已有岗位：{0}", up.getName());
        }

        if (unitPostService.leaderTypeDuplicate(id, record.getUnitId(), record.getLeaderType())) {

            UnitPostView up = unitPostService.getByLeaderType(record.getUnitId(), record.getLeaderType());
            return failed("{0}重复，已有岗位：{1}",
                    SystemConstants.UNIT_POST_LEADER_TYPE_MAP.get(record.getLeaderType()),
                    up.getName());
        }

        if (id == null) {
            //record.setStatus(SystemConstants.UNIT_POST_STATUS_NORMAL);
            unitPostService.insertSelective(record,cadrePost);
            logger.info(addLog( LogConstants.LOG_ADMIN, "添加干部岗位：%s", record.getId()));
        } else {
            Integer oldCadreId=null;
            CadrePost cp=cadrePostService.getByUnitPostId(id);//原关联干部
            if(cp!=null) oldCadreId=cp.getCadreId();

            unitPostService.updateByPrimaryKeySelective(record,oldCadreId,cadrePost,isSync);
            logger.info(addLog( LogConstants.LOG_ADMIN, "更新干部岗位：%s-干部任职情况：%s", record.getId(), cadreId));
           /* if(BooleanUtils.isTrue(isSync)){
                  unitPostService.syncCadrePost(record,cadreId);
                logger.info(addLog( LogConstants.LOG_ADMIN, "更新干部任职情况：%s", record.getId()));
            }*/
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPost_au")
    public String unitPost_au(Integer id, Integer unitId,
                              @RequestParam(required = false, defaultValue =
                                      SystemConstants.UNIT_POST_STATUS_NORMAL+"") byte status,
                              ModelMap modelMap) {

        if (id != null) {
            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
            modelMap.put("unitPost", unitPost);

            CadrePost cadrePost=cadrePostService.getByUnitPostId(unitPost.getId());
            modelMap.put("cadrePost", cadrePost);

            unitId = unitPost.getUnitId();
            status = unitPost.getStatus();
        }
        if(unitId!=null) {
            modelMap.put("unit", CmTag.getUnit(unitId));
        }
        modelMap.put("status", status);

        return "unit/unitPost/unitPost_au";
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPost_group", method = RequestMethod.POST)
    @ResponseBody
    public Map unitPost_group(UnitPost record, HttpServletRequest request) {

        Integer id = record.getId();
        Integer groupId = record.getGroupId();
        if(id!=null){
            if(groupId==null){
                commonMapper.excuteSql("update unit_post set group_id=null where id="+ record.getId());

            }else{
               unitPostMapper.updateByPrimaryKeySelective(record);
            }
        }

        logger.info(addLog( LogConstants.LOG_ADMIN, "更新关联岗位分组：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPost_group")
    public String unit_post_group(Integer id,
                              ModelMap modelMap) {

        if (id != null) {
            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
            modelMap.put("unitPost", unitPost);

            if(unitPost.getGroupId()!=null){
                UnitPostGroup  unitPostGroup=unitPostGroupMapper.selectByPrimaryKey(unitPost.getGroupId());
                modelMap.put("unitPostGroup", unitPostGroup);
            }
        }

        return "unit/unitPost/unitPost_group";
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPost_abolish")
    public String unitPost_abolish(Integer[] ids,  ModelMap modelMap) {

        int id = ids[0];
        UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
        modelMap.put("unitPost", unitPost);

        return "unit/unitPost/unitPost_abolish";
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPost_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_abolish(HttpServletRequest request, Integer[] ids,
                                   @DateTimeFormat(pattern= DateUtils.YYYY_MM_DD)Date abolishDate) {

        if (null != ids && ids.length>0) {
            unitPostService.abolish(ids, abolishDate);
            logger.info(addLog(LogConstants.LOG_ADMIN, "撤销干部岗位：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPost_unabolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_unabolish(HttpServletRequest request, Integer[] ids) {

        if (null != ids && ids.length>0) {
            unitPostService.unabolish(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "返回现有干部岗位：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPost_sortByCode", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_sortByCode(Integer unitId, @RequestParam(required = false, defaultValue = "1") Boolean asc) {

        unitPostService.sortByCode(unitId, asc);
        logger.info(addLog(LogConstants.LOG_ADMIN, "按干部岗位编号进行排序, unitId=%s", unitId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:del")
    @RequestMapping(value = "/unitPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map unitPost_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){

            UnitPostExample example = new UnitPostExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<UnitPost> unitPosts = unitPostMapper.selectByExample(example);

            unitPostService.batchDel(ids);

            logger.info(addLog( LogConstants.LOG_ADMIN, "批量删除干部岗位：%s",
                    JSONUtils.toString(unitPosts, false)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:changeOrder")
    @RequestMapping(value = "/unitPost_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        unitPostService.changeOrder(id, addNum);
        logger.info(addLog( LogConstants.LOG_ADMIN, "干部岗位调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void unitPost_export(UnitPostViewExample example, HttpServletResponse response) {

        List<UnitPostView> records = unitPostViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"岗位编号|100","岗位名称|300|left","单位编号|100","单位名称|220|left",
                "分管工作|200|left","是否正职|100",
                "岗位级别|100","职务属性|150","职务类别|100",
                "是否占干部职数|100",
                "现任职干部|100","任职干部是否占职数|100","干部级别|100","任职类型|100",
                "任职日期|100","现任职务年限|100", "现任职务始任日期|120","现任职务始任年限|120", "备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            UnitPostView record = records.get(i);
            CadreView cadre = record.getCadre();
            CadrePost cadrePost = record.getCadrePost();

            String[] values = {
                            record.getCode()+"",
                            record.getName(),
                            record.getUnitCode(),
                            record.getUnitName(),
                            record.getJob(),
                            BooleanUtils.isTrue(record.getIsPrincipal())?"是":"否",
                            metaTypeService.getName(record.getAdminLevel()),
                            metaTypeService.getName(record.getPostType()),
                            metaTypeService.getName(record.getPostClass()),
                            BooleanUtils.isTrue(record.getIsCpc())?"是":"否",
                            cadre==null?"":cadre.getRealname(),
                            cadre==null?"":(BooleanUtils.isTrue(record.getCpIsCpc())?"是":"否"),
                            cadre==null?"":metaTypeService.getName(record.getCpAdminLevel()),
                            cadrePost==null?"":(cadrePost.getIsMainPost()?"主职":"兼职"),
                            cadrePost==null?"": DateUtils.formatDate(cadrePost.getLpWorkTime(), DateUtils.YYYYMMDD_DOT),
                            cadrePost==null?"": DateUtils.yearOffNow(cadrePost.getLpWorkTime()) + "",
                            cadrePost==null?"": DateUtils.formatDate(cadrePost.getNpWorkTime(), DateUtils.YYYYMMDD_DOT),
                            cadrePost==null?"": DateUtils.yearOffNow(cadrePost.getNpWorkTime()) + "",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("干部岗位库(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    public void unitPost_cadre_export(UnitPostViewExample example, HttpServletResponse response) {

        //工作证号、姓名、所在单位及职务、行政级别、职务属
        //性、是否正职、是否班子负责人、性别、民族、出生时间、年龄、党派、党派
        //加入时间、参加工作时间、最高学历、最高学位、所学专业、专业技术职务、
        //现职务始任时间、现职务始任年限、现职级始任时间、任现职级年限
        List<UnitPostView> records = unitPostViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100", "姓名|50", "所在单位及职务|300|left", "行政级别|100","职务属性|150",
                "是否正职|80", "是否班子负责人|120", "性别|50", "民族|100", "出生日期|100",
                "年龄|50", "政治面貌|100", "党派加入时间|150", "参加工作时间|150", "最高学历|100",
                "最高学位|100", "所学专业|150", "专业技术职务|150","任现职时间|150","现职务始任时间|150","现职务始任年限|150",
                "现职级始任时间|150", "任现职级年限|150"};

        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        boolean postTimeToDay = CmTag.getBoolProperty("postTimeToDay");

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            UnitPostView record = records.get(i);
            CadreView cv = record.getCadre();
            Date birth = cv.getBirth();
            Byte gender = cv.getGender();
            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(),
                    cv.getOwPositiveTime(), "中共",
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);
            String partyName = cadreParty.get("partyName");
            String partyAddTime = cadreParty.get("growTime");
            String[] values = {
                            cv.getCode(),
                            cv.getRealname(),
                            cv.getTitle(),
                            metaTypeService.getName(cv.getAdminLevel()),
                            metaTypeService.getName(cv.getPostType()),

                    BooleanUtils.isTrue(record.getCadreIsPrincipal())?"是":"否",
                    SystemConstants.UNIT_POST_LEADER_TYPE_MAP.get(record.getLeaderType()),
                    gender==null?"": SystemConstants.GENDER_MAP.get(gender),
                    cv.getNation(),
                    DateUtils.formatDate(birth, birthToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),

                    birth== null ? "" : DateUtils.yearOffNow(birthToDay?birth:DateUtils.getFirstDayOfMonth(birth)) + "",
                    StringUtils.trimToEmpty(partyName),
                    StringUtils.trimToEmpty(partyAddTime),
                    DateUtils.formatDate(cv.getWorkTime(), DateUtils.YYYYMM), //参加工作时间
                    metaTypeService.getName(cv.getEduId()),

                    cv.getDegree(),
                    cv.getMajor(),
                    cv.getProPost(),
                    DateUtils.formatDate(cv.getLpWorkTime(), postTimeToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),
                    DateUtils.formatDate(cv.getNpWorkTime(), postTimeToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),
                    NumberUtils.trimToEmpty(cv.getCadrePostYear()),

                    DateUtils.formatDate(cv.getsWorkTime(), postTimeToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),
                    NumberUtils.trimToEmpty(cv.getAdminLevelYear())
            };
            valuesList.add(values);
        }
        String fileName = String.format("超任职年限的干部列表(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/unitPost_selects")
    @ResponseBody
    public Map unitPost_selects(Integer pageSize, Integer pageNo,
                                Byte status,
                                Integer unitId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitPostViewExample example = new UnitPostViewExample();
        Criteria criteria = example.createCriteria();
        if(status!=null){
            criteria.andStatusEqualTo(status);
        }

        example.setOrderByClause("status asc, unit_status asc, unit_sort_order asc, sort_order asc");
        if(unitId!=null){
            criteria.andUnitIdEqualTo(unitId);
        }
        if(StringUtils.isNotBlank(searchStr)){
            criteria.search(searchStr.trim());
        }

        long count = unitPostViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<UnitPostView> records = unitPostViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(UnitPostView record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getCode() + "-" + record.getName());
                option.put("adminLevel", record.getAdminLevel());
                option.put("id", record.getId() + "");
                option.put("up", record);
                option.put("del", record.getStatus()== SystemConstants.UNIT_POST_STATUS_ABOLISH);
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("unitPost:allocation")
    @RequestMapping("/unitPostAllocation")
    public String unitPostAllocation(
            @RequestParam(required = false, defaultValue = "1") Byte module,
            @RequestParam(required = false, defaultValue = CadreConstants.CADRE_TYPE_CJ+"") byte cadreType,
            @RequestParam(required = false, defaultValue = "0") int export,
            ModelMap modelMap, HttpServletResponse response) throws IOException {

        modelMap.put("module", module);
        modelMap.put("cadreType", cadreType);

        if (module == 1) {
            byte _upa_displayPosts = CmTag.getByteProperty("upa_displayPosts");

            if (export == 1) {
                XSSFWorkbook wb=new XSSFWorkbook();
                if(_upa_displayPosts==UNIT_POST_DISPLAY_NORMAL||_upa_displayPosts==UNIT_POST_DISPLAY_KEEP) {
                     wb = unitPostAllocationService.cpcInfo_Xlsx(cadreType);
                }else if(_upa_displayPosts==UNIT_POST_DISPLAY_NOT_CPC) {
                     wb = unitPostAllocationService.cpcInfo_Xlsx2(cadreType);
                }

                String fileName = sysConfigService.getSchoolName() + "内设机构"
                        + CadreConstants.CADRE_TYPE_MAP.get(cadreType) +"配备情况（"
                        + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
                ExportHelper.output(wb, fileName + ".xlsx", response);
                return null;
            }

            List<UnitPostAllocationInfoBean> beans = new ArrayList<>();

            if(_upa_displayPosts==UNIT_POST_DISPLAY_NORMAL||_upa_displayPosts==UNIT_POST_DISPLAY_KEEP) {
                beans = unitPostAllocationService.cpcInfo_data(null, cadreType, true);
            }else if(_upa_displayPosts==UNIT_POST_DISPLAY_NOT_CPC) {
                beans = unitPostAllocationService.cpcInfo_data2(null, cadreType, true);
            }

            modelMap.put("beans", beans);
        }else if (module == 2) {

            if (export == 1) {
                XSSFWorkbook wb = unitPostAllocationService.cpcStat_Xlsx(cadreType);

                String fileName = sysConfigService.getSchoolName() + "内设机构"+
                        CadreConstants.CADRE_TYPE_MAP.get(cadreType)
                        +"配备统计表（" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
                ExportHelper.output(wb, fileName + ".xlsx", response);
                return null;
            }

            MetaClass mcUnitType = CmTag.getMetaClassByCode("mc_unit_type");
            Map<String, MetaClassOption> unitTypeGroupMap = mcUnitType.getOptions();
            modelMap.put("unitTypeGroupMap", unitTypeGroupMap);

            Map<String, List<Integer>> cpcStatDataMap = unitPostAllocationService.cpcStat_data(cadreType);
            modelMap.put("cpcStatDataMap", cpcStatDataMap);
        }

        return "unit/unitPost/unitPostAllocation_page";
    }

    @RequiresPermissions("unitPost:allocation")
    @RequestMapping("/unitPost_unitType_cadres")
    public String unitPost_unitType_cadres(Integer adminLevel, boolean isMainPost, String unitType, ModelMap modelMap) {

        List<CadrePost> cadrePosts = iCadreMapper.findCadrePostsByUnitType(adminLevel, isMainPost, unitType.trim());
        modelMap.put("cadrePosts", cadrePosts);

        MetaClass mcUnitType = CmTag.getMetaClassByCode("mc_unit_type");
        Map<String, MetaClassOption> unitTypeGroupMap = mcUnitType.getOptions();

        modelMap.put("unitType", unitTypeGroupMap.get(unitType.trim()));
        modelMap.put("adminLevel", metaTypeService.findAll().get(adminLevel));
        modelMap.put("isMainPost", isMainPost);

        return "unit/unitPost/unitPost_unitType_cadres";
    }

    @RequiresPermissions("unitPost:import")
    @RequestMapping("/unitPost_collectUnitName")
    public String unitPost_collectUnitName(ModelMap modelMap) {

        return "unit/unitPost/unitPost_collectUnitName";
    }

    @RequiresPermissions("unitPost:import")
    @RequestMapping(value = "/unitPost_collectUnitName", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_collectUnitName(int col,
                                           Integer addCol, //工号插入列数
                                           HttpServletRequest request, HttpServletResponse response) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int firstNotEmptyRowNum = 0;
        XSSFRow firstRow = sheet.getRow(firstNotEmptyRowNum++);
        while (firstRow==null){
            if(firstNotEmptyRowNum>=100) break;
            firstRow = sheet.getRow(firstNotEmptyRowNum++);
        }
        if(firstRow==null){
            return failed("该文件前100行数据为空，无法导出");
        }

        int cellNum = firstRow.getLastCellNum() - firstRow.getFirstCellNum() + 1; // 只能得到第一行的列数

        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            // 行数据如果为空，不处理
            if (row == null) continue;

            XSSFCell cell = row.getCell(col - 1);
            String key = ExcelUtils.getCellValue(cell);
            if(StringUtils.isBlank(key)) continue;

            // 去掉所有空格
            key = key.replaceAll("\\s*", "");

            //提取学工号

            UnitPostViewExample example = new UnitPostViewExample();
            example.createCriteria().andNameEqualTo(key).andStatusEqualTo((byte)1);
            List<UnitPostView> unitPostList = unitPostViewMapper.selectByExample(example);

            if (unitPostList==null||unitPostList.size()==0) continue;
            Set<String> unitNameList = unitPostList.stream().map(UnitPostView::getUnitName).collect(Collectors.toSet());

            // 每一行插入的位置
            int rowAddCol = -1;
            if (addCol != null && addCol <= cellNum) {
                rowAddCol = addCol-1;
                cell = row.getCell(rowAddCol);
                if(cell==null){
                    cell = row.createCell(rowAddCol);
                }
            } else {
                rowAddCol = cellNum;
                cell = row.createCell(rowAddCol);
            }
            cell.setCellValue(StringUtils.join(unitNameList,"，"));
        }

        String savePath = FILE_SEPARATOR + "_filterExport"
                + FILE_SEPARATOR + "提取单位名称" + DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD) + ".xlsx";
        FileUtils.mkdirs(springProps.uploadPath + savePath, true);

        ExportHelper.save(workbook, springProps.uploadPath + savePath);

        Map<String, Object> resultMap = success();
        resultMap.put("file", UserResUtils.sign(savePath));
        resultMap.put("filename", xlsx.getOriginalFilename());

        return resultMap;
    }
    @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPost_label")
    public String unitPost_label(Integer unitPostId,
                                 Integer cadreId,
                                 @RequestParam(required = false, defaultValue = "1")Byte cls,
                                 ModelMap modelMap) {

        UnitPost unitPost = unitPostMapper.selectByPrimaryKey(unitPostId);
        String[] labels=unitPost.getLabel().split(",");
        Integer adminLevel = unitPost.getAdminLevel();
        if(StringUtils.equals(CmTag.getMetaType(adminLevel).getCode(), "mt_admin_level_main")||
                StringUtils.equals(CmTag.getMetaType(adminLevel).getCode(), "mt_admin_level_vice")||
                StringUtils.equals(CmTag.getMetaType(adminLevel).getCode(), "mt_admin_level_none")){
            modelMap.put("status", CADRE_STATUS_CJ);
        }else{
            modelMap.put("status", CADRE_STATUS_KJ);
        }
        
        if(cadreId !=null){
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
        }

        modelMap.put("cls", cls);
        modelMap.put("unitPost", unitPost);
        modelMap.put("unitPostLabels", Arrays.asList(labels));

        return "unit/unitPost/unitPost_label";
    }
}
