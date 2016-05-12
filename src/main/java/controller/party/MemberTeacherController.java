package controller.party;

import controller.BaseController;
import domain.Branch;
import domain.MemberTeacher;
import domain.MemberTeacherExample;
import domain.MemberTeacherExample.Criteria;
import domain.Party;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberTeacherMixin;
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

        modelMap.put("teacherEducationTypes", searchMapper.teacherEducationTypes());
        modelMap.put("teacherPostClasses", searchMapper.teacherPostClasses());

        return "party/memberTeacher/memberTeacher_page";
    }

    @RequiresPermissions("memberTeacher:list")
    @RequestMapping("/memberTeacher_data")
    public void memberTeacher_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "grow_time", tableName = "ow_member_teacher") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 @RequestParam(defaultValue = "2")int cls, // 教师或学生，用于页面标签
                                    Integer userId,
                                    Integer unitId,
                                    Integer partyId,
                                    Integer branchId,
                                    Byte gender,
                                    Integer age,
                                    String education,
                                    String postClass,
                                    String _retireTime,
                                    Boolean isHonorRetire,
                                    String _growTime,
                                    String _positiveTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
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
        example.setOrderByClause(String.format("%s %s", sort, order));

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
            memberTeacher_export(example, response);
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

        modelMap.put("GENDER_MALE_MAP", SystemConstants.GENDER_MAP);
        modelMap.put("MEMBER_SOURCE_MAP", SystemConstants.MEMBER_SOURCE_MAP);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("MEMBER_POLITICAL_STATUS_MAP", SystemConstants.MEMBER_POLITICAL_STATUS_MAP);

        return "party/memberTeacher/memberTeacher_base";
    }

    public void memberTeacher_export(MemberTeacherExample example, HttpServletResponse response) {

        List<MemberTeacher> records = memberTeacherMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号","姓名","性别","出生日期","所属分党委","所属党支部",
                "入党时间","最高学历","岗位类别","专业技术职务","联系手机"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberTeacher record = records.get(i);
            Byte gender = record.getGender();
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();

            String[] values = {
                    record.getCode(),
                    record.getRealname(),
                    gender==null?"":SystemConstants.GENDER_MAP.get(gender),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record.getEducation(),
                    record.getPostClass(),
                    record.getProPost(),
                    record.getMobile()
            };

            valuesList.add(values);
        }
        String fileName = "教职工党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");

        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
