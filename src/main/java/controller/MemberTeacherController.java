package controller;

import domain.MemberTeacher;
import domain.MemberTeacherExample;
import domain.MemberTeacherExample.Criteria;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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
    public String memberTeacher_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "grow_time") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                 Integer type, // 教师或学生，用于页面标签
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

        MemberTeacherExample example = new MemberTeacherExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(type== 2){ // 在职教职工党员
            criteria.andIsRetireEqualTo(false);
        }else if(type== 3){ // 离退休党员
            criteria.andIsRetireEqualTo(true);
        }
        if (export == 1) {
            memberTeacher_export(example, response);
            return null;
        }

        int count = memberTeacherMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberTeacher> MemberTeachers = memberTeacherMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberTeachers", MemberTeachers);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
            searchStr += "&userId=" + userId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        if(type!=null){
            modelMap.put("type", type);
            searchStr += "&type=" + type;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());

        return "memberTeacher/memberTeacher_page";
    }

    // 基本信息
    @RequiresPermissions("memberTeacher:base")
    @RequestMapping("/memberTeacher_base")
    public String memberTeacher_base(Integer userId, ModelMap modelMap) {

        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);

        modelMap.put("GENDER_MALE_MAP", SystemConstants.GENDER_MALE_MAP);
        modelMap.put("MEMBER_SOURCE_MAP", SystemConstants.MEMBER_SOURCE_MAP);

        return "memberTeacher/memberTeacher_base";
    }
    // 党籍信息
    @RequiresPermissions("memberTeacher:member")
    @RequestMapping("/memberTeacher_member")
    public String memberTeacher_member(Integer userId, ModelMap modelMap) {

        MemberTeacher memberTeacher = memberTeacherService.get(userId);
        modelMap.put("memberTeacher", memberTeacher);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("MEMBER_POLITICAL_STATUS_MAP", SystemConstants.MEMBER_POLITICAL_STATUS_MAP);

        return "memberTeacher/memberTeacher_member";
    }

    public void memberTeacher_export(MemberTeacherExample example, HttpServletResponse response) {

        List<MemberTeacher> memberTeachers = memberTeacherMapper.selectByExample(example);
        int rownum = memberTeacherMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属党支部","所属分党委","入党时间","工作证号","最高学历","性别","岗位类别","专业技术职务","联系手机","出生日期"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberTeacher memberTeacher = memberTeachers.get(i);
            String[] values = {
                        memberTeacher.getBranchId()+"",
                                            memberTeacher.getPartyId()+"",
                                            DateUtils.formatDate(memberTeacher.getGrowTime(), DateUtils.YYYY_MM_DD),
                                            memberTeacher.getCode(),
                                            memberTeacher.getEducation(),
                                            memberTeacher.getGender()+"",
                                            memberTeacher.getPostClass(),
                                            memberTeacher.getProPost(),
                                            memberTeacher.getMobile(),
                                            DateUtils.formatDate(memberTeacher.getBirth(), DateUtils.YYYY_MM_DD)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "教职工党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
