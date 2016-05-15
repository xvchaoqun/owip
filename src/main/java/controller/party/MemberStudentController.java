package controller.party;

import controller.BaseController;
import domain.Branch;
import domain.MemberStudent;
import domain.MemberStudentExample;
import domain.MemberStudentExample.Criteria;
import domain.Party;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberStudentMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
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

        modelMap.put("studentGrades", searchMapper.studentGrades());
        modelMap.put("studentTypes", searchMapper.studentTypes());

        return "party/memberStudent/memberStudent_page";
    }
    @RequiresPermissions("memberStudent:list")
    @RequestMapping("/memberStudent_data")
    public void memberStudent_data(HttpServletResponse response,
                                     @SortParam(required = false, defaultValue = "grow_time", tableName = "ow_member_student") String sort,
                                     @OrderParam(required = false, defaultValue = "desc") String order,
                                     @RequestParam(defaultValue = "1")int cls,
                                     Integer userId,
                                     Integer unitId,
                                     Integer partyId,
                                     Integer branchId,
                                     Byte gender,
                                     Integer age,
                                     String grade,
                                     String type,
                                     String _growTime,
                                     String _positiveTime,
                                     String eduLevel,
                                     String eduType,
                                     @RequestParam(required = false, defaultValue = "0") int export,
                                     Integer pageSize, Integer pageNo) throws IOException {

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
        if (StringUtils.isNotBlank(eduLevel)) {
            criteria.andEduLevelLike("%" + eduLevel + "%");
        }
        if (StringUtils.isNotBlank(eduType)) {
            criteria.andEduTypeLike("%" + eduType + "%");
        }

        if(age!=null){
            switch (age){
                case 1: // 20岁以下
                    criteria.andBirthGreaterThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -20));
                    break;
                case 2:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -30),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -21));
                    break;
                case 3:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -40),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -31));
                    break;
                case 4:
                    criteria.andBirthBetween(DateUtils.getDateBeforeOrAfterYears(new Date(), -50),
                            DateUtils.getDateBeforeOrAfterYears(new Date(), -41));
                    break;
                case 5:
                    criteria.andBirthLessThanOrEqualTo(DateUtils.getDateBeforeOrAfterYears(new Date(), -51));
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
            memberStudent_export(example, response);
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

        modelMap.put("GENDER_MALE_MAP", SystemConstants.GENDER_MAP);
        modelMap.put("MEMBER_SOURCE_MAP", SystemConstants.MEMBER_SOURCE_MAP);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("MEMBER_POLITICAL_STATUS_MAP", SystemConstants.MEMBER_POLITICAL_STATUS_MAP);
        modelMap.put("MEMBER_SOURCE_MAP", SystemConstants.MEMBER_SOURCE_MAP);

        return "party/memberStudent/memberStudent_base";
    }

    public void memberStudent_export(MemberStudentExample example, HttpServletResponse response) {

        List<MemberStudent> records = memberStudentMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学生证号","姓名", "性别", "学生类别", "年级", "入党时间","转正时间", "所属分党委", "所属党支部"};
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
                    record.getType(),
                    record.getGrade(),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName()
            };
            valuesList.add(values);
        }
        String fileName = "学生党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
