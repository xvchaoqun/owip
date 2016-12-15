package controller.party;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.ext.ExtJzg;
import domain.party.Branch;
import domain.member.MemberTeacher;
import domain.member.MemberTeacherExample;
import domain.member.MemberTeacherExample.Criteria;
import domain.party.Party;
import domain.sys.MetaType;
import domain.unit.Unit;
import interceptor.OrderParam;
import mixin.MemberTeacherMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberTeacherController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberTeacher:list")
    @RequestMapping("/memberTeacher")
    public String memberTeacher() {

        return "index";
    }

    @RequiresPermissions("memberTeacher:list")
    @RequestMapping("/memberTeacher_page")
    public String memberTeacher_page(
            @RequestParam(defaultValue = "1")int cls,
            Integer userId,
            Integer partyId,
            Integer branchId,
            @RequestParam(required = false, value = "nation")String[] nation,
            @RequestParam(required = false, value = "nativePlace")String[] nativePlace,
            ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));

        if (nation!=null) {
            List<String> selectNations = Arrays.asList(nation);
            modelMap.put("selectNations", selectNations);
        }
        if (nativePlace!=null) {
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            modelMap.put("selectNativePlaces", selectNativePlaces);
        }

        modelMap.put("teacherEducationTypes", searchMapper.teacherEducationTypes());
        modelMap.put("teacherPostClasses", searchMapper.teacherPostClasses());
        modelMap.put("teacherNations", searchMapper.teacherNations());
        modelMap.put("teacherNativePlaces", searchMapper.teacherNativePlaces());

        return "party/memberTeacher/memberTeacher_page";
    }

    @RequiresPermissions("memberTeacher:list")
    @RequestMapping("/memberTeacher_data")
    public void memberTeacher_data(HttpServletResponse response,
                                   @RequestParam(defaultValue = "party") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 @RequestParam(defaultValue = "2")int cls, // 教师或学生，用于页面标签
                                    Integer userId,
                                    Integer unitId,
                                    Integer partyId,
                                    Integer branchId,
                                    Byte politicalStatus,
                                    Byte gender,
                                   @RequestParam(required = false, value = "nation")String[] nation,
                                   @RequestParam(required = false, value = "nativePlace")String[] nativePlace,
                                    Byte age,
                                    String education,
                                    String postClass,
                                    String _retireTime,
                                    Boolean isHonorRetire,
                                    String _growTime,
                                    String _positiveTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberTeacherExample example = new MemberTeacherExample();
        Criteria criteria = example.createCriteria();

        if(StringUtils.equalsIgnoreCase(sort, "party")){
            example.setOrderByClause(String.format("party_id , branch_id %s, grow_time desc", order));
        }else if(StringUtils.equalsIgnoreCase(sort, "growTime")){
            example.setOrderByClause(String.format("grow_time %s", order));
        }

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (unitId != null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if(gender!=null){
            criteria.andGenderEqualTo(gender);
        }
        if(politicalStatus!=null){
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (nation!=null) {
            List<String> selectNations = Arrays.asList(nation);
            criteria.andNationIn(selectNations);
        }
        if (nativePlace!=null) {
            List<String> selectNativePlaces = Arrays.asList(nativePlace);
            criteria.andNativePlaceIn(selectNativePlaces);
        }
        if(age!=null){
            switch (age){
                case SystemConstants.MEMBER_AGE_20: // 20岁以下
                    criteria.andBirthGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -20));
                    break;
                case SystemConstants.MEMBER_AGE_21_30:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -30),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                    break;
                case SystemConstants.MEMBER_AGE_31_40:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -40),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -31));
                    break;
                case SystemConstants.MEMBER_AGE_41_50:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -50),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -41));
                    break;
                case SystemConstants.MEMBER_AGE_51:
                    criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -51));
                    break;
                case SystemConstants.MEMBER_AGE_0:
                    criteria.andBirthIsNull();
                    break;
            }
        }
        if(StringUtils.isNotBlank(education)){
            criteria.andEducationEqualTo(education);
        }
        if(StringUtils.isNotBlank(postClass)){
            criteria.andPostClassEqualTo(postClass);
        }

        if (StringUtils.isNotBlank(_retireTime)) {
            String start = _retireTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _retireTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andRetireTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andRetireTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }
        if(isHonorRetire!=null){
            criteria.andIsHonorRetireEqualTo(isHonorRetire);
        }

        if (StringUtils.isNotBlank(_growTime)) {
            String start = _growTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _growTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andGrowTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }

        if (StringUtils.isNotBlank(_positiveTime)) {
            String start = _positiveTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _positiveTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andPositiveTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andPositiveTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }

        /*
            cls=2教职工   =>  member.type=1 member.status=1
                3离退休   =>  member.type=2 member.status=1
                4应退休   =>  member.type=2 member.status=1
                5已退休   =>  member.type=2 memberTeacher.isRetire=1 member.status=2
         */
        switch (cls){
            case 2:
                criteria.andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                        .andIsRetireNotEqualTo(true);
                break;
            case 3:
                criteria.andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                        .andIsRetireEqualTo(true);
                break;
            case 7:
                criteria.andStatusEqualTo(SystemConstants.MEMBER_STATUS_TRANSFER);
                break;
           /* case 4:
                criteria.andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL)
                        .andIsRetireEqualTo(true);
                break;
            case 5:
                criteria.andStatusEqualTo(SystemConstants.MEMBER_STATUS_RETIRE)
                        .andIsRetireEqualTo(true);
                break;*/
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andUserIdIn(Arrays.asList(ids));
            memberTeacher_export(cls, example, response);
            return;
        }

        int count = memberTeacherMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberTeacher> MemberTeachers = memberTeacherMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", MemberTeachers);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberTeacher.class, MemberTeacherMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    // 基本信息 + 党籍信息
    @RequiresPermissions("memberTeacher:base")
    @RequestMapping("/memberTeacher_base")
    public String memberTeacher_base(Integer userId, ModelMap modelMap) {

        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);

        return "party/memberTeacher/memberTeacher_base";
    }

    public void memberTeacher_export(int cls, MemberTeacherExample example, HttpServletResponse response) {

        //Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, MetaType> adminLevelMap = metaTypeService.metaTypes("mc_admin_level");
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<MemberTeacher> records = memberTeacherMapper.selectByExample(example);
        String[] titles = {"工作证号","姓名","编制类别","人员类别","人员状态","在岗情况","岗位类别", "主岗等级",
                "性别","出生日期", "年龄","年龄范围","民族", "国家/地区", "证件号码",
                "政治面貌","所在分党委、党总支、直属党支部","所在党支部", "所在单位", "入党时间","到校日期",
                "专业技术职务","专技岗位等级","管理岗位等级","任职级别","行政职务","学历","学历毕业学校","学位授予学校",
                "学位","学员结构", "人才类型", "人才称号", "手机号码"};
        List<String[]> valuesList = new ArrayList<>();
        for (MemberTeacher record:records) {
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Date birth = record.getBirth();
            String ageRange = "";
            if(birth!=null){
                byte memberAgeRange = SystemConstants.getMemberAgeRange(DateUtils.getYear(birth));
                if(memberAgeRange>0)
                    ageRange = SystemConstants.MEMBER_AGE_MAP.get(memberAgeRange);
            }
            Cadre cadre = cadreService.findByUserId(record.getUserId());
            String post = record.getPost();  // 行政职务 -- 所在单位及职务
            String adminLevel = record.getPostLevel(); // 任职级别 -- 行政级别
            if(cadre!=null){
                post = cadre.getTitle();
                adminLevel = adminLevelMap.get(cadre.getTypeId()).getName();
            }
            String[] values = {
                    record.getCode(),
                    record.getRealname(),
                    record.getAuthorizedType(),
                    record.getStaffType(),
                    record.getStaffStatus(), // 人员状态
                    record.getOnJob(), // 在岗情况
                    record.getPostClass(), // 岗位类别
                    record.getPostType(), // 主岗等级--岗位级别
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth!=null?DateUtils.intervalYearsUntilNow(birth) + "":"",
                    ageRange, // 年龄范围
                    record.getNation(),
                    record.getCountry(),// 国家/地区
                    record.getIdcard(), // 证件号码
                    SystemConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),
                    partyId==null?"":partyMap.get(partyId).getName(),
                    branchId==null?"":branchMap.get(branchId).getName(),
                    record.getExtUnit(), // 所在单位
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record.getArriveTime(), // 到校日期
                    record.getProPost(), // 专业技术职务
                    record.getProPostLevel(), //专技岗位等级
                    record.getManageLevel(), // 管理岗位等级
                    adminLevel, // 任职级别 -- 行政级别
                    post, // 行政职务 -- 职务
                    record.getEducation(), // 学历
                    record.getSchool(), // 学历毕业学校
                    record.getDegreeSchool(),
                    record.getDegree(), // 学位
                    record.getFromType(), // 学员结构
                    record.getTalentType(), // 人才类型
                    record.getTalentTitle(),
                    record.getMobile()
            };
            valuesList.add(values);
        }
        String fileName = (cls==7?"已转出":"")+"教职工党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");

        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
