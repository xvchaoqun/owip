package controller.cadreReserve;

import controller.BaseController;
import controller.analysis.CadreCategorySearchBean;
import controller.global.OpException;
import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.cadreInspect.CadreInspect;
import domain.cadreReserve.CadreReserve;
import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
import freemarker.template.TemplateException;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import persistence.cadre.common.CadreReserveCount;
import persistence.cadre.common.ICadreWorkMapper;
import service.cadre.CadreAdformService;
import service.cadre.CadreInfoFormService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CadreReserveController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CadreAdformService cadreAdformService;
    @Autowired
    private CadreInfoFormService cadreInfoFormService;
    @Autowired
    private ICadreWorkMapper iCadreWorkMapper;

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve/search")
    public String search() {

        return "cadreReserve/cadreReserve_search";
    }

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping(value = "/cadreReserve/search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_search(int cadreId) {

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        String msg = "";
        CadreView cadre = iCadreMapper.getCadre(cadreId);

        if (cadre == null) {
            msg = "该用户不存在";
        } else {
            resultMap.put("realname", cadre.getRealname());

            if (cadre == null) {
                msg = "该用户不是年轻干部";
            } else {
                CadreReserve cadreReserve = cadreReserveService.getNormalRecord(cadre.getId());
                if (cadreReserve == null) {
                    msg = "该用户不是年轻干部";
                } else {
                    resultMap.put("cadreId", cadre.getId());
                    resultMap.put("reserveType", cadreReserve.getType());
                }
            }
        }
        resultMap.put("msg", msg);

        return resultMap;
    }

    // 转移
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_transfer")
    public String cadreReserve_transfer(Integer[] ids, ModelMap modelMap) {

        if (ids != null && ids.length == 1) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(ids[0]);
            if (cadreReserve != null) {
                modelMap.put("cadre", CmTag.getCadreById(cadreReserve.getCadreId()));
            }
        }

        return "cadreReserve/cadreReserve_transfer";
    }

    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_transfer(Integer[] ids, int type) {

        Map<Integer, MetaType> reserveTypeMap = CmTag.getMetaTypes("mc_cadre_reserve_type");
        if (!reserveTypeMap.containsKey(type)) {
            return failed("转移库不存在。");
        }

        if (ids != null) {
            for (Integer id : ids) {

                CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
                if (cadreReserve != null) {
                    if (cadreReserve.getType() == type) {
                        return failed("不允许转移至相同的库。");
                    }

                    cadreReserveService.updateType(cadreReserve.getId(), type);
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve")
    public String cadreReserve(Byte reserveStatus, Integer reserveType,
                               Integer[] dpTypes,
                               String[] staffTypes,
                               String[] nation,
                               Integer[] labels, // 标签
                               String[] authorizedTypes,
                               Integer[] unitTypes,
                               Integer[] unitIds,
                               Integer[] adminLevels,
                               Integer[] maxEdus,
                               String[] proPosts,
                               Integer[] postTypes,
                               String[] proPostLevels,
                               Integer[] workTypes,
                               Byte[] leaderTypes,

                               Integer cadreId, ModelMap modelMap) {

        Map<Integer, MetaType> cadreReserveTypeMap = metaTypeService.metaTypes("mc_cadre_reserve_type");

        if (reserveStatus == null && reserveType == null) {
            // 默认页面
            reserveType = new ArrayList<>(cadreReserveTypeMap.keySet()).get(0);
        }
        if (reserveType != null) {
            // 正常状态的年轻干部库，读取指定的类别
            reserveStatus = CadreConstants.CADRE_RESERVE_STATUS_NORMAL;
        }
        if (reserveStatus != CadreConstants.CADRE_RESERVE_STATUS_NORMAL) {
            // 非正常状态的年轻干部库，读取全部的类别
            reserveType = null;
        }

        modelMap.put("reserveStatus", reserveStatus);
        modelMap.put("reserveType", reserveType);

        if (dpTypes != null) {
            modelMap.put("selectDpTypes", Arrays.asList(dpTypes));
        }
        modelMap.put("staffTypes", CmTag.getPropertyCaches("staffTypes"));
        if(staffTypes!=null){
            modelMap.put("selectStaffTypes", Arrays.asList(staffTypes));
        }
        if (nation != null) {
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        if(labels!=null){
            modelMap.put("selectLabels", Arrays.asList(labels));
        }
        modelMap.put("authorizedTypes", CmTag.getPropertyCaches("authorizedTypes"));
        if(authorizedTypes!=null){
            modelMap.put("selectAuthorizedTypes", Arrays.asList(authorizedTypes));
        }
        if (unitTypes != null) {
            modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
        }
        if (unitIds != null) {
            modelMap.put("selectUnitIds", Arrays.asList(unitIds));
        }
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
        if (adminLevels != null) {
            modelMap.put("selectAdminLevels", Arrays.asList(adminLevels));
        }
        if (maxEdus != null) {
            modelMap.put("selectMaxEdus", Arrays.asList(maxEdus));
        }
        modelMap.put("proPosts", CmTag.getPropertyCaches("teacherProPosts"));
        if (proPosts != null) {
            modelMap.put("selectProPosts", Arrays.asList(proPosts));
        }
        if (postTypes != null) {
            modelMap.put("selectPostTypes", Arrays.asList(postTypes));
        }
        modelMap.put("proPostLevels", CmTag.getPropertyCaches("teacherProPostLevels"));
        if (proPostLevels != null) {
            modelMap.put("selectProPostLevels", Arrays.asList(proPostLevels));
        }
        if (workTypes != null) {
            modelMap.put("selectWorkTypes",Arrays.asList(workTypes));
        }
        if (leaderTypes != null) {
            modelMap.put("selectLeaderTypes", Arrays.asList(leaderTypes));
        }

        if (unitTypes != null) {
            modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
        }

        modelMap.put("staffStatuses", CmTag.getPropertyCaches("staffStatuses"));
        modelMap.put("isTemps", CmTag.getPropertyCaches("isTemps"));

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
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

        Map<Byte, Integer> statusCountMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : CadreConstants.CADRE_RESERVE_STATUS_MAP.entrySet()) {
            statusCountMap.put(entry.getKey(), 0);
        }
        Map<Integer, Integer> normalCountMap = new HashMap<>();
        for (Map.Entry<Integer, MetaType> entry : cadreReserveTypeMap.entrySet()) {
            normalCountMap.put(entry.getKey(), 0);
        }
        List<CadreReserveCount> cadreReserveCounts = iCadreMapper.selectCadreReserveCount();
        for (CadreReserveCount crc : cadreReserveCounts) {
            Byte st = crc.getStatus();
            if (st == CadreConstants.CADRE_RESERVE_STATUS_NORMAL) {
                Integer type = crc.getType();
                Integer count = normalCountMap.get(type);
                if (count == null) count = 0; // 不可能的情况
                normalCountMap.put(type, count + crc.getNum());
            }
            Integer stCount = statusCountMap.get(st);
            if (stCount == null) stCount = 0;
            statusCountMap.put(st, stCount + crc.getNum());
        }
        modelMap.put("statusCountMap", statusCountMap);
        modelMap.put("normalCountMap", normalCountMap);

        return "cadreReserve/cadreReserve_page";
    }

    @RequiresPermissions("cadreReserve:list")
    @RequestMapping("/cadreReserve_data")
    public void cadreReserve_data(HttpServletResponse response, HttpServletRequest request, Byte reserveStatus, Integer reserveType,
                                  Byte gender,
                                  Integer[] dpTypes, // 党派
                                  String[] staffTypes, // 标签
                                  String[] nation,
                                  String title,
                                  Integer[] labels, // 标签
                                  Integer state,
                                  String[] authorizedTypes, // 标签
                                  Integer[] unitTypes, // 部门属性
                                  @RequestDateRange DateRange _birth,
                                  @RequestDateRange DateRange _cadreGrowTime,
                                  Integer[] unitIds, // 所在单位
                                  Integer startAge,
                                  Integer endAge,
                                  Integer startDpAge, // 党龄
                                  Integer endDpAge, // 党龄
                                  Integer[] adminLevels, // 行政级别
                                  Integer[] maxEdus, // 最高学历
                                  Byte degreeType,
                                  String major, // 所学专业
                                  String[] proPosts, // 专业技术职务
                                  Integer[] postTypes, // 职务属性
                                  Integer startNowPostAge,
                                  Integer endNowPostAge,
                                  String[] proPostLevels, // 职称级别
                                  Byte firstUnitPost, // 第一主职是否已关联岗位（1：关联 0： 没关联 -1：缺第一主职）
                                  Boolean isPrincipal, // 是否正职
                                  Integer startNowLevelAge,
                                  Integer endNowLevelAge,
                                  Boolean isDouble, // 是否双肩挑
                                  Boolean andWorkTypes,
                                  Integer[] workTypes,
                                  String workDetail,
                                  Byte[] leaderTypes, // 是否班子负责人
                                  Boolean isDep,
                                  Boolean hasCrp, // 是否有干部挂职经历
                                  Boolean hasAbroadEdu, // 是否有国外学习经历
                                  Integer cadreId,
                                  String staffStatus,
                                  String isTemp,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  @RequestParam(required = false, defaultValue = "1") int format, // 导出格式
                                  Integer[] ids, // 导出的记录
                                  Integer[] cols, // 选择导出的列
                                  Integer pageSize, Integer pageNo) throws IOException, TemplateException, DocumentException {

        Map<Integer, MetaType> cadreReserveTypeMap = metaTypeService.metaTypes("mc_cadre_reserve_type");

        if (reserveStatus == null && reserveType == null) {
            // 默认页面
            reserveType = new ArrayList<>(cadreReserveTypeMap.keySet()).get(0);
        }
        if (reserveType != null) {
            // 正常状态的年轻干部库，读取指定的类别
            reserveStatus = CadreConstants.CADRE_RESERVE_STATUS_NORMAL;
        }
        if (reserveStatus != CadreConstants.CADRE_RESERVE_STATUS_NORMAL) {
            // 非正常状态的年轻干部库，读取全部的类别
            reserveType = null;
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreReserveViewExample example = new CadreReserveViewExample();
        CadreReserveViewExample.Criteria criteria = example.createCriteria();

        if (reserveStatus != null)
            criteria.andReserveStatusEqualTo(reserveStatus);
        if (reserveStatus == null || reserveStatus == CadreConstants.CADRE_RESERVE_STATUS_NORMAL)
            criteria.andReserveTypeEqualTo(reserveType);

        example.setOrderByClause("reserve_sort_order asc");

        if (gender != null){
            criteria.andGenderEqualTo(gender);
        }
        if (dpTypes != null) {
            criteria.andDpTypeIdIn(new HashSet<>(Arrays.asList(dpTypes)));
        }
        if (staffTypes != null) {
            criteria.andStaffTypeIn(Arrays.asList(staffTypes));
        }
        if (nation != null) {

            Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_nation");
            Set<String> nations = metaTypeMap.values()
                    .stream().map(MetaType::getName).collect(Collectors.toSet());

            criteria.andNationIn(Arrays.asList(nation), nations);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(SqlUtils.trimLike(title));
        }
        if (labels != null) {
            criteria.andLabelsContain(new HashSet<>(Arrays.asList(labels)));
        }
        if (state != null) {
            criteria.andStateEqualTo(state);
        }
        if (authorizedTypes != null) {
            criteria.andAuthorizedTypeIn(Arrays.asList(authorizedTypes));
        }
        if (unitTypes != null) {
            criteria.andUnitTypeIdIn(Arrays.asList(unitTypes));
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
        if (unitIds != null) {
            criteria.andUnitIdIn(Arrays.asList(unitIds));
        }
        if (endAge != null) {
            //  >= 不含（减一）
            criteria.andBirthGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endAge + 1)));
        }
        if (startAge != null) {
            // <= 包含
            criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startAge));
        }
        if (endDpAge != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * (endDpAge + 1)));
        }
        if (startDpAge != null) {
            criteria.andGrowTimeLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -1 * startDpAge));
        }
        if (adminLevels != null) {
            criteria.andAdminLevelIn(Arrays.asList(adminLevels));
        }
        if(degreeType!=null){
            if(degreeType==-1){
                criteria.andDegreeTypeIsNull();
            }else{
                criteria.andDegreeTypeEqualTo(degreeType);
            }
        }
        if(StringUtils.isNotBlank(major)){
            criteria.andMajorLikeIn(major);
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
        if (maxEdus != null) {
            if(new HashSet<>(Arrays.asList(maxEdus)).contains(-1)){
                criteria.andEduIdIsNull();
            }else {
                criteria.andEduIdIn(Arrays.asList(maxEdus));
            }
        }
        if (postTypes != null) {
            criteria.andPostTypeIn(Arrays.asList(postTypes));
        }
        if (endNowPostAge != null) {
            criteria.andCadrePostYearLessThanOrEqualTo(endNowPostAge);
        }
        if (startNowPostAge != null) {
            criteria.andCadrePostYearGreaterThanOrEqualTo(startNowPostAge);
        }
        if (proPostLevels != null) {
            criteria.andProPostLevelIn(Arrays.asList(proPostLevels));
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
        if (isPrincipal != null) {
            criteria.andIsPrincipalEqualTo(isPrincipal);
        }
        if (endNowLevelAge != null) {
            criteria.andAdminLevelYearLessThanOrEqualTo(endNowLevelAge);
        }
        if (startNowLevelAge != null) {
            criteria.andAdminLevelYearGreaterThanOrEqualTo(startNowLevelAge);
        }
        if (isDouble != null) {
            criteria.andIsDoubleEqualTo(isDouble);
        }
        if (workTypes != null){
            List<Integer> cadreIds = iCadreWorkMapper.getCadreIdsOfWorkTypes(Arrays.asList(workTypes),
                    BooleanUtils.isTrue(andWorkTypes), CadreConstants.CADRE_STATUS_RESERVE);
            if(cadreIds.size()==0){
                criteria.andIdIsNull();
            }else {
                criteria.andIdIn(cadreIds);
            }
        }
        if (StringUtils.isNotBlank(workDetail)){
            String[] workDetails=workDetail.split(SystemConstants.STRING_SEPARTOR);
            List<String> detailList= Arrays.asList(workDetails);
            List<Integer> cadreIds = iCadreWorkMapper.getCadreIdsOfWorkDetail(detailList, CadreConstants.CADRE_STATUS_RESERVE);
            if(cadreIds.size()==0){
                criteria.andIdIsNull();
            }else {
                criteria.andIdIn(cadreIds);
            }
        }
        if (leaderTypes != null) {
            criteria.andLeaderTypeIn(Arrays.asList(leaderTypes));
        }
        if(isDep!=null){
            criteria.andIsDepEqualTo(isDep);
        }
        if (hasCrp != null) {
            criteria.andHasCrpEqualTo(hasCrp);
        }
        if(hasAbroadEdu!=null){

            CadreCategorySearchBean searchBean = new CadreCategorySearchBean();
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

        if (unitTypes != null) {
            criteria.andUnitTypeIdIn(Arrays.asList(unitTypes));
        }
        if(StringUtils.isNotBlank(staffStatus)){
            criteria.andStaffStatusEqualTo(staffStatus);
        }
        if(StringUtils.isNotBlank(isTemp)){
            criteria.andIsTempEqualTo(isTemp);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andReserveIdIn(Arrays.asList(ids));
            cadreReserve_export(format, reserveStatus, reserveType, cols, example, response);
            return;
        }else if (export == 2 || export == 3 || export == 6){
            if (ids != null && ids.length > 0) {
                criteria.andReserveIdIn(Arrays.asList(ids));
            }
            List<CadreReserveView> cadreReserves = cadreReserveViewMapper.selectByExample(example);
            Integer[] cadreIds = new Integer[cadreReserves.size()];
            int i = 0;
            for (CadreReserveView cadreReserve : cadreReserves) {
                cadreIds[i++] = cadreReserve.getId();
            }

            //reserveType字段用来区分文件名称
            if (export == 2){
                //干部任免审批表
                cadreAdformService.export(cadreIds, reserveType, format==1, request, response);
            }else if (export == 3){
                // 干部信息采集表
                cadreInfoFormService.export(cadreIds, reserveType, request, response);
            }else if (export == 6){
                // 干部信息表(简版)
                cadreInfoFormService.export_simple(cadreIds, reserveType, request,response);
            }
            return;
        }

        long count = cadreReserveViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReserveView> Cadres = cadreReserveViewMapper.
                selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Cadres);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);


        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }


    // 只有干部库中类型为年轻干部时，才可以修改干部库的信息
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_au(boolean _isCadre,
                                  Integer cadreId,
                                  Integer userId, Integer reserveId, Integer reserveType, String reserveRemark,
                                  Cadre cadreRecord, HttpServletRequest request) {
        if (_isCadre) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
            userId = cadre.getUserId();
        }
        CadreReserve record = new CadreReserve();
        record.setId(reserveId);
        record.setType(reserveType);
        record.setRemark(reserveRemark);
        if (reserveId == null) {
            cadreReserveService.insertOrUpdateSelective(userId, record, cadreRecord);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加年轻干部：%s", record.getId()));
        } else {
            cadreReserveService.updateByPrimaryKeySelective(record, cadreRecord);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新年轻干部：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_au")
    public String cadreReserve_au(Integer id, int reserveType, ModelMap modelMap) {

        if (id != null) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            reserveType = cadreReserve.getType();
            modelMap.put("cadreReserve", cadreReserve);
            CadreView cadre = iCadreMapper.getCadre(cadreReserve.getCadreId());
            modelMap.put("cadre", cadre);
        }
        modelMap.put("reserveType", reserveType);

        return "cadreReserve/cadreReserve_au";
    }

    /*@RequiresPermissions("cadreReserve:del")
    @RequestMapping(value = "/cadreReserve_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreReserveService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除年轻干部：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_pass")
    public String cadreReserve_pass(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            CadreView cadre = iCadreMapper.getCadre(cadreReserve.getCadreId());
            modelMap.put("cadreReserve", cadreReserve);
            modelMap.put("cadre", cadre);
        }
        return "cadreReserve/cadreReserve_pass";
    }

    // 列为考察对象
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_pass(Integer reserveId, String reserveRemark,
                                    Cadre cadreRecord, HttpServletRequest request) {

        CadreReserve record = new CadreReserve();
        record.setId(reserveId);
        record.setRemark(reserveRemark);

        Cadre cadre = cadreReserveService.pass(record, cadreRecord);

        SysUserView user = cadre.getUser();
        logger.info(addLog(LogConstants.LOG_ADMIN, "年轻干部列为考察对象：%s-%s", user.getRealname(), user.getCode()));
        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping("/cadreReserve_inspectPass")
    public String cadreReserve_inspectPass(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreReserve cadreReserve = cadreReserveMapper.selectByPrimaryKey(id);
            Integer cadreId = cadreReserve.getCadreId();
            CadreInspect cadreInspect = cadreInspectService.getNormalRecord(cadreId);

            CadreView cadre = iCadreMapper.getCadre(cadreInspect.getCadreId());
            modelMap.put("cadreInspect", cadreInspect);
            modelMap.put("cadre", cadre);
        }

        return "cadreReserve/cadreReserve_inspectPass";
    }

    // 通过常委会任命 （没有确定考察对象的模块情况下，直接在年轻干部库-已列为考察对象中进行操作）
    @RequiresPermissions("cadreReserve:edit")
    @RequestMapping(value = "/cadreReserve_inspectPass", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_inspectPass(Integer inspectId, String inspectRemark,
                                           Cadre cadreRecord, HttpServletRequest request) {

        CadreInspect record = new CadreInspect();
        record.setId(inspectId);
        record.setRemark(inspectRemark);

        Cadre cadre = cadreInspectService.pass(record, cadreRecord);

        SysUserView user = cadre.getUser();
        logger.info(addLog(LogConstants.LOG_ADMIN, "考察对象通过常委会任命：%s-%s", user.getRealname(), user.getCode()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:abolish")
    @RequestMapping(value = "/cadreReserve_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_abolish(HttpServletRequest request, Integer id, ModelMap modelMap) {

        cadreReserveService.abolish(id);
        logger.info(addLog(LogConstants.LOG_ADMIN, "撤销年轻干部：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:abolish")
    @RequestMapping(value = "/cadreReserve_unAbolish", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_unAbolish(HttpServletRequest request, Integer id, ModelMap modelMap) {

        cadreReserveService.unAbolish(id);
        logger.info(addLog(LogConstants.LOG_ADMIN, "返回年轻干部库：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:del")
    @RequestMapping(value = "/cadreReserve_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreReserveService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除已撤销年轻干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:del")
    @RequestMapping(value = "/cadreReserve_batchDelPass", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreReserve_batchDelPass(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            cadreReserveService.batchDelPass(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除已列为考察对象的年轻干部：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReserve:changeOrder")
    @RequestMapping(value = "/cadreReserve_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreReserveService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "年轻干部调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    private void cadreReserve_export(int format, Byte reserveStatus, Integer reserveType, Integer[] cols, CadreReserveViewExample example, HttpServletResponse response) throws IOException {

        if (format == 1) {
            //SXSSFWorkbook wb= cadreReserveExportService.export(reserveType, example, ShiroHelper.isPermitted("cadre:list") ? 0 : 1, cols);//一览表
            Byte _reserveType = (byte)reserveType.intValue();
            List<CadreReserveView> cadreReserves = cadreReserveViewMapper.selectByExample(example);
            List<Integer> cadreIds = new ArrayList<>();
            for (CadreReserveView cadreReserve : cadreReserves) {
                cadreIds.add(cadreReserve.getId());
            }
            CadreViewExample cadreExample = new CadreViewExample();
            cadreExample.createCriteria().andIdIn(cadreIds);
            SXSSFWorkbook wb = cadreExportService.export(_reserveType, cadreExample, ShiroHelper.isPermitted("cadre:list") ? 0 : 1, cols, 1);
            String suffix = null;
            if (reserveType != null) {
                suffix = metaTypeService.getName(reserveType);
            }else {
                suffix = CadreConstants.CADRE_RESERVE_STATUS_MAP.get(reserveStatus);
            }
            String fileName = CmTag.getSysConfig().getSchoolName() + "优秀年轻干部";

            if (StringUtils.isNotBlank(suffix))
                fileName = CmTag.getSysConfig().getSchoolName() + "优秀年轻干部（" + suffix + "）";

            ExportHelper.output(wb, fileName + ".xlsx", response);
        }else {
            //名单
            cadreReserveExportService.export2(reserveStatus, reserveType, example, response);
        }


    }

    @RequiresPermissions("cadreReserve:import")
    @RequestMapping("/cadreReserve_import")
    public String cadreReserve_import(int reserveType, ModelMap modelMap) {

        modelMap.put("reserveType", reserveType);
        return "cadreReserve/cadreReserve_import";
    }

    @RequiresPermissions("cadreReserve:import")
    @RequestMapping(value = "/cadreReserve_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReserve_import(HttpServletRequest request, int reserveType) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<Cadre> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

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

            record.setTitle(StringUtils.trimToNull(xlsRow.get(2)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(3)));

            records.add(record);
        }

        int addCount = cadreReserveService.batchImport(records, reserveType);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", records.size());

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入年轻干部成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
