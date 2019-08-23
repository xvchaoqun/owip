package controller.dp;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.dp.*;
import domain.sys.*;
import domain.unit.Unit;
import interceptor.OrderParam;
import mixin.MemberMixin;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import service.dp.DpPartyService;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.helper.DpPartyHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/dp")
public class DpMemberController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DpPartyService dpPartyService;

    @RequiresPermissions("dpMember:list")
    @RequestMapping("/dpMember")
    public String dpMember(HttpServletResponse response,
                            Integer userId,
                           Integer partyId,
                           //1 学生 2 教职工 3 离退休 6 已转出学生 7 已转出教职工 10 全部
                           Integer cls,
                           @RequestParam(required = false, value = "nation") String[] nation,
                           @RequestParam(required = false, value = "nativePlace") String nativePlace,
                           ModelMap modelMap) {

        modelMap.put("cls", cls);

        //查询登录用户管理的民主党派
        boolean addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL);
        List<Integer> adminDpPartyIdList = dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId());

        //统计学生、教职工各个状态人数
        Map dpMemberStudentCount = iDpMemberMapper.selectDpMemberStudentCount(addPermits,adminDpPartyIdList);
        if (dpMemberStudentCount != null){
            modelMap.putAll(dpMemberStudentCount);
        }
        Map dpMemberTeacherCount = iDpMemberMapper.selectDpMemberTeacherCount(addPermits,adminDpPartyIdList);
        if (dpMemberTeacherCount != null){
            modelMap.putAll(dpMemberTeacherCount);
        }

        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        if (partyId != null){
            modelMap.put("dpParty", dpPartyMap.get(partyId));
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        if (nativePlace != null){
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            modelMap.put("selectNativePlaces", selectNativePlaces);
        }

        //确认默认显示党派成员人数最多的标签页
        if (cls == null){
            int studentNormalCount = 0;
            if (dpMemberStudentCount != null){
                studentNormalCount = ((BigDecimal) dpMemberStudentCount.get("student_normalCount")).intValue();
            }
            int teacherNormalCount = 0;
            int teacherRetireCount = 0;

            if (dpMemberStudentCount != null){
                teacherNormalCount = ((BigDecimal) dpMemberTeacherCount.get("teacher_normalCount")).intValue();
                teacherRetireCount = ((BigDecimal) dpMemberTeacherCount.get("teacher_retireCount")).intValue();
            }
            if (studentNormalCount >= teacherNormalCount
                    && studentNormalCount >= teacherRetireCount){
                cls = 1;
            } else if (teacherNormalCount > studentNormalCount
                        && teacherNormalCount >= teacherRetireCount){
                cls = 2;
            }else {
                cls = 3;
            }
        }

        modelMap.put("cls", cls);

        //导出的名字
        List<String> titles = new ArrayList<>();
        if (cls == 2 || cls == 3 || cls == 7){ //教师党派成员
            titles = getTeacherExportTitles();
            if (cls == 3){
                titles.add(6, "离退休时间|80");
            }

            modelMap.put("teacherEducationTypes", iDpPropertyMapper.teacherEducationTypes());
            modelMap.put("teacherPostClasses", iDpPropertyMapper.teacherPostClasses());
            modelMap.put("nations", iDpPropertyMapper.teacherNations());
            modelMap.put("nativePlaces", iDpPropertyMapper.teacherNativePlaces());
        } else if (cls == 1|| cls == 6){//学生党派成员
            titles = getStudentExportTitles();

            modelMap.put("studentGrades", iDpPropertyMapper.studentGrades());
            modelMap.put("studentTypes", iDpPropertyMapper.studentTypes());
            modelMap.put("nations", iDpPropertyMapper.studentNations());
            modelMap.put("nativePlaces", iDpPropertyMapper.studentNativePlaces());
        } else if (cls == 10){
            modelMap.put("nations", iDpPropertyMapper.nations());
            modelMap.put("nativePlaces", iDpPropertyMapper.nativePlaces());
        }

        modelMap.put("titles", titles);

        return "/dp/dpMember/dpMember_page";
    }

    @RequiresPermissions("dpMember:list")
    @RequestMapping("/dpMember_data")
    public void dpMember_data(HttpServletResponse response,
                                 @RequestParam(defaultValue = "dpParty") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    @RequestParam(defaultValue = "1")int cls,
                                    Integer userId,
                                    Integer partyId,
                                    Integer unitId,
                                    Byte politicalStatus,
                                    Byte gender,
                                    Byte age,
                                    @RequestParam(required = false, value = "nation") String[] nation,//
                                    @RequestParam(required = false, value = "nativePlace") String[] nativePlace,//
                                    @RequestDateRange DateRange _growTime,
                                    @RequestDateRange DateRange _positiveTime,
                                    @RequestDateRange DateRange _outHandleTime,
                                    Byte userSource, //账号来源

                                     /**学生党员**/
                                    String grade,//
                                    String studentType,
                                    String eduLevel,
                                    String eduType,

                                    /**教职工党员**/
                                    String staffType,
                                    String education,
                                    String postClass,
                                    @RequestDateRange DateRange _retireTime,
                                    Boolean isHonorRetire,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 @RequestParam(required = false) Integer[] cols, // 选择导出的列
                                 Integer pageSize, Integer pageNo)  throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpMemberViewExample example = new DpMemberViewExample();
        DpMemberViewExample.Criteria criteria = example.createCriteria();

        if (StringUtils.equalsIgnoreCase(sort, "dpParty")) {
            example.setOrderByClause(String.format("party_id, grow_time desc"));
        } else if (StringUtils.equalsIgnoreCase(sort, "growTime")) {
            example.setOrderByClause(String.format("grow_time %s", order));
        }
        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));


        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (gender != null){
            criteria.andGenderEqualTo(gender);
        }
        if (politicalStatus != null) {
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (nation != null){
            List<String> selectNations = Arrays.asList(nation);
            criteria.andNationIn(selectNations);
        }
        if (nativePlace != null){
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            criteria.andNativePlaceIn(selectNativePlaces);
        }
        if (grade != null){
            criteria.andGradeEqualTo(grade);
        }
        if (studentType != null){
            criteria.andStudentTypeEqualTo(studentType);
        }
            if (age != null) {
                switch (age) {
                    case DpConstants.DP_MEMBER_AGE_20: // 20及以下
                        criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                        break;
                    case DpConstants.DP_MEMBER_AGE_21_30:
                        criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -31))
                                .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                        break;
                    case DpConstants.DP_MEMBER_AGE_31_40:
                        criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -41))
                                .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -31));
                        break;
                    case DpConstants.DP_MEMBER_AGE_41_50:
                        criteria.andBirthGreaterThan(DateUtils.getDateBeforeOrAfterYears(new Date(), -51))
                                .andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -41));
                        break;
                    case DpConstants.DP_MEMBER_AGE_51: // 51及以上
                        criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -51));
                        break;
                    case DpConstants.DP_MEMBER_AGE_0:
                        criteria.andBirthIsNull();
                        break;
                }
            }



        if (StringUtils.isNotBlank(eduLevel)){
            criteria.andEduLevelLike(SqlUtils.like(eduLevel));
        }
        if (StringUtils.isNotBlank(eduType)){
            criteria.andEduTypeLike(SqlUtils.like(eduType));
        }
        if (StringUtils.isNotBlank(education)) {
            criteria.andEducationEqualTo(education);
        }


        if (_retireTime.getStart() != null) {
            criteria.andRetireTimeGreaterThanOrEqualTo(_retireTime.getStart());
        }

        if (_retireTime.getEnd() != null) {
            criteria.andRetireTimeLessThanOrEqualTo(_retireTime.getEnd());
        }

        if (isHonorRetire != null) {
            criteria.andIsHonorRetireEqualTo(isHonorRetire);
        }

        if (_growTime.getStart() != null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
        }

        if (_growTime.getEnd() != null) {
            criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
        }
        if (_positiveTime.getStart() != null) {
            criteria.andPositiveTimeGreaterThanOrEqualTo(_positiveTime.getStart());
        }
        if (_positiveTime.getEnd() != null) {
            criteria.andPositiveTimeLessThanOrEqualTo(_positiveTime.getEnd());
        }
        if (_outHandleTime.getStart() != null) {
            criteria.andOutHandleTimeGreaterThanOrEqualTo(_outHandleTime.getStart());
        }
        if (_outHandleTime.getEnd() != null) {
            criteria.andOutHandleTimeLessThanOrEqualTo(_outHandleTime.getEnd());
        }
        if (postClass != null){
            criteria.andPostClassEqualTo(postClass);
        }
        if (staffType != null){
            criteria.andStaffTypeEqualTo(staffType);
        }

        /*
           1 学生 2教职工 3离退休 6已转出学生 7 已转出教职工 10全部
         */
        switch (cls) {
            case 1:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_STUDENT)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_NORMAL);
                break;
            case 2:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_NORMAL)
                        .andIsRetireNotEqualTo(true);
                break;
            case 3:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_NORMAL)
                        .andIsRetireEqualTo(true);
                break;
            case 6:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_STUDENT)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_TRANSFER);
                break;
            case 7:
                criteria.andTypeEqualTo(DpConstants.DP_MEMBER_TYPE_TEACHER)
                        .andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_TRANSFER);
                break;
            case 10:
                criteria.andStatusEqualTo(DpConstants.DP_MEMBER_STATUS_NORMAL);
                break;
            default:
                criteria.andUserIdIsNull();
                break;
        }


        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andUserIdIn(Arrays.asList(ids));
            if (cls == 1 || cls == 6){
                student_export(cls, example, cols, response);
            }else if (cls == 2 || cls == 3 || cls == 7){
                teacher_export(cls, example, cols, response);
            }
            return;
        }

        long count = dpMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpMemberView> records= dpMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(DpMemberView.class, MemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping("/dpMember_changeDpParty")
    public String dpMember_changeDpParty(){

        return "dp/dpMember/dpMember_changeDpParty";
    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping(value = "/dpMember_changeDpParty", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_changeDpParty(@RequestParam(value = "ids[]") Integer[] ids,
                                         int partyId,
                                         ModelMap modelMap){
        if (null != ids){
            dpMemberService.changeDpParty(ids,partyId);
            logger.info(addLog(LogConstants.LOG_DPMEMBER, "批量校内组织关系转移：%s，%s",
                    StringUtils.join(ids,","), partyId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:edit")
    @RequestMapping(value = "/dpMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_au(DpMember record,
                              @CurrentUser SysUserView loginUser,
                              String _applyTime,
                              String _activeTime,
                              String _candidateTime,
                              String _growTime,
                              String _positiveTime,
                              String _transferTime,
                              String reason,
                              HttpServletRequest request) {

        Integer partyId = record.getPartyId();

        //权限
        Integer loginUserId = loginUser.getUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
            boolean isAdmin = dpPartyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin) throw new UnauthorizedException();
        }

        Integer userId = record.getUserId();

        if (StringUtils.isNotBlank(_applyTime)){
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_activeTime)) {
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_candidateTime)) {
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_growTime)) {
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_positiveTime)) {
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_transferTime)) {
            record.setTransferTime(DateUtils.parseDate(_transferTime, DateUtils.YYYY_MM_DD));
        }
        SysUserView sysUser = sysUserService.findById(record.getUserId());
        DpMember dpMember = dpMemberService.get(userId);
        if (dpMember == null){
            SecurityUtils.getSubject().checkPermission("dpMember:edit");
            DpMember dpMemberAdd = dpMemberMapper.selectByPrimaryKey(userId);
            if (dpMemberAdd != null ){
                return failed(sysUserService.findById(userId).getRealname() + "用户已是党派成员");
            }

            record.setStatus(DpConstants.DP_MEMBER_STATUS_NORMAL);//正常
            record.setCreateTime(new Date());
            record.setSource(DpConstants.DP_MEMBER_SOURCE_ADMIN);//后台添加
            dpMemberService.add(record);
            //dpMemberService.addModify(record.getUserId(), "后台添加");//向民主党派成员信息表添加信息

            logger.info(addLog(LogConstants.LOG_DPMEMBER, "添加党派成员信息表： %s %s %s, 添加原因：%s", sysUser.getId(), sysUser.getRealname(),
                    dpPartyService.findAll().get(partyId).getName(), reason));
        } else {
            SecurityUtils.getSubject().checkPermission("dpMember:edit");

            record.setPoliticalStatus(null);//不能修改党籍信息
            dpMemberService.updateByPrimaryKeySelective(record,reason);

            logger.info(addLog(LogConstants.LOG_DPMEMBER,"更新党派成员信息表： %s %s %s, 更新原因：%s", sysUser.getId(), sysUser.getRealname(),
                    dpPartyService.findAll().get(partyId).getName(), reason));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:edit")
    @RequestMapping("/dpMember_au")
    public String dpMember_au(Integer userId, Integer partyId, ModelMap modelMap) {

        DpMember dpMember = null;
        if (userId != null) {
            SecurityUtils.getSubject().checkPermission("dpMember:edit");

            dpMember = dpMemberMapper.selectByPrimaryKey(userId);
            partyId = dpMember.getPartyId();
            modelMap.put("sysUser", sysUserService.findById(userId));
        } else {
           SecurityUtils.getSubject().checkPermission("dpMember:edit");
        }

        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        if (partyId != null){
            modelMap.put("dpParty", dpPartyMap.get(partyId));
        }
        modelMap.put("dpMember", dpMember);

        return "dp/dpMember/dpMember_au";
    }

    @RequiresPermissions("dpMember:del")
    @RequestMapping(value = "/dpMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            dpMemberService.del(userId);
            logger.info(log( LogConstants.LOG_GROW, "删除党员信息表：{0}", userId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:del")
    @RequestMapping(value = "/dpMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] userIds, ModelMap modelMap) {


        if (null != userIds && userIds.length>0){
            List<DpMember> dpMembers = new ArrayList<>();
            for (Integer userId : userIds){
                dpMembers.add(dpMemberService.get(userId));
            }

            dpMemberService.batchDel(userIds);
            logger.info(log( LogConstants.LOG_DPMEMBER, "批量删除党派成员信息表：{0}", StringUtils.join(dpMembers, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:changeOrder")
    @RequestMapping(value = "/dpMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_changeOrder(Integer userId, Integer addNum, HttpServletRequest request) {

        logger.info(log( LogConstants.LOG_GROW, "党员信息表调序：{0}, {1}", userId, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpMember:modifyStatus")
    @RequestMapping("/dp_member_modify_status")
    public String dpMember_modify_status(int userId, ModelMap modelMap){

        DpMember dpMember = dpMemberMapper.selectByPrimaryKey(userId);
        modelMap.put("dpMember", dpMember);

        return "dp/dpMember/dp_member_modify_status";
    }

    @RequiresPermissions("dpMember:modifyStatus")
    @RequestMapping(value = "/dp_member_modify_status",method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_modify_status(int userId, byte politicalStatus, String remark){

        DpMember dpMember = dpMemberMapper.selectByPrimaryKey(userId);
        if (dpMember.getPoliticalStatus() != politicalStatus){
            dpMemberService.modifyStatus(userId, politicalStatus, remark);
        }

        return success(FormUtils.SUCCESS);
    }

    public void dpMember_export(DpMemberViewExample example, HttpServletResponse response) {

        List<DpMemberView> records = dpMemberViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属民主党派|100","政治面貌|100","类别|100","1正常|100","来源|100","增加类型|100","入党时间|100","创建时间|100","党内职务|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpMemberView record = records.get(i);
            String[] values = {
                record.getPartyId()+"",
                            record.getPoliticalStatus()+"",
                            record.getType()+"",
                            record.getStatus()+"",
                            record.getSource()+"",
                            record.getAddType()+"",
                            DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            record.getPartyPost()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党派成员信息表(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dpMember_selects")
    @ResponseBody
    public Map dpMember_selects(Integer pageSize,
                                Integer pageNo,
                                Integer partyId,
                                Byte type,//党派成员类别
                                Boolean isRetire,
                                Byte politicalStatus,
                                @RequestParam(required = false, value = "status") Byte[] status,//党派成员状态
                                Boolean noAuth,//默认需要读取权限
                                @RequestParam(defaultValue = "0", required = false) boolean needPrivate,
                                String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = searchStr.trim() + "%";

        boolean addPermits = false;
        List<Integer> adminDpPartyIdList = null;
        if (BooleanUtils.isNotTrue(noAuth)){
            addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL);
            adminDpPartyIdList = dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId());
        }

        List<Byte> statusList = null;
        if (status != null && status.length > 0){
            statusList = Arrays.asList(status);
        }

        int count = iDpMemberMapper.countDpMemberList(partyId, type, isRetire, politicalStatus, statusList,
                searchStr, addPermits, adminDpPartyIdList);

        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpMember> records = iDpMemberMapper.selectDpMemberList(partyId, type, isRetire, politicalStatus, statusList,
                searchStr, addPermits, adminDpPartyIdList, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String,Object>> options = new ArrayList<Map<String,Object>>();
        if(null != records && records.size()>0){

            for(DpMember record:records){
                Map<String, Object> option = new HashMap<>();
                SysUserView uv = sysUserService.findById(record.getUserId());
                option.put("id", record.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("username", uv.getUsername());
                option.put("locked", uv.getLocked());
                option.put("code", uv.getCode());
                option.put("realname", uv.getRealname());
                option.put("gender", uv.getGender());
                option.put("birth", uv.getBirth());
                option.put("nation", uv.getNation());

                if (needPrivate){
                    option.put("idcard",uv.getIdcard());
                    option.put("politicalStatus", record.getPoliticalStatus());
                    option.put("mobile", uv.getMobile());
                }

                if (StringUtils.isNotBlank(uv.getCode())){
                    option.put("unit", extService.getUnit(uv.getId()));
                }

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequestMapping("/dpMember_view")
    public String member_show_page(@CurrentUser SysUserView loginUser,
            HttpServletResponse response,
            int userId,
            ModelMap modelMap){

        DpMember dpMember = dpMemberService.get(userId);

        //党派管理员才可以操作
        if (ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)){
            Integer partyId = dpMember.getPartyId();
            Integer loginUserId = loginUser.getId();
            boolean isDpPartyAdmin = DpPartyHelper.isPresentDpPartyAdmin(loginUserId,partyId);
            if (!isDpPartyAdmin){
                throw new UnauthorizedException();
            }
        }
        if (dpMember.getType() == DpConstants.DP_MEMBER_TYPE_TEACHER){
            return "dp/dpMember/dpTeacher_view";
        }
        return "dp/dpMember/dpStudent_view";
    }

    @RequiresPermissions("dpMember:list")
    @RequestMapping("/dpMember/search")
    public String dpMember_search(){

        return "dp/dpMember/dpMember_search";
    }

    @RequiresPermissions("dpMember:list")
    @RequestMapping(value = "/dpMember/search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_search(int userId){

        String realname = "";
        String unit = "";
        String msg = "";
        String code = "";
        Byte userType = null;
        String status = "";
        SysUserView sysUser = sysUserService.findById(userId);
        if (sysUser == null){
            msg = "该用户不存在！";
        } else {
            code = sysUser.getCode();
            userType = sysUser.getType();
            realname = sysUser.getRealname();
            unit = sysUser.getUnit();
            DpMember dpMember = dpMemberService.get(userId);
            if (dpMember == null){
                msg = "该用户不是党派成员！";
            } else {
                Integer partyId = dpMember.getPartyId();
                DpParty dpParty = dpPartyService.findAll().get(partyId);
                msg = dpParty.getName();

                //查询状态
                if (dpMember.getStatus() == DpConstants.DP_MEMBER_STATUS_NORMAL){
                    status = "正常";
                } else if (dpMember.getStatus() == DpConstants.DP_MEMBER_STATUS_TRANSFER){
                    status = "已转出";
                } else if (dpMember.getStatus() == DpConstants.DP_MEMBER_STATUS_QUIT){
                    status = "已出党";
                }

                if (dpMember.getType() == DpConstants.DP_MEMBER_TYPE_TEACHER){
                    DpMemberView dpMemberView = iDpMemberMapper.getDpMemberView(userId);
                    if (dpMemberView.getIsRetire())
                        status = "已退休";
                }
            }
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("msg", msg);
        resultMap.put("userType", userType);
        resultMap.put("code", code);
        resultMap.put("realname", realname);
        resultMap.put("unit", unit);
        resultMap.put("status", status);
        return resultMap;
    }

    //党派成员基本信息
    @RequestMapping("/dpMember_base")
    public String dpMember_base(Integer userId, ModelMap modelMap){

        DpMemberView dpMemberView = iDpMemberMapper.getDpMemberView(userId);
        Integer partyId = dpMemberView.getPartyId();
        modelMap.put("dpParty", dpPartyService.findById(partyId));
        modelMap.put("dpMember", dpMemberView);
        modelMap.put("uv", sysUserService.findById(userId));
        if(dpMemberView.getType() == DpConstants.DP_MEMBER_TYPE_TEACHER){
            modelMap.put("teacherInfo", teacherInfoService.get(userId));
            return "dp/dpMember/dpTeacher_base";
        }else {
        modelMap.put("studentInfo", studentInfoService.get(userId));
        return "dp/dpMember/dpStudent_base";}

    }

    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping("/dpMember_import")
    public String dpMember_import(boolean inSchool,
                                  ModelMap modelMap){

        modelMap.put("inSchool", inSchool);

        return "dp/dpMember/dpMember_import";
    }

    //导入校内账号的党派成员信息
    @RequiresPermissions(SystemConstants.PERMISSION_DPPARTYVIEWALL)
    @RequestMapping(value = "/dpMember_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpMember_import(boolean inSchool, HttpServletRequest request) throws InvalidFormatException, IOException {

        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        Map<String, DpParty> runPartyMap = new HashMap<>();
        for (DpParty dpParty : dpPartyMap.values()){
            if (BooleanUtils.isNotTrue(dpParty.getIsDeleted())){
                runPartyMap.put(dpParty.getCode(), dpParty);
            }
        }

        Map<String, Byte> politicalStatusMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : DpConstants.DP_MEMBER_POLITICAL_STATUS_MAP.entrySet()){
            politicalStatusMap.put(entry.getValue(),entry.getKey());
        }

        MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multiPartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(pkg);

        XSSFSheet sheet = wb.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<String, Object> resultMap = null;

        if (inSchool){
            resultMap = importInSchoolDpMember(xlsRows,runPartyMap,politicalStatusMap);
        } else {
            resultMap = importOutSchoolDpMember(xlsRows,runPartyMap,politicalStatusMap);
        }

        int successCount = (int)resultMap.get("successCount");
        int total = (int)resultMap.get("total");

        logger.info(log(LogConstants.LOG_ADMIN,"导入党派成员成功，总共{0}条记录，其中成功导入{1}条记录", total, successCount));
        return resultMap;
    }

    public Map<String, Object> importOutSchoolDpMember(List<Map<Integer, String>> xlsRows,
                                                      Map<String, DpParty> runPartyMap,
                                                      Map<String, Byte> politicalStatusMap) {

        Date now = new Date();
        List<DpMember> records = new ArrayList<>();
        List<TeacherInfo> teacherInfos = new ArrayList<>();
        List<SysUserInfo> sysUserInfos = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            row++;
            DpMember record = new DpMember();
            TeacherInfo teacherInfo = new TeacherInfo();
            SysUserInfo sysUserInfo = new SysUserInfo();

            int col = 0;
            /*for (int i = 0; i<xlsRow.size();i ++){
                System.out.println(StringUtils.trim(xlsRow.get(i)) + "," + i);
            }*/
            String userCode = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(userCode)){
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            int userId = uv.getId();
            if (uv == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            SysUserView sysUser = sysUserService.findById(userId);
            Byte source = sysUser.getSource();
            if (source == SystemConstants.USER_TYPE_BKS || source == SystemConstants.USER_SOURCE_YJS
                    || source == SystemConstants.USER_TYPE_JZG){
                throw new OpException("第{0}行学工号[{1}]是学校账号", row, userCode);
            }
            record.setUserId(userId);
            teacherInfo.setUserId(userId);
            sysUserInfo.setUserId(userId);

            String realname = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(realname)){
                throw new OpException("第{0}行姓名为空", row);
            }
            sysUserInfo.setRealname(realname);

            String gender = StringUtils.trim(xlsRow.get(col++));
            if(StringUtils.contains(gender, "男")){
                sysUserInfo.setGender(SystemConstants.GENDER_MALE);
            } else if (StringUtils.contains(gender, "女")){
                sysUserInfo.setGender(SystemConstants.GENDER_FEMALE);
            } else {
                sysUserInfo.setGender(SystemConstants.GENDER_UNKNOWN);
            }
            sysUserInfo.setBirth(DateUtils.parseStringToDate(StringUtils.trim(xlsRow.get(col++))));
            sysUserInfo.setNativePlace(StringUtils.trimToNull(xlsRow.get(col++)));

            sysUserInfo.setNation(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setIdcard(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setMobile(StringUtils.trimToNull(xlsRow.get(col++)));
            sysUserInfo.setCountry(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setFromType(StringUtils.trimToNull(xlsRow.get(col++))); // 学员结构，本校、境内、京外等

            sysUserInfo.setUnit(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setEducation(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setDegree(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setDegreeTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            teacherInfo.setMajor(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setSchool(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setArriveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            teacherInfo.setAuthorizedType(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setStaffType(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setStaffStatus(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setPostClass(StringUtils.trimToNull(xlsRow.get(col++)));

            teacherInfo.setProPost(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setTitleLevel(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setAddress(StringUtils.trimToNull(xlsRow.get(col++)));

            sysUserInfo.setEmail(StringUtils.trimToNull(xlsRow.get(col++)));
            teacherInfo.setIsRetire(StringUtils.equals(xlsRow.get(col++), "是"));
            teacherInfo.setRetireTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

            teacherInfo.setIsHonorRetire(StringUtils.equals(xlsRow.get(col++), "是"));
            //判断xlsx文件和系统内是否包含所要导入的信息
            String _politicalStatus = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_politicalStatus)){
                throw new OpException("第{0}行党籍状态为空", row);
            }
            Byte politicalStatus = politicalStatusMap.get(_politicalStatus);
            if (politicalStatus == null){
                throw new OpException("第{0}行党籍状态[{1}]有误", row, _politicalStatus);
            }
            record.setPoliticalStatus(politicalStatus);

            String partyCode = StringUtils.trim(xlsRow.get(col++));
            if (StringUtils.isBlank(partyCode)){
                throw new OpException("第{0}行民主党派编码为空", row);
            }
            DpParty dpParty = runPartyMap.get(partyCode);
            if (dpParty == null){
                throw new OpException("第{0}行民主党派编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(dpParty.getId());

            record.setTransferTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));

            record.setSponsor(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPartyPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartyReward(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setOtherReward(StringUtils.trimToNull(xlsRow.get(col++)));

            record.setCreateTime(now);

            //record.setType();
            record.setStatus(DpConstants.DP_MEMBER_STATUS_NORMAL);
            // 默认为原有党员导入
            record.setAddType(CmTag.getMetaTypeByCode("mt_member_add_type_old").getId());
            record.setSource(DpConstants.DP_MEMBER_SOURCE_IMPORT);

            records.add(record);
            teacherInfos.add(teacherInfo);
            sysUserInfos.add(sysUserInfo);
        }

        int successCount = dpMemberService.batchImportOutSchool(records, teacherInfos, sysUserInfos);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }
        //导入校内账号的党派成员
    public Map<String, Object> importInSchoolDpMember(List<Map<Integer, String>> xlsRows,
                                                      Map<String, DpParty> runPartyMap,
                                                      Map<String, Byte> politicalStatusMap){

        Date now = new Date();
        List<DpMember> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows){
            row++;
            DpMember record = new DpMember();
            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)){
                continue;
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, userCode);
            }
            record.setUserId(uv.getUserId());

            String partyCode = StringUtils.trim(xlsRow.get(2));
            if (StringUtils.isBlank(partyCode)){
                throw new OpException("第{0}行民主党派编码为空", partyCode);
            }
            DpParty dpParty = runPartyMap.get(partyCode);
            if (dpParty == null){
                throw new OpException("第{0}行民主党派编码[{1}]不存在", row, partyCode);
            }
            record.setPartyId(dpParty.getId());

            String _politicalStatus = StringUtils.trimToNull(xlsRow.get(4));
            if (StringUtils.isBlank(_politicalStatus)){
                throw new OpException("第{0}行党籍状态为空", row);
            }
            Byte politicalStatus = politicalStatusMap.get(_politicalStatus);
            if (politicalStatus == null){
                throw new OpException("第{0}行党籍状态[{1}]有误", row, _politicalStatus);
            }
            record.setPoliticalStatus(politicalStatus);

            int col = 7;
            record.setTransferTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setApplyTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setActiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setCandidateTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setSponsor(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setGrowTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPositiveTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(col++))));
            record.setPartyPost(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setPartyReward(StringUtils.trimToNull(xlsRow.get(col++)));
            record.setOtherReward(StringUtils.trimToNull(xlsRow.get(col++)));

            record.setCreateTime(now);

            //record.setType();
            record.setStatus(DpConstants.DP_MEMBER_STATUS_NORMAL);
            // 默认为原有党员导入
            record.setAddType(CmTag.getMetaTypeByCode("mt_member_add_type_old").getId());
            record.setSource(DpConstants.DP_MEMBER_SOURCE_IMPORT);

            records.add(record);
        }
        int successCount = dpMemberService.batchImportInSchool(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    private List<String> getTeacherExportTitles() {

        return new ArrayList<>(Arrays.asList(new String[]{"工作证号|100", "姓名|120",
                "编制类别|80", "人员类别|100", "人员状态|80", "在岗情况|80", /*"岗位类别|80", "主岗等级|120",*/
                "性别|50", "出生日期|80", "年龄|50", "年龄范围|80", "民族|50", "国家/地区|80", "证件号码|150",
                "政治面貌|80", "所属民主党派|300", "所在单位|200",
                "入党时间|100",  "入党介绍人|100", "转正时间|100",
                "党内职务|100", "党内奖励|100", "其他奖励|100", "增加类型|100",
                "到校日期|80",
                "专业技术职务|120",/* "专技岗位等级|120", "管理岗位等级|120",*/ "任职级别|120",
                /*"行政职务|180",*/ "学历|120", "学历毕业学校|200",/* "学位授予学校|200",*/
                "学位|100", "学员结构|100", /*"人才类型|100", "人才称号|200", */"手机号码|100"}));
    }

    public void teacher_export(int cls, DpMemberViewExample example, Integer[] cols, HttpServletResponse response) {

        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, DpParty> dpPartyMap = dpPartyService.findAll();
        List<DpMemberView> records = dpMemberViewMapper.selectByExample(example);

        List<String> exportTitles = getTeacherExportTitles();
        if (cls == 3) {
            exportTitles.add(6, "离退休时间|80");
        }

        if (cols != null && cols.length > 0) {
            // 选择导出列
            List<String> _titles = new ArrayList<>();
            for (int col : cols) {
                _titles.add(exportTitles.get(col));
            }
            exportTitles.clear();
            exportTitles.addAll(_titles);
        }

        List<List<String>> valuesList = new ArrayList<>();
        List<Integer> dpPartyIds = new ArrayList<>();
        List<DpParty> dpParties = dpPartyMapper.selectByExample(new DpPartyExample());
        for (DpParty dpParty : dpParties){
            dpPartyIds.add(dpParty.getId());
        }
        for (DpMemberView record : records) {
            SysUserView sysUserView = record.getUser();
            TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(record.getUserId());
            Integer partyId = record.getPartyId();
            if (!dpPartyIds.contains(partyId)){
                partyId = null;
            }
            Date birth = sysUserView.getBirth();
            String ageRange = "";
            if (birth != null) {
                byte memberAgeRange = DpConstants.getDpMemberAgeRange(birth);
                if (memberAgeRange > 0)
                    ageRange = DpConstants.DP_MEMBER_AGE_MAP.get(memberAgeRange);
            }
            CadreView cadre = CmTag.getCadreByUserId(record.getUserId());
            SysUserView uv = sysUserService.findById(record.getUserId());
            String post = teacherInfo.getPost();  // 行政职务 -- 所在单位及职务
            String adminLevel = teacherInfo.getPostLevel(); // 任职级别 -- 行政级别
            if (cadre != null && (cadre.getStatus() == CadreConstants.CADRE_STATUS_MIDDLE
                    || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)) {
                post = cadre.getTitle();
                if (cadre.getAdminLevel() != null) adminLevel = CmTag.getMetaType(cadre.getAdminLevel()).getName();
            }
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    sysUserView.getCode(),//工作证号
                    sysUserView.getRealname(),//姓名
                    record.getAuthorizedType(),//编制类别
                    teacherInfo.getStaffType(),//人员类别
                    teacherInfo.getStaffStatus(), // 人员状态
                    teacherInfo.getOnJob(), // 在岗情况
                    //teacherInfo.getPostClass(), // 岗位类别
                    //teacherInfo.getMainPostLevel(), // 主岗等级
                    sysUserView.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(sysUserView.getGender()),//性别
                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),//出生日期
                    birth != null ? DateUtils.intervalYearsUntilNow(birth) + "" : "",//年龄
                    ageRange, // 年龄范围
                    sysUserView.getNation(),//民族
                    uv.getCountry(),// 国家/地区
                    sysUserView.getIdcard(), // 证件号码
                    DpConstants.DP_MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),//政治面貌
                    partyId == null ? "--" : dpPartyService.findAll().get(partyId).getName(),//所属民主党派
                    uv.getUnit(), // 所在单位

                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),//入党时间
                    record.getSponsor(),//入党介绍人
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),//转正时间
                    record.getPartyPost(),//党内职务
                    record.getPartyReward(),//党内奖励
                    record.getOtherReward(),//其他奖励
                    metaTypeService.getName(record.getAddType()),//增加类型

                    DateUtils.formatDate(teacherInfo.getArriveTime(), DateUtils.YYYY_MM_DD), // 到校日期
                    record.getProPost(), // 专业技术职务
                    //teacherInfo.getProPostLevel(), //专技岗位等级
                    //teacherInfo.getManageLevel(), // 管理岗位等级
                    adminLevel, // 任职级别 -- 行政级别
                    //post, // 行政职务 -- 职务
                    record.getEducation(), // 学历
                    teacherInfo.getSchool(), // 学历毕业学校
                    //teacherInfo.getDegreeSchool(),//学位授予学校
                    teacherInfo.getDegree(), // 学位
                    teacherInfo.getFromType(), // 学员结构
                    //teacherInfo.getTalentType(), // 人才类型
                    //teacherInfo.getTalentTitle(),//人才称号
                    sysUserView.getMobile()//手机号码
            }));

            if (cls == 3) {
                values.add(6, DateUtils.formatDate(record.getRetireTime(), DateUtils.YYYY_MM_DD));
            }

            if (cols != null && cols.length > 0) {
                // 选择导出列
                List<String> _values = new ArrayList<>();
                for (int col : cols) {
                    _values.add(values.get(col));
                }
                values.clear();
                values.addAll(_values);
            }

            valuesList.add(values);
        }
        String fileName = (cls == 7 ? "已转出" : (cls == 3 ? "离退休" : "在职")) + "教职工党派成员信息(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";

        ExportHelper.export(exportTitles, valuesList, fileName, response);
    }

    private List<String> getStudentExportTitles() {

        return new ArrayList<>(Arrays.asList(new String[]{"学号|100", "学生类别|150", "姓名|120", "性别|50", "出生日期|100", "身份证号|150",
                "民族|100", "年级|50", "所属民主党派|300",
                "政治面貌|100", "入党时间|100",  "入党介绍人|100", "转正时间|100",
                "党内职务|100", "党内奖励|100", "其他奖励|100", "增加类型|100",
                "培养层次（研究生）|150", "培养类型（研究生）|150", "教育类别（研究生）|150",
                "培养方式（研究生）|150", "预计毕业年月|100", "学籍状态|100"}));
    }

    public void student_export(int cls, DpMemberViewExample example, Integer[] cols, HttpServletResponse response) {

        Map<Integer, Unit> unitMap = unitService.findAll();
        List<DpMemberView> records = dpMemberViewMapper.selectByExample(example);
        int rownum = records.size();

        List<String> exportTitles = getStudentExportTitles();
        if (cols != null && cols.length > 0) {
            // 选择导出列
            List<String> _titles = new ArrayList<>();
            for (int col : cols) {
                _titles.add(exportTitles.get(col));
            }
            exportTitles.clear();
            exportTitles.addAll(_titles);
        }

        List<List<String>> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpMemberView record = records.get(i);
            SysUserView uv = CmTag.getUserById(record.getUserId());
            StudentInfo studentInfo = studentInfoMapper.selectByPrimaryKey(record.getUserId());
            Byte gender = uv.getGender();
            Integer partyId = record.getPartyId();

            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    uv.getCode(),//学号
                    record.getStudentType(),//学生类别
                    uv.getRealname(),//姓名
                    gender == null ? "" : SystemConstants.GENDER_MAP.get(gender),//性别
                    DateUtils.formatDate(uv.getBirth(), DateUtils.YYYY_MM_DD),//出生日期
                    uv.getIdcard(),//身份证号
                    uv.getNation(),//民族
                    record.getGrade(), // 年级
                    partyId == null ? "" : dpPartyService.findAll().get(partyId).getName(),//
                    DpConstants.DP_MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()), // 政治面貌
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),//入党时间
                    record.getSponsor(),//入党介绍人
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),//转正时间
                    record.getPartyPost(),//党内职务
                    record.getPartyReward(),//党内奖励
                    record.getOtherReward(),//其他奖励
                    metaTypeService.getName(record.getAddType()),//增加类型
                    record.getEduLevel(),//培养层次（研究生）
                    record.getEduType(),//培养类型（研究生）
                    studentInfo.getEduCategory(),//教育类别（研究生）
                    studentInfo.getEduWay(),//培养方式（研究生）
                    DateUtils.formatDate(record.getExpectGraduateTime(), DateUtils.YYYY_MM_DD),//预计毕业年月
                    studentInfo.getXjStatus()//学籍状态
            }));

            if (cols != null && cols.length > 0) {
                // 选择导出列
                List<String> _values = new ArrayList<>();
                for (int col : cols) {
                    _values.add(values.get(col));
                }
                values.clear();
                values.addAll(_values);
            }

            valuesList.add(values);
        }

        String fileName = (cls == 6 ? "已转出" : "") + "学生党派成员信息(" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ")";
        ExportHelper.export(exportTitles, valuesList, fileName, response);
    }

}
