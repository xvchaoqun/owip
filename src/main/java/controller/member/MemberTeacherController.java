package controller.member;

import domain.cadre.CadreView;
import domain.member.MemberTeacher;
import domain.member.MemberTeacherExample;
import domain.member.MemberTeacherExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import mixin.MemberTeacherMixin;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.CadreConstants;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberTeacherController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberTeacher:list")
    @RequestMapping("/memberTeacher")
    public String memberTeacher(
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

        modelMap.put("teacherEducationTypes", iPropertyMapper.teacherEducationTypes());
        modelMap.put("teacherPostClasses", iPropertyMapper.teacherPostClasses());
        modelMap.put("teacherNations", iPropertyMapper.teacherNations());
        modelMap.put("teacherNativePlaces", iPropertyMapper.teacherNativePlaces());

        return "member/memberTeacher/memberTeacher_page";
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
                                   @RequestDateRange DateRange _retireTime,
                                    Boolean isHonorRetire,
                                   @RequestDateRange DateRange _growTime,
                                   @RequestDateRange DateRange _positiveTime,
                                   @RequestDateRange DateRange _outHandleTime,
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
                case MemberConstants.MEMBER_AGE_20: // 20岁以下
                    criteria.andBirthGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -20));
                    break;
                case MemberConstants.MEMBER_AGE_21_30:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -30),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                    break;
                case MemberConstants.MEMBER_AGE_31_40:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -40),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -31));
                    break;
                case MemberConstants.MEMBER_AGE_41_50:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -50),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -41));
                    break;
                case MemberConstants.MEMBER_AGE_51:
                    criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -51));
                    break;
                case MemberConstants.MEMBER_AGE_0:
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

        if (_retireTime.getStart()!=null) {
            criteria.andRetireTimeGreaterThanOrEqualTo(_retireTime.getStart());
        }

        if (_retireTime.getEnd()!=null) {
            criteria.andRetireTimeLessThanOrEqualTo(_retireTime.getEnd());
        }

        if(isHonorRetire!=null){
            criteria.andIsHonorRetireEqualTo(isHonorRetire);
        }

        if (_growTime.getStart()!=null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
        }

        if (_growTime.getEnd()!=null) {
            criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
        }
        if (_positiveTime.getStart()!=null) {
            criteria.andPositiveTimeGreaterThanOrEqualTo(_positiveTime.getStart());
        }
        if (_positiveTime.getEnd()!=null) {
            criteria.andPositiveTimeLessThanOrEqualTo(_positiveTime.getEnd());
        }
        if (_outHandleTime.getStart()!=null) {
            criteria.andOutHandleTimeGreaterThanOrEqualTo(_outHandleTime.getStart());
        }
        if (_outHandleTime.getEnd()!=null) {
            criteria.andOutHandleTimeLessThanOrEqualTo(_outHandleTime.getEnd());
        }

        /*
            cls=2教职工   =>  member.type=1 member.status=1
                3离退休   =>  member.type=2 member.status=1
                4应退休   =>  member.type=2 member.status=1
                5已退休   =>  member.type=2 memberTeacher.isRetire=1 member.status=2
         */
        switch (cls){
            case 2:
                criteria.andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                        .andIsRetireNotEqualTo(true);
                break;
            case 3:
                criteria.andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                        .andIsRetireEqualTo(true);
                break;
            case 7:
                criteria.andStatusEqualTo(MemberConstants.MEMBER_STATUS_TRANSFER);
                break;
           /* case 4:
                criteria.andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL)
                        .andIsRetireEqualTo(true);
                break;
            case 5:
                criteria.andStatusEqualTo(MemberConstants.MEMBER_STATUS_RETIRE)
                        .andIsRetireEqualTo(true);
                break;*/
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andUserIdIn(Arrays.asList(ids));
            memberTeacher_export(cls, example, response);
            return;
        }

        long count = memberTeacherMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(MemberTeacher.class, MemberTeacherMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 基本信息 + 党籍信息
    @RequiresPermissions("memberTeacher:base")
    @RequestMapping("/memberTeacher_base")
    public String memberTeacher_base(Integer userId, ModelMap modelMap) {

        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);

        return "member/memberTeacher/memberTeacher_base";
    }

    public void memberTeacher_export(int cls, MemberTeacherExample example, HttpServletResponse response) {

        //Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<MemberTeacher> records = memberTeacherMapper.selectByExample(example);
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{"工作证号|100","姓名|50",
                "编制类别|80","人员类别|100","人员状态|80","在岗情况|80","岗位类别|80", "主岗等级|120",
                "性别|50","出生日期|80", "年龄|50","年龄范围|80","民族|50", "国家/地区|80", "证件号码|150",
                "政治面貌|80","所在分党委、党总支、直属党支部|300","所在党支部|300", "所在单位|200", "入党时间|80","到校日期|80",
                "专业技术职务|120","专技岗位等级|120","管理岗位等级|120","任职级别|120",
                "行政职务|180","学历|120","学历毕业学校|200","学位授予学校|200",
                "学位|100","学员结构|100", "人才类型|100", "人才称号|200", "手机号码|80"}));
        if(cls==3){
            titles.add(6, "离退休时间|80");
        }
        List<List<String>> valuesList = new ArrayList<>();
        for (MemberTeacher record:records) {
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Date birth = record.getBirth();
            String ageRange = "";
            if(birth!=null){
                byte memberAgeRange = MemberConstants.getMemberAgeRange(DateUtils.getYear(birth));
                if(memberAgeRange>0)
                    ageRange = MemberConstants.MEMBER_AGE_MAP.get(memberAgeRange);
            }
            CadreView cadre = CmTag.getCadreByUserId(record.getUserId());
            SysUserView uv = sysUserService.findById(record.getUserId());
            String post = record.getPost();  // 行政职务 -- 所在单位及职务
            String adminLevel = record.getPostLevel(); // 任职级别 -- 行政级别
            if(cadre!=null && (cadre.getStatus()== CadreConstants.CADRE_STATUS_MIDDLE
                    || cadre.getStatus()== CadreConstants.CADRE_STATUS_LEADER)){
                post = cadre.getTitle();
                if(cadre.getAdminLevel()!=null) adminLevel = CmTag.getMetaType(cadre.getAdminLevel()).getName();
            }
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    record.getCode(),
                    record.getRealname(),
                    record.getAuthorizedType(),
                    record.getStaffType(),
                    record.getStaffStatus(), // 人员状态
                    record.getOnJob(), // 在岗情况
                    record.getPostClass(), // 岗位类别
                    record.getMainPostLevel(), // 主岗等级
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth!=null?DateUtils.intervalYearsUntilNow(birth) + "":"",
                    ageRange, // 年龄范围
                    record.getNation(),
                    uv.getCountry(),// 国家/地区
                    record.getIdcard(), // 证件号码
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),
                    partyId==null?"":partyMap.get(partyId).getName(),
                    branchId==null?"":branchMap.get(branchId).getName(),
                    uv.getUnit(), // 所在单位
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYY_MM_DD), // 到校日期
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
            }));

            if(cls==3){
                values.add(6, DateUtils.formatDate(record.getRetireTime(), DateUtils.YYYY_MM_DD));
            }

            valuesList.add(values);
        }
        String fileName = (cls==7?"已转出":"")+"教职工党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");

        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
