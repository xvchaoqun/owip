package controller.party;

import controller.BaseController;
import domain.party.Branch;
import domain.member.MemberStudent;
import domain.member.MemberStudentExample;
import domain.member.MemberStudentExample.Criteria;
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
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberStudentController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberStudent:list")
    @RequestMapping("/memberStudent")
    public String memberStudent() {

        return "index";
    }

    /*@RequiresPermissions("memberStudent:list")
    @RequestMapping("/memberStudent_page1")
    public String memberStudent_page1(HttpServletResponse response,
                                     @SortParam(required = false, defaultValue = "grow_time", tableName = "ow_member_student") String sort,
                                     @OrderParam(required = false, defaultValue = "desc") String order,
                                     @RequestParam(defaultValue = "1")int cls,
                                     Integer userId,
                                     @RequestParam(required = false, defaultValue = "0") int export,
                                     Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberStudentExample example = new MemberStudentExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.MEMBER_STATUS_NORMAL);
        example.setOrderByClause(String.format("%s %s", sort, order));

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            memberStudent_export(example, response);
            return null;
        }

        int count = memberStudentMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberStudent> MemberStudents = memberStudentMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberStudents", MemberStudents);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
            searchStr += "&userId=" + userId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }

        modelMap.put("cls", cls);
        searchStr += "&cls=" + cls;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());

        //modelMap.put("MEMBER_SOURCE_MAP", SystemConstants.MEMBER_SOURCE_MAP);

        return "party/memberStudent/memberStudent_page";
    }*/

    @RequiresPermissions("memberStudent:list")
    @RequestMapping("/memberStudent_page")
    public String memberStudent_page(
                                  @RequestParam(defaultValue = "1")int cls,
                                  Integer userId,
                                  Integer partyId,
                                  Integer branchId,
                                  @RequestParam(required = false, value = "nation")String[] nation,
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

        modelMap.put("studentGrades", searchMapper.studentGrades());
        modelMap.put("studentTypes", searchMapper.studentTypes());
        modelMap.put("studentNations", searchMapper.studentNations());

        return "party/memberStudent/memberStudent_page";
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
                                     String _growTime,
                                     String _positiveTime,
                                     String eduLevel,
                                     String eduType,
                                     @RequestParam(required = false, value = "nation")String[] nation,
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

        return "party/memberStudent/memberStudent_base";
    }

    public void memberStudent_export(int cls, MemberStudentExample example, HttpServletResponse response) {

        List<MemberStudent> records = memberStudentMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学生证号","姓名", "性别", "民族","学生类别", "年级", "入党时间","转正时间", "所属分党委", "所属党支部"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberStudent record = records.get(i);
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    record.getCode(),
                    record.getRealname(),
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    record.getNation(),
                    record.getType(),
                    record.getGrade(),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName()
            };
            valuesList.add(values);
        }
        String fileName = (cls==6?"已转出":"")+"学生党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
