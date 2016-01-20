package controller.party;

import controller.BaseController;
import domain.MemberStudent;
import domain.MemberStudentExample;
import domain.MemberStudentExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
public class MemberStudentController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberStudent:list")
    @RequestMapping("/memberStudent")
    public String memberStudent() {

        return "index";
    }

    @RequiresPermissions("memberStudent:list")
    @RequestMapping("/memberStudent_page")
    public String memberStudent_page(HttpServletResponse response,
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

        List<MemberStudent> memberStudents = memberStudentMapper.selectByExample(example);
        int rownum = memberStudentMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"转正时间", "所属党支部", "所属分党委", "入党时间", "学生证号", "性别", "学生类别", "年级"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberStudent memberStudent = memberStudents.get(i);
            String[] values = {
                    DateUtils.formatDate(memberStudent.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    memberStudent.getBranchId() + "",
                    memberStudent.getPartyId() + "",
                    DateUtils.formatDate(memberStudent.getGrowTime(), DateUtils.YYYY_MM_DD),
                    memberStudent.getCode(),
                    memberStudent.getGender()+"",
                    memberStudent.getType(),
                    memberStudent.getGrade() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "VIEW_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
