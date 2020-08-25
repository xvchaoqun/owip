package controller.cadre;

import controller.BaseController;
import controller.analysis.CadreCategorySearchBean;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.*;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.party.BranchMember;
import domain.sys.SysUserView;
import domain.unit.Unit;
import domain.unit.UnitPost;
import freemarker.template.TemplateException;
import mixin.CadreDispatchMixin;
import mixin.CadreEduMixin;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import persistence.cadre.common.ICadreWorkMapper;
import service.cadre.CadreAdformService;
import service.cadre.CadreInfoCheckService;
import service.cadre.CadreInfoFormService;
import service.cadre.CadreService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.DispatchConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CadreController extends BaseController {

    @Autowired
    private CadreAdformService cadreAdformService;
    @Autowired
    private CadreInfoFormService cadreInfoFormService;
    @Autowired
    private ICadreWorkMapper iCadreWorkMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/cadre")
    public String cadre_page(@RequestParam(required = false, defaultValue = CadreConstants.CADRE_STATUS_CJ + "") Byte status,
                             String[] nation,
                             Integer[] dpTypes,
                             Integer[] unitIds,
                             Integer[] unitTypes,
                             Integer[] adminLevels,
                             Integer[] maxEdus,
                             Integer[] postTypes,
                             String[] proPosts,
                             String[] proPostLevels,
                             Byte[] leaderTypes,
                             Integer[] labels, // 标签
                             String[] staffTypes,
                            String[] authorizedTypes,
                             @RequestParam(required = false, defaultValue = "0")Boolean isEngage, //是否聘任制干部，指无行政级别的干部
                             @RequestParam(required = false, defaultValue = "0")Boolean isKeepSalary,//是否为保留待遇干部信息，指第一主职无关联岗位的干部
                             Integer[] workTypes,
                             Integer cadreId, ModelMap modelMap) {

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREARCHIVE)) {
            throw new UnauthorizedException("没有权限访问");
        }

        modelMap.put("status", status);

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
        }

        // MAP<unitTypeId, List<unitId>>
        Map<Integer, List<Integer>> unitListMap = new LinkedHashMap<>();
        Map<Integer, List<Integer>> historyUnitListMap = new LinkedHashMap<>();
        Map<Integer, Unit> unitMap = unitService.findAll();
        for (Unit unit : unitMap.values()) {

            Integer unitTypeId = unit.getTypeId();
            if (unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY){
                List<Integer> units = historyUnitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    historyUnitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            }else {
                List<Integer> units = unitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    unitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            }
        }
        modelMap.put("unitListMap", unitListMap);
        modelMap.put("historyUnitListMap", historyUnitListMap);

        if (dpTypes != null) {
            modelMap.put("selectDpTypes", Arrays.asList(dpTypes));
        }
        if (unitIds != null) {
            modelMap.put("selectUnitIds", Arrays.asList(unitIds));
        }
        if (unitTypes != null) {
            modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
        }
        if (adminLevels != null) {
            modelMap.put("selectAdminLevels", Arrays.asList(adminLevels));
        }

        if (maxEdus != null) {
            modelMap.put("selectMaxEdus", Arrays.asList(maxEdus));
        }
        if (postTypes != null) {
            modelMap.put("selectPostTypes", Arrays.asList(postTypes));
        }

        modelMap.put("proPosts", CmTag.getPropertyCaches("teacherProPosts"));
        if (proPosts != null) {
            modelMap.put("selectProPosts", Arrays.asList(proPosts));
        }
        modelMap.put("proPostLevels", CmTag.getPropertyCaches("teacherProPostLevels"));
        if (proPostLevels != null) {
            modelMap.put("selectProPostLevels", Arrays.asList(proPostLevels));
        }
        if (leaderTypes != null) {
            modelMap.put("selectLeaderTypes", Arrays.asList(leaderTypes));
        }
        if (workTypes != null) {
            modelMap.put("selectWorkTypes",Arrays.asList(workTypes));
        }

        //modelMap.put("nations", CmTag.getPropertyCaches("teacherNations"));
        if (nation != null) {
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        if(labels!=null){
            modelMap.put("selectLabels", Arrays.asList(labels));
        }

        modelMap.put("staffTypes", CmTag.getPropertyCaches("staffTypes"));
        modelMap.put("authorizedTypes", CmTag.getPropertyCaches("authorizedTypes"));
        if(staffTypes!=null){
            modelMap.put("selectStaffTypes", Arrays.asList(staffTypes));
        }
        if(authorizedTypes!=null){
            modelMap.put("selectAuthorizedTypes", Arrays.asList(authorizedTypes));
        }

        // 导出的列名字
        List<String> titles = cadreExportService.getTitles();
        boolean useCadreState = CmTag.getBoolProperty("useCadreState");
        boolean hasPartyModule = CmTag.getBoolProperty("hasPartyModule");
        if(!useCadreState){
            titles.remove(2);
        }
        if(!hasPartyModule){
            titles.remove(titles.size()-7); // 去掉所在党组织
        }

        modelMap.put("titles", titles);

        if (isEngage || isKeepSalary) {
            return "cadre/cadre_engage_page";
        }

        return "cadre/cadre_page";
    }

    @RequestMapping("/cadre_data")
    public void cadre_data(HttpServletResponse response,
                           HttpServletRequest request,
                           @RequestParam(required = false, defaultValue = CadreConstants.CADRE_STATUS_CJ + "") Byte status,
                           Integer cadreId,
                           Byte gender,
                           Integer startAge,
                           Integer endAge,
                           Integer startDpAge, // 党龄
                           Integer endDpAge, // 党龄
                           Integer startNowPostAge,
                           Integer endNowPostAge,
                           Integer startNowLevelAge,
                           Integer endNowLevelAge,
                           Integer[] workTypes,
                           Boolean andWorkTypes,
                           String workDetail,
                           String[] nation,
                           Integer[] dpTypes, // 党派
                           @RequestDateRange DateRange _birth,
                           @RequestDateRange DateRange _cadreGrowTime,
                           Integer[] unitIds, // 所在单位
                           Integer[] unitTypes, // 部门属性
                           Integer[] adminLevels, // 行政级别
                           Integer[] maxEdus, // 最高学历
                           String major, // 所学专业
                           Integer[] postTypes, // 职务属性
                           String[] proPosts, // 专业技术职务
                           String[] proPostLevels, // 职称级别
                           Boolean isPrincipal, // 是否正职
                           Byte[] leaderTypes, // 是否班子负责人
                           Integer[] labels, // 标签
                           String[] staffTypes, // 标签
                           String[] authorizedTypes, // 标签
                           Boolean isDouble, // 是否双肩挑
                           Boolean hasCrp, // 是否有干部挂职经历
                           Boolean hasAbroadEdu, // 是否有国外学习经历
                           Boolean isDep,
                           Byte degreeType,
                           Integer state,
                           String title,
                           String sortBy, // 自定义排序
                           Byte firstUnitPost, // 第一主职是否已关联岗位（1：关联 0： 没关联 -1：缺第一主职）
                           //是否为保留待遇干部信息，指第一主职无关联岗位的干部
                           @RequestParam(required = false, defaultValue = "0") Boolean isKeepSalary,
                           //是否聘任制干部，指无行政级别的干部
                           @RequestParam(required = false, defaultValue = "0") Boolean isEngage,
                           @RequestParam(required = false, defaultValue = "0") int export,
                           @RequestParam(required = false, defaultValue = "1") int format, // 导出格式
                           Integer[] ids, // 导出的记录
                           Integer[] cols, // 选择导出的列
                           Integer pageSize, Integer pageNo) throws IOException, TemplateException, DocumentException {

        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREARCHIVE)) {
            throw new UnauthorizedException("没有权限访问");
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreViewExample example = new CadreViewExample();
        CadreViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);

        String sortStr = "sort_order desc";
        if(StringUtils.isNotBlank(sortBy)) {
            switch (sortBy.trim()){
                case "birth_asc":
                   sortStr = "birth asc";
                   break;
                case "birth_desc":
                    sortStr = "birth desc";
                    break;
                case "growTime":
                   sortStr = "ow_grow_time asc, dp_grow_time asc";
                   break;
                case "arriveTime":
                   sortStr = "arrive_time asc";
                   break;
                case "finishTime":
                   sortStr = "finish_time asc";
                   break;
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
        example.setOrderByClause(sortStr);

        if (isEngage) {
            Integer adminLevel = CmTag.getMetaTypeByCode("mt_admin_level_none").getId();
            criteria.andAdminLevelEqualTo(adminLevel);
        }
        if (isKeepSalary) { // 保留待遇干部，即第一主职已关联岗位
           criteria.andMainCadrePostIdGreaterThan(0).andUnitPostIdIsNull();
        }
        if (firstUnitPost != null) {
            if(firstUnitPost==-1){ // 缺第一主职
                criteria.andMainCadrePostIdIsNull();
            }else if (firstUnitPost==1){ // 第一主职已关联岗位
                criteria.andMainCadrePostIdGreaterThan(0).andUnitPostIdIsNotNull();
            }else if (firstUnitPost==0){ // 第一主职没关联岗位
                criteria.andMainCadrePostIdGreaterThan(0).andUnitPostIdIsNull();
            }
        }

        if (workTypes != null){
            List<Integer> cadreIds = iCadreWorkMapper.getCadreIdsOfWorkTypes(Arrays.asList(workTypes),
                    BooleanUtils.isTrue(andWorkTypes), status);
            if(cadreIds.size()==0){
                criteria.andIdIsNull();
            }else {
                criteria.andIdIn(cadreIds);
            }
        }
        if (StringUtils.isNotBlank(workDetail)){
            String[] workDetails=workDetail.split(SystemConstants.STRING_SEPARTOR);
            List<String> detailList= Arrays.asList(workDetails);
            List<Integer> cadreIds = iCadreWorkMapper.getCadreIdsOfWorkDetail(detailList, status);
            if(cadreIds.size()==0){
                criteria.andIdIsNull();
            }else {
                criteria.andIdIn(cadreIds);
            }
        }
        if(hasAbroadEdu!=null){

            CadreCategorySearchBean searchBean = new CadreCategorySearchBean();
            searchBean.setCadreStatus(status);
            List<Integer> cadreIds = iCadreMapper.selectCadreIdListByEdu(CadreConstants.CADRE_SCHOOL_TYPE_ABROAD, searchBean);

            if(hasAbroadEdu){
                criteria.andIdIn(cadreIds);
            }else{
                criteria.andIdNotIn(cadreIds);
            }
        }

        if (cadreId != null) {
            criteria.andIdEqualTo(cadreId);
        }
        if (gender != null) {
            criteria.andGenderEqualTo(gender);
        }
        if (_birth.getStart() != null) {
            criteria.andBirthGreaterThanOrEqualTo(_birth.getStart());
        }

        if (_birth.getEnd() != null) {
            criteria.andBirthLessThanOrEqualTo(_birth.getEnd());
        }
        if (_cadreGrowTime.getStart() != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_cadreGrowTime.getStart());
        }

        if (_cadreGrowTime.getEnd() != null) {
            criteria.andGrowTimeLessThanOrEqualTo(_cadreGrowTime.getEnd());
        }

        if (endAge != null) {
            //  >= 不含（减一）
            Date brith= DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endAge + 1));
            Date brith_start=DateUtils.getFirstDayOfMonth(brith);
            criteria.andBirthGreaterThanOrEqualTo(brith_start);
        }
        if (startAge != null) {
            // <= 包含
            Date brith= DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startAge);
            Date brith_end=DateUtils.getLastDayOfMonth(brith);
            criteria.andBirthLessThanOrEqualTo(brith_end);
        }
        if (endDpAge != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endDpAge + 1)));
        }
        if (startDpAge != null) {
            criteria.andGrowTimeLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startDpAge));
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

        if (unitIds != null) {
            criteria.andUnitIdIn(Arrays.asList(unitIds));
        }
        if (unitTypes != null) {
            criteria.andUnitTypeIdIn(Arrays.asList(unitTypes));
        }
        if (adminLevels != null) {
            criteria.andAdminLevelIn(Arrays.asList(adminLevels));
        }
        if (maxEdus != null) {
            if(new HashSet<>(Arrays.asList(maxEdus)).contains(-1)){
                criteria.andEduIdIsNull();
            }else {
                criteria.andEduIdIn(Arrays.asList(maxEdus));
            }
        }
        if(StringUtils.isNotBlank(major)){
                criteria.andMajorLikeIn(major);
        }
        if (postTypes != null) {
            criteria.andPostTypeIn(Arrays.asList(postTypes));
        }
        if (proPosts != null) {
            List<String> _proPosts = new ArrayList<String>(Arrays.asList(proPosts));
            if (_proPosts.contains("0")) {
                _proPosts.remove("0");
                criteria.andProPostIsNullOrIn(_proPosts);
            }else {
                criteria.andProPostIn(_proPosts);
            }
        }
        if (proPostLevels != null) {
            criteria.andProPostLevelIn(Arrays.asList(proPostLevels));
        }
        if (nation != null) {

            Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_nation");
            Set<String> nations = metaTypeMap.values()
                    .stream().map(MetaType::getName).collect(Collectors.toSet());

            criteria.andNationIn(Arrays.asList(nation), nations);
        }
        if (dpTypes != null) {
            criteria.andDpTypeIdIn(new HashSet<>(Arrays.asList(dpTypes)));
        }

        if (labels != null) {
            criteria.andLabelsContain(new HashSet<>(Arrays.asList(labels)));
        }

        if (staffTypes != null) {
            criteria.andStaffTypeIn(Arrays.asList(staffTypes));
        }

        if (authorizedTypes != null) {
            criteria.andAuthorizedTypeIn(Arrays.asList(authorizedTypes));
        }

        if (isPrincipal != null) {
            criteria.andIsPrincipalEqualTo(isPrincipal);
        }
        if (leaderTypes != null) {
            criteria.andLeaderTypeIn(Arrays.asList(leaderTypes));
        }
        if (isDouble != null) {
            criteria.andIsDoubleEqualTo(isDouble);
        }
        if (hasCrp != null) {
            criteria.andHasCrpEqualTo(hasCrp);
        }
        if(isDep!=null){
            criteria.andIsDepEqualTo(isDep);
        }

        if(degreeType!=null){
            if(degreeType==-1){
                criteria.andDegreeTypeIsNull();
            }else{
                criteria.andDegreeTypeEqualTo(degreeType);
            }
        }
        if (state != null) {
            criteria.andStateEqualTo(state);
        }

        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(SqlUtils.trimLike(title));
        }

        if (export == 1) {
            // 判断导出权限
            SecurityUtils.getSubject().checkPermission("cadre:export");

            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            // 一览表
            cadre_export(format, status, cols, example, response);
            return;
        } else if (export == 2 || export == 3 || export == 5 || export == 6) {

            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            List<CadreView> records = cadreViewMapper.selectByExample(example);
            Integer[] cadreIds = records.stream().map(CadreView::getId)
                    .collect(Collectors.toList()).stream().toArray(Integer[]::new);
            if (export == 2) {
                // 干部任免审批表
                cadreAdformService.export(cadreIds, null, format==1, request, response);
            } else if(export == 3){
                // 干部信息采集表
                cadreInfoFormService.export(cadreIds, null, request, response);
            } else if(export == 5){

                perfectCadreInfoExport(cadreIds, response);
            }else if (export == 6){
                // 干部信息表(简版)
                cadreInfoFormService.export_simple(cadreIds, null, request,response);
            }
            return;
        } else if (export == 4) {

            // 批量排序表
            exportForBatchSort(status, response);
        }

        long count = cadreViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreView> Cadres = cadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        if (ShiroHelper.isPermitted("cadre:list")) {
            Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
            baseMixins.put(Dispatch.class, CadreDispatchMixin.class);
            baseMixins.put(CadreEdu.class, CadreEduMixin.class);
            JSONUtils.jsonp(resultMap, baseMixins);
        } else {
            // 没有干部管理员的权限，只能看到部分字段
            JSONUtils.jsonpAntPathFilters(resultMap, "id", "code", "realname",
                    "leaderType", "gender", "nation", "nativePlace",
                    "idcard", "birth", "eduId", "proPost", "lpWorkTime",
                    "npWorkTime", "cadrePostYear", "sWorkTime", "adminLevelYear",
                    "unitId",
                    "unit", "unit.unitType", "unit.unitType.name",
                    "unit.name", "title", "adminLevel", "postType", "dpTypeId", "dpGrowTime", "isOw", "owGrowTime", "mobile", "email");
        }
    }

    private void cadre_export(int format, Byte status, Integer[] cols, CadreViewExample example, HttpServletResponse response) throws IOException {

        SXSSFWorkbook wb = null;
        if (format == 1) {
            // 一览表
            wb = cadreExportService.export(status, example, ShiroHelper.isPermitted("cadre:list") ? 0 : 1, cols, 0);
            String cadreType = CadreConstants.CADRE_STATUS_MAP.get(status);
            String fileName = CmTag.getSysConfig().getSchoolName() + cadreType + "(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
            ExportHelper.output(wb, fileName + ".xlsx", response);

        } else {
            // 名单
            cadreExportService.exportCadres(example, response);
        }
    }

    private void exportForBatchSort(Byte status, HttpServletResponse response) {

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        List<CadreView> records = cadreViewMapper.selectByExample(example);

        int rownum = records.size();
        String[] titles = {"工作证号|120", "姓名|100", "所在单位及职务|220|left", "岗位编号(主职)|120", "岗位名称|300|left", "岗位是否已撤销|80"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            CadreView cv = records.get(i);

            String unitPostCode = "--";
            String unitPostName = "--";
            String unitPostStatus = "--";
            CadrePost cadrePost = cadrePostService.getFirstMainCadrePost(cv.getId());
            if (cadrePost != null) {
                Integer unitPostId = cadrePost.getUnitPostId();
                if (unitPostId != null) {
                    UnitPost unitPost = unitPostMapper.selectByPrimaryKey(unitPostId);
                    if (unitPost != null) {
                        unitPostCode = unitPost.getCode();
                        unitPostName = unitPost.getName();
                        unitPostStatus = (unitPost.getStatus() == SystemConstants.UNIT_POST_STATUS_NORMAL) ? "否" : "是";
                    }
                }
            }

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    unitPostCode,
                    unitPostName,
                    unitPostStatus

            };
            valuesList.add(values);
        }
        String fileName = String.format("干部批量排序表(%s)",
                CadreConstants.CADRE_STATUS_MAP.get(status));

        ExportHelper.export(titles, valuesList, fileName, response);
    }

    private void perfectCadreInfoExport(Integer[] cadreIds, HttpServletResponse response) {

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andIdIn(Arrays.asList(cadreIds));
        example.setOrderByClause("sort_order desc");

        List<CadreView> records = cadreViewMapper.selectByExample(example);

        int rownum = records.size();
        String[] titles = {"工作证号|120", "姓名|100", "所在单位及职务|400|left", "完整性校验结果|120"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            CadreView cv = records.get(i);

            boolean perfectCadreInfo = CadreInfoCheckService.perfectCadreInfo(cv.getUserId());

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    perfectCadreInfo?"通过":"未通过"
            };
            valuesList.add(values);
        }
        String fileName = "干部信息完整性校验结果";

        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 批量排序
    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_batchSort")
    public String cadre_batchSort(byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        return "cadre/cadre_batchSort";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_batchSort", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_batchSort(byte status, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<Integer> cadreIdList = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {
            row++;

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            CadreView cadre = cadreService.dbFindByUserId(uv.getId());
            if (cadre == null) {
                throw new OpException("第{0}行[{1}]不是{2}", row, userCode,
                        CadreConstants.CADRE_STATUS_MAP.get(status));
            }
            cadreIdList.add(cadre.getId());
        }

        Collections.reverse(cadreIdList); // 逆序导入

        cadreService.batchSort(status, cadreIdList);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        return resultMap;
    }

    //@RequiresPermissions("cadre:archive")
    @RequestMapping("/cadre_view")
    public String cadre_view(HttpServletResponse response,
                             int cadreId,
                             String to, // 默认跳转到基本信息
                             Byte cls, // 1 党委委员 或 2 支委委员 档案页
                             Integer branchId,
                             ModelMap modelMap) {

        if (StringUtils.isBlank(to)) {
            to = "cadre_base";
            String def = CmTag.getStringProperty("cadreViewDef");
            if (StringUtils.isNotBlank(def) && !StringUtils.equals(to, def)
                    && ShiroHelper.isPermitted("cadreAdform:list")) {
                to = def;
            }
        }
        modelMap.put("to", to);

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        if(cadre.getUserId().intValue()!=ShiroHelper.getCurrentUserId()
                && !ShiroHelper.isPermittedAny(new String[]{
                    SystemConstants.PERMISSION_CADREARCHIVE,
                    SystemConstants.PERMISSION_PARTYMEMBERARCHIVE})){

            throw new UnauthorizedException();
        }

        modelMap.put("cadre", cadre);

        if(cls!=null){
            // 判断权限
            if(cls==1){ // 党委
                //partyMemberService.get

            }else if(cls==2){

            }else{
                throw new UnauthorizedException();
            }
            int userId = cadre.getUserId();
            if(branchId !=null) {
                MetaType branchSecretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");
                BranchMember branchSecretary =
                        iPartyMapper.findBranchMember(branchSecretaryType.getId(), branchId, userId);
                 modelMap.put("branchSecretary", branchSecretary);
            }

            return "party/partyMember/partyMember_view";
        }

        return "cadre/cadre_view";
    }

    // 基本信息
    //@RequiresPermissions("cadre:archive")
    @RequestMapping("/cadre_base")
    public String cadre_base(int cadreId, ModelMap modelMap) {

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        if(cadre.getUserId().intValue()!=ShiroHelper.getCurrentUserId()
                && !ShiroHelper.isPermittedAny(new String[]{
                    SystemConstants.PERMISSION_CADREARCHIVE,
                    SystemConstants.PERMISSION_PARTYMEMBERARCHIVE})){

            throw new UnauthorizedException();
        }

        cadreCommonService.cadreBase(cadreId, modelMap);

        return "cadre/cadre_base";
    }

    // 提任（现任处级干部->现任校领导 或 现任科级干部->现任处级干部）
    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_promote")
    public String cadre_promote(int id, ModelMap modelMap) {

        CadreView cadre = iCadreMapper.getCadre(id);
        modelMap.put("cadre", cadre);

        return "cadre/cadre_promote";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_promote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_promote(int id, String title, HttpServletRequest request) {

        cadreService.promote(id, StringUtils.trimToNull(title));

        logger.info(addLog(LogConstants.LOG_ADMIN, "干部提任：%s", id));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);

        return resultMap;
    }

    // 离任
    @RequiresPermissions("cadre:leave")
    @RequestMapping("/cadre_leave")
    public String cadre_leave(int id, ModelMap modelMap) {

        CadreView cadre = iCadreMapper.getCadre(id);
        modelMap.put("cadre", cadre);

        TreeNode dispatchCadreTree = cadreCommonService.getDispatchCadreTree(id, DispatchConstants.DISPATCH_CADRE_TYPE_DISMISS);
        modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));

        // 获取该干部的所有岗位
        List<CadrePost> cadreMainCadrePosts = cadrePostService.getCadreMainCadrePosts(id);
        List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(id);
        List<CadrePost> cadrePosts = new ArrayList<>();

        for (CadrePost mainCadrePost : cadreMainCadrePosts) {
            if (mainCadrePost.getUnitPostId() != null) {
                cadrePosts.add(mainCadrePost);
            }
        }
        for (CadrePost subCadrePost : subCadrePosts) {
            if (subCadrePost.getUnitPostId() != null) {
                cadrePosts.add(subCadrePost);
            }
        }
        modelMap.put("cadrePosts", cadrePosts);

        return "cadre/cadre_leave";
    }

    @RequiresPermissions("cadre:leave")
    @RequestMapping(value = "/cadre_leave", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_leave(int id, String title, Integer dispatchCadreId,
                              String _deposeDate, String _appointDate, String originalPost,
                              Integer[] postIds,
                              HttpServletRequest request) {

        // if(status==null) status=CadreConstants.CADRE_STATUS_LEAVE;

        byte status = cadreService.leave(id, StringUtils.trimToNull(title), dispatchCadreId,
                _deposeDate, _appointDate, originalPost,  postIds);

       logger.info(addLog(LogConstants.LOG_ADMIN, "干部离任：%s", id));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("status", status);

        return resultMap;
    }

    // 离任干部发文
    @RequiresPermissions("cadre:leave")
    @RequestMapping("/cadre_leave_dispatch")
    @ResponseBody
    public Dispatch cadre_leave_dispatch(int id) {

        DispatchCadre dispatchCadre = dispatchCadreMapper.selectByPrimaryKey(id);

        return dispatchCadre.getDispatch();
    }

    // 在“离任干部库”和“离任校领导干部库”中加一个按钮“重新任用”，点击这个按钮，可以转移到“考察对象”中去。
    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_re_assign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_re_assign(Integer[] ids) {

        cadreService.re_assign(ids);

        logger.info(addLog(LogConstants.LOG_ADMIN, "干部重新任用：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_au(Cadre record,
                           Integer[] unitIds,
                           HttpServletRequest request) {

        record.setIsDep(BooleanUtils.isTrue(record.getIsDep()));
        record.setIsDouble(BooleanUtils.isTrue(record.getIsDouble()));
        if(record.getIsDouble()){
            /*if(unitIds==null || unitIds.length==0) {
                return failed("请选择双肩挑单位");
            }*/
            record.setDoubleUnitIds(StringUtils.join(unitIds, ","));
        }
        record.setLabel(StringUtils.trimToEmpty(record.getLabel()));

        Integer id = record.getId();
        if (id == null) {

            cadreService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部：%s", record.getId()));
        } else {
            record.setUserId(null); // 不能修改账号、干部类别
            record.setStatus(null);
            cadreService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_au")
    public String cadre_au(Integer id, Byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        if (id != null) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(id);
            modelMap.put("cadre", cadre);
            modelMap.put("status", cadre.getStatus());

            modelMap.put("sysUser", sysUserService.findById(cadre.getUserId()));
        }
        if (id != null && (status == CadreConstants.CADRE_STATUS_CJ_LEAVE || status == CadreConstants.CADRE_STATUS_LEADER_LEAVE)) {
            TreeNode dispatchCadreTree = cadreCommonService.getDispatchCadreTree(id, DispatchConstants.DISPATCH_CADRE_TYPE_DISMISS);
            modelMap.put("tree", JSONUtils.toString(dispatchCadreTree));
        }

        // MAP<unitTypeId, List<unitId>>
        Map<Integer, List<Integer>> unitListMap = new LinkedHashMap<>();
        Map<Integer, List<Integer>> historyUnitListMap = new LinkedHashMap<>();
        Map<Integer, Unit> unitMap = unitService.findAll();
        for (Unit unit : unitMap.values()) {

            Integer unitTypeId = unit.getTypeId();
            if (unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY){
                List<Integer> units = historyUnitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    historyUnitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            }else {
                List<Integer> units = unitListMap.get(unitTypeId);
                if (units == null) {
                    units = new ArrayList<>();
                    unitListMap.put(unitTypeId, units);
                }
                units.add(unit.getId());
            }
        }
        modelMap.put("unitListMap", unitListMap);
        modelMap.put("historyUnitListMap", historyUnitListMap);

        return "cadre/cadre_au";
    }

    // 更换工号
    @RequiresPermissions("cadre:changeCode")
    @RequestMapping("/cadre_changeCode")
    public String cadre_changeCode(int cadreId, ModelMap modelMap) {

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);

        return "cadre/cadre_changeCode";
    }

    @RequiresPermissions("cadre:changeCode")
    @RequestMapping(value = "/cadre_changeCode", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_changeCode(int userId, int newUserId, String remark) {

        cadreService.changeCode(userId, newUserId, remark);

        return success(FormUtils.SUCCESS);
    }

    // 干部库转移
    @RequiresPermissions("cadre:edit")
    @RequestMapping("/cadre_transfer")
    public String cadre_transfer() {

        return "cadre/cadre_transfer";
    }

    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/cadre_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_transfer(int cadreId, byte status) {

        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
        byte cadreStatus = cadre.getStatus();
        if (!CadreConstants.CADRE_STATUS_SET.contains(cadreStatus) ||
                !CadreConstants.CADRE_STATUS_SET.contains(status)) {
            return failed("不允许转移。");
        }
        if (cadreStatus == status) {
            return failed("不允许转移至相同的干部库。");
        }

        Cadre record = new Cadre();
        record.setId(cadre.getId());
        record.setStatus(status);
        record.setIsDouble(cadre.getIsDouble());
        record.setSortOrder(getNextSortOrder(CadreService.TABLE_NAME, "status=" + status));
        cadreService.updateByPrimaryKeySelective(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:del")
    @RequestMapping(value = "/cadre_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:changeOrder")
    @RequestMapping(value = "/cadre_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadre:import")
    @RequestMapping("/cadre_import")
    public String cadre_import(byte status, ModelMap modelMap) {

        modelMap.put("status", status);
        return "cadre/cadre_import";
    }

    @RequiresPermissions("cadre:import")
    @RequestMapping(value = "/cadre_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadre_import(HttpServletRequest request, Byte status) throws InvalidFormatException, IOException {

        //User sessionUser = getAdminSessionUser(request);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<Cadre> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            Cadre record = new Cadre();

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            int userId = uv.getId();
            record.setUserId(userId);

            // 留空默认是机关干部
            record.setIsDep(StringUtils.contains(StringUtils.trimToNull(xlsRow.get(2)), "院系"));

            boolean useCadreState = BooleanUtils.isTrue(CmTag.getBoolProperty("useCadreState"));

            int stateCol = 4;
            int titleCol = 5;
            int remarkCol = 11;

            if(useCadreState) { // 有人员类别
                stateCol = 2;
                titleCol = 3;
                remarkCol = 4;
            }else{ // 没有
                titleCol = 3;
                remarkCol = 9;
            }

            if(useCadreState) {
                String _state = StringUtils.trim(xlsRow.get(stateCol));
                MetaType state = CmTag.getMetaTypeByName("mc_cadre_state", _state);
                if (state != null) {
                    record.setState(state.getId());
                }
            }

            record.setTitle(StringUtils.trimToNull(xlsRow.get(titleCol)));

            String _isDouble = StringUtils.trimToNull(xlsRow.get(titleCol+1));
            record.setIsDouble(StringUtils.equals(_isDouble, "是"));

            if (record.getIsDouble()) {

                List<Integer> doubleUnitIds = new ArrayList<>();

                String unitCode = StringUtils.trimToNull(xlsRow.get(titleCol+3));
                if (StringUtils.isNotBlank(unitCode)) {
                    Unit unit = unitService.findRunUnitByCode(unitCode);
                    if (unit == null) {
                        throw new OpException("第{0}行双肩挑单位1编码[{1}]不存在", row, unitCode);
                    }
                    doubleUnitIds.add(unit.getId());
                }

                unitCode = StringUtils.trimToNull(xlsRow.get(titleCol+5));
                if (StringUtils.isNotBlank(unitCode)) {
                    Unit unit = unitService.findRunUnitByCode(unitCode);
                    if (unit == null) {
                        throw new OpException("第{0}行双肩挑单位2编码[{1}]不存在", row, unitCode);
                    }
                    doubleUnitIds.add(unit.getId());
                }

                if (doubleUnitIds.size() > 0) {
                    record.setDoubleUnitIds(StringUtils.join(doubleUnitIds, ","));
                }
            }

            record.setRemark(StringUtils.trimToNull(xlsRow.get(remarkCol)));

            record.setStatus(status);
            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = cadreService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入干部成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    // 根据单位、行政级别批量排序
    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/batchSortByAdminLevel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchSortByAdminLevel(byte status, HttpServletRequest request) {

        cadreService.batchSortByAdminLevel(status);
        return success(FormUtils.SUCCESS);
    }

    // 根据单位、行政级别批量排序
    @RequiresPermissions("cadre:edit")
    @RequestMapping(value = "/batchSortByUnit", method = RequestMethod.POST)
    @ResponseBody
    public Map batchSortByUnit(byte status, HttpServletRequest request) {

        cadreService.batchSortByUnit(status);
        return success(FormUtils.SUCCESS);
    }
}
