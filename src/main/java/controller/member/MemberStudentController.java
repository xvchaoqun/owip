package controller.member;

import controller.BaseController;
import domain.member.MemberStudent;
import domain.member.MemberStudentExample;
import domain.member.MemberStudentExample.Criteria;
import domain.party.Branch;
import domain.member.MemberStay;
import domain.party.Party;
import interceptor.OrderParam;
import mixin.MemberStudentMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberStudentController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberStudent:list")
    @RequestMapping("/memberStudent")
    public String memberStudent(
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

        modelMap.put("studentGrades", searchMapper.studentGrades());
        modelMap.put("studentTypes", searchMapper.studentTypes());
        modelMap.put("studentNations", searchMapper.studentNations());
        modelMap.put("studentNativePlaces", searchMapper.studentNativePlaces());

        return "member/memberStudent/memberStudent_page";
    }
    @RequiresPermissions("memberStudent:list")
    @RequestMapping("/memberStudent_data")
    public void memberStudent_data(HttpServletResponse response,
                                     @RequestParam(defaultValue = "party") String sort,
                                     @OrderParam(required = false, defaultValue = "desc") String order,
                                     @RequestParam(defaultValue = "1")int cls,
                                     Integer userId,
                                     Integer unitId,
                                     Integer partyId,
                                     Integer branchId,
                                     Byte politicalStatus,
                                     Byte gender,
                                     Byte age,
                                     String grade,
                                     String type,
                                     @RequestDateRange DateRange _growTime,
                                     @RequestDateRange DateRange _positiveTime,
                                     String eduLevel,
                                     String eduType,
                                     @RequestParam(required = false, value = "nation")String[] nation,
                                     @RequestParam(required = false, value = "nativePlace")String[] nativePlace,
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

        MemberStudentExample example = new MemberStudentExample();
        Criteria criteria = example.createCriteria();
        if(cls==6)
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STATUS_TRANSFER);
        else
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL);
        if(StringUtils.equalsIgnoreCase(sort, "party")){
            example.setOrderByClause(String.format("party_id , branch_id %s, grow_time desc", order));
        }else if(StringUtils.equalsIgnoreCase(sort, "growTime")){
            example.setOrderByClause(String.format("grow_time %s", order));
        }

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (userId != null) {
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
        if (StringUtils.isNotBlank(eduLevel)) {
            criteria.andEduLevelLike("%" + eduLevel + "%");
        }
        if (StringUtils.isNotBlank(eduType)) {
            criteria.andEduTypeLike("%" + eduType + "%");
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
        if(StringUtils.isNotBlank(grade)){
            criteria.andGradeEqualTo(grade);
        }
        if(StringUtils.isNotBlank(type)){
            criteria.andTypeEqualTo(type);
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

       /* if (StringUtils.isNotBlank(_growTime)) {
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
        }*/


        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andUserIdIn(Arrays.asList(ids));
            memberStudent_export(cls, example, response);
            return;
        }

        int count = memberStudentMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberStudent> memberStudents = memberStudentMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberStudents);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberStudent.class, MemberStudentMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    // 基本信息 + 党籍信息
    @RequiresPermissions("memberStudent:base")
    @RequestMapping("/memberStudent_base")
    public String memberStudent_base(Integer userId, ModelMap modelMap) {

        MemberStudent memberStudent = memberStudentService.get(userId);
        modelMap.put("memberStudent", memberStudent);

        return "member/memberStudent/memberStudent_base";
    }

    public void memberStudent_export(int cls, MemberStudentExample example, HttpServletResponse response) {

        //Map<Integer, Unit> unitMap = unitService.findAll();
        List<MemberStudent> records = memberStudentMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学号","学生类别","姓名","性别", "出生日期", "身份证号",
                "民族", "年级", "所在分党委、党总支、直属党支部", "所属党支部", "政治面貌", "发展时间",
                "培养层次（研究生填写）","培养类型（研究生填写）", "教育类别（研究生填写）",
                "培养方式（研究生填写）","预计毕业年月", "学籍状态","是否出国留学"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberStudent record = records.get(i);
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();

            MemberStay memberStay = memberStayService.get(record.getUserId());

            String[] values = {
                    record.getCode(),
                    record.getType(),
                    record.getRealname(),
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                    record.getIdcard(),
                    record.getNation(),
                    record.getGrade(), // 年级
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    SystemConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()), // 政治面貌
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record.getEduLevel(),
                    record.getEduType(),
                    record.getEduCategory(),
                    record.getEduWay(),
                    DateUtils.formatDate(record.getExpectGraduateTime(), DateUtils.YYYY_MM_DD),
                    record.getXjStatus(),
                    (memberStay!=null&&memberStay.getStatus()==
                            SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY)?"是":"否"// 是否出国留学
            };
            valuesList.add(values);
        }
        String fileName = (cls==6?"已转出":"")+"学生党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
