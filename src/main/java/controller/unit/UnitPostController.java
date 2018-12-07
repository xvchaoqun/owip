package controller.unit;

import controller.BaseController;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.unit.Unit;
import domain.unit.UnitPost;
import domain.unit.UnitPostView;
import domain.unit.UnitPostViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.util.HtmlUtils;
import service.unit.UnitPostAllocationInfoBean;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UnitPostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 历史任职干部
    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPost_cadres")
    public String unitPost_cadres(int unitPostId, ModelMap modelMap) {

        modelMap.put("unitPost", unitPostMapper.selectByPrimaryKey(unitPostId));

        return "unit/unitPost/unitPost_cadres";
    }

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPosts")
    public String unitPosts(int unitId, int adminLevelId, Boolean displayEmpty, ModelMap modelMap) {

        List<UnitPostView> unitPosts = unitPostService.query(unitId, adminLevelId, displayEmpty);
        modelMap.put("unitPosts", unitPosts);

        return "unit/unitPost/unitPosts";
    }

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPostList")
    public String unitPostList(@RequestParam(required = false, defaultValue = "1")Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        return "unit/unitPost/unitPostList_page";
    }

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPost")
    public String unitPost(@RequestParam(required = false, defaultValue = "1")Byte cls,
                           @RequestParam(required = false, value = "unitTypes") Integer[] unitTypes,
                           @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
                           Integer cadreId,
                           Integer unitId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (cadreId != null) {
            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
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
                                    String name,
                                    Integer adminLevel,
                                    Integer postType,
                                    Integer postClass,
                              Boolean isPrincipalPost,
                              Boolean isCpc,
                              // 显示空缺岗位
                              Boolean displayEmpty,
                              // 显示空缺岗位或兼职
                              Boolean displayOpen,

                              Integer cadreId,
                              Integer startNowPostAge,
                              Integer endNowPostAge,
                              Integer startNowLevelAge,
                              Integer endNowLevelAge,
                              @RequestParam(required = false, value = "unitTypes") Integer[] unitTypes, // 部门属性
                              @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels, // 行政级别

                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, defaultValue = "0") int exportType,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitPostViewExample example = new UnitPostViewExample();
        UnitPostViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("unit_sort_order asc, sort_order desc");
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
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (adminLevel!=null) {
            criteria.andAdminLevelEqualTo(adminLevel);
        }
        if (postType!=null) {
            criteria.andPostTypeEqualTo(postType);
        }
        if (postClass!=null) {
            criteria.andPostClassEqualTo(postClass);
        }

        if (isPrincipalPost!=null) {
            criteria.andIsPrincipalPostEqualTo(isPrincipalPost);
        }
        if (isCpc!=null) {
            criteria.andIsCpcEqualTo(isCpc);
        }

        if(BooleanUtils.isTrue(displayEmpty)){
            criteria.andCadreIdIsNull();
        }

        if(BooleanUtils.isTrue(displayOpen)){
            criteria.displayOpen();
        }

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (endNowPostAge != null) {
            criteria.andCadrePostYearLessThanOrEqualTo(endNowPostAge);
        }
        if (startNowPostAge != null) {
            criteria.andCadrePostYearGreaterThanOrEqualTo(startNowPostAge);
        }
        if (endNowLevelAge != null) {
            criteria.andAdminLevelYearLessThanOrEqualTo(endNowLevelAge);
        }
        if (startNowLevelAge != null) {
            criteria.andAdminLevelYearGreaterThanOrEqualTo(startNowLevelAge);
        }
        if (unitTypes != null) {
            criteria.andUnitTypeIdIn(Arrays.asList(unitTypes));
        }
        if (adminLevels != null) {
            criteria.andCpAdminLevelIn(Arrays.asList(adminLevels));
        }
        if (export == 1) {
            if(exportType==0) {
                if (ids != null && ids.length > 0)
                    criteria.andIdIn(Arrays.asList(ids));
                unitPost_export(example, response);
                return;
            }else if(exportType==1){
                // 导出空缺或兼职岗位
                unitPostService.exportOpenList(example, response);
                return;
            }
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
    public Map do_unitPost_au(UnitPost record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsPrincipalPost(BooleanUtils.isTrue(record.getIsPrincipalPost()));
        record.setIsCpc(BooleanUtils.isTrue(record.getIsCpc()));

        record.setName(HtmlUtils.htmlUnescape(record.getName()));

        if (unitPostService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if (id == null) {
            record.setStatus(SystemConstants.UNIT_POST_STATUS_NORMAL);
            unitPostService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "添加干部岗位库：%s", record.getId()));
        } else {

            unitPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "更新干部岗位库：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPost_au")
    public String unitPost_au(Integer id, Integer unitId, ModelMap modelMap) {

        if (id != null) {
            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
            modelMap.put("unitPost", unitPost);
            unitId = unitPost.getUnitId();
        }
        modelMap.put("unitId", unitId);

        return "unit/unitPost/unitPost_au";
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping("/unitPost_abolish")
    public String unitPost_abolish(int id, ModelMap modelMap) {

        UnitPost unitPost = unitPostMapper.selectByPrimaryKey(id);
        modelMap.put("unitPost", unitPost);

        return "unit/unitPost/unitPost_abolish";
    }

    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPost_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_abolish(HttpServletRequest request, int id,
                                   @DateTimeFormat(pattern=DateUtils.YYYY_MM_DD)Date abolishDate) {

        unitPostService.abolish(id, abolishDate);
        logger.info(addLog( LogConstants.LOG_ADMIN, "撤销干部岗位：%s", id));

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("unitPost:edit")
    @RequestMapping(value = "/unitPost_unabolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_unabolish(HttpServletRequest request, int id) {

        unitPostService.unabolish(id);
        logger.info(addLog( LogConstants.LOG_ADMIN, "返回现有干部岗位：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unitPost:del")
    @RequestMapping(value = "/unitPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map unitPost_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            unitPostService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_ADMIN, "批量删除干部岗位：%s", StringUtils.join(ids, ",")));
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
                "现任职干部|100","干部级别|100","任职类型|100",
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
                            BooleanUtils.isTrue(record.getIsPrincipalPost())?"是":"否",
                            metaTypeService.getName(record.getAdminLevel()),
                            metaTypeService.getName(record.getPostType()),
                            metaTypeService.getName(record.getPostClass()),
                            BooleanUtils.isTrue(record.getIsCpc())?"是":"否",
                            cadre==null?"":cadre.getRealname(),
                            cadre==null?"":metaTypeService.getName(record.getCpAdminLevel()),
                            cadrePost==null?"":(cadrePost.getIsMainPost()?"主职":"兼职"),
                            "",
                            "",
                            "",
                            "",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部岗位库_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/unitPost_selects")
    @ResponseBody
    public Map unitPost_selects(Integer pageSize, Integer pageNo, Integer unitId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        UnitPostViewExample example = new UnitPostViewExample();
        UnitPostViewExample.Criteria criteria = example.createCriteria()
                .andStatusNotEqualTo(SystemConstants.UNIT_POST_STATUS_DELETE);
        example.setOrderByClause("status asc, unit_status asc, unit_sort_order asc, sort_order desc");
        if(unitId!=null){
            criteria.andUnitIdEqualTo(unitId);
        }
        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
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
                option.put("text", record.getName());
                option.put("code", record.getCode());
                option.put("adminLevel", record.getAdminLevel());
                option.put("id", record.getId() + "");
                option.put("up", record);
                option.put("del", record.getStatus()==SystemConstants.UNIT_POST_STATUS_ABOLISH);
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPostAllocation")
    public String unitPostAllocation(
            @RequestParam(required = false, defaultValue = "1") Byte module,
            @RequestParam(required = false, defaultValue = "0") int export,
            ModelMap modelMap, HttpServletResponse response) throws IOException {

        modelMap.put("module", module);

        if (module == 1) {
            if (export == 1) {
                XSSFWorkbook wb = unitPostAllocationService.cpcInfo_Xlsx();

                String fileName = sysConfigService.getSchoolName() + "内设机构干部配备情况（" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
                ExportHelper.output(wb, fileName + ".xlsx", response);
                return null;
            }

            List<UnitPostAllocationInfoBean> beans = unitPostAllocationService.cpcInfo_data(null, true);
            modelMap.put("beans", beans);
        } else if (module == 2) {

            return "unit/unitPost/unitPost_openList";

        }else if (module == 3) {

            if (export == 1) {
                XSSFWorkbook wb = unitPostAllocationService.cpcStat_Xlsx();

                String fileName = sysConfigService.getSchoolName() + "内设机构干部配备统计表（" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
                ExportHelper.output(wb, fileName + ".xlsx", response);
                return null;
            }

            Map<String, List<Integer>> cpcStatDataMap = unitPostAllocationService.cpcStat_data();
            modelMap.put("jgList", cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_JG));
            modelMap.put("xyList", cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_XY));
            modelMap.put("fsList", cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_FS));
            modelMap.put("totalList", cpcStatDataMap.get("total"));
        }

        return "unit/unitPost/unitPostAllocation_page";
    }

    @RequiresPermissions("unitPost:list")
    @RequestMapping("/unitPost_unitType_cadres")
    public String unitPost_unitType_cadres(Integer adminLevelId, boolean isMainPost, String unitType, ModelMap modelMap) {

        List<CadrePost> cadrePosts = iCadreMapper.findCadrePostsByUnitType(adminLevelId, isMainPost, unitType.trim());
        modelMap.put("cadrePosts", cadrePosts);

        modelMap.put("unitType", SystemConstants.UNIT_TYPE_ATTR_MAP.get(unitType.trim()));
        modelMap.put("adminLevel", metaTypeService.findAll().get(adminLevelId));
        modelMap.put("isMainPost", isMainPost);

        return "unit/unitPost/unitPost_unitType_cadres";
    }
}
