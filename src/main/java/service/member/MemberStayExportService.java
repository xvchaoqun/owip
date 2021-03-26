package service.member;

import bean.UserBean;
import domain.member.MemberOut;
import domain.member.MemberStayView;
import domain.member.MemberStayViewExample;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.StudentInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.base.MetaTypeService;
import service.party.BranchService;
import service.party.PartyService;
import service.sys.StudentInfoService;
import service.sys.TeacherInfoService;
import service.sys.UserBeanService;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import java.util.List;
import java.util.Map;

@Service
public class MemberStayExportService extends MemberBaseMapper {

    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected TeacherInfoService teacherService;
    @Autowired
    protected StudentInfoService studentService;
    @Autowired
    protected MemberOutService memberOutService;

    // 汇总导出
    public SXSSFWorkbook toXlsx(byte type, MemberStayViewExample example, String title) {

        List<MemberStayView> records = memberStayViewMapper.selectByExample(example);
        int count = records.size();

        int rowNum = 0;
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //sheet.setDefaultColumnWidth(12);
        //sheet.setDefaultRowHeight((short)(20*60));
        {
            Row titleRow = sheet.createRow(rowNum);
            titleRow.setHeight((short) (35.7 * 30));
            Cell headerCell = titleRow.createCell(0);
            CellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font = wb.createFont();
            // 设置字体加粗
            font.setFontName("宋体");
            font.setFontHeight((short) 350);
            cellStyle.setFont(font);
            headerCell.setCellStyle(cellStyle);
            headerCell.setCellValue(title);
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }
        String[] titles = null;
        if(type == MemberConstants.MEMBER_STAY_TYPE_ABROAD) {
            titles = new String[]{"编号", "学号", "姓名", "性别", "民族",
                    "出生年月", "入党时间", "培养层次", "籍贯", "身份证号",
                    "党籍状况", "手机", "微信号", "电子邮箱", "组织关系所在分党委名称",
                    "保留组织关系期间所在党支部名称", "留学起始时间", "预计回国时间", "留学国家", "留学学校（院系）",
                    "留学方式", "是否转出", "转出时间", "转入单位"};
        }else{
            titles = new String[]{"编号", "学号", "姓名", "性别", "民族",
                    "出生年月", "入党时间", "培养层次", "籍贯", "身份证号",
                    "党籍状况", "手机", "微信号", "QQ", "电子邮箱",
                    "组织关系所在分党委名称", "保留组织关系期间所在党支部名称", "暂留原因", "暂留起始时间", "预计转出时间",
                     "是否转出", "转出时间", "转入单位"};
        }


        int columnCount = titles.length;
        Row firstRow = sheet.createRow(rowNum++);
        firstRow.setHeight((short) (35.7 * 12));
        for (int i = 0; i < columnCount; i++) {
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(ExportHelper.getHeadStyle(wb));
        }

        int columnIndex = 0;

        if(type == MemberConstants.MEMBER_STAY_TYPE_ABROAD) {
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 编号
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 出生年月
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 党籍状况
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 250));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 350));

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300)); // 保留组织关系期间所在党支部名称
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 110));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 110));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 留学方式
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 80));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 80));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));
        }else{

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 编号
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 出生年月
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 党籍状况
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 350)); // 组织关系所在分党委名称
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));

            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 80)); // 是否转出
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 80));
            sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));
        }

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        //Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        for (int i = 0; i < count; i++) {

            MemberStayView record = records.get(i);
            int userId = record.getUserId();
            StudentInfo student = studentService.get(userId);
            UserBean u = userBeanService.get(userId);
            Integer partyId = record.getPartyId();
            //Integer branchId = record.getBranchId();
            Integer toBranchId = record.getToBranchId();

            String transferTime = "";
            String transferUnit = "";
            if (u.getMemberStatus() != null && u.getMemberStatus() == MemberConstants.MEMBER_STATUS_OUT) {
                MemberOut memberOut = memberOutService.getLatest(userId);
                if (memberOut != null && memberOut.getStatus()==MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY){
                    transferTime = DateUtils.formatDate(memberOut.getHandleTime(), DateUtils.YYYYMM);
                    transferUnit = memberOut.getToUnit();
                }
            }

            String[] values = null;
            if(type == MemberConstants.MEMBER_STAY_TYPE_ABROAD) {
                values = new String[]{
                        record.getCode(),
                        u.getCode(),
                        u.getRealname(),
                        u.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(u.getGender()),
                        u.getNation(),

                        DateUtils.formatDate(u.getBirth(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(u.getGrowTime(), DateUtils.YYYY_MM_DD),
                        student == null ? "" : student.getEduLevel(),
                        u.getNativePlace(),
                        u.getIdcard(),

                        MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(u.getPoliticalStatus()),
                        record.getMobile(),
                        record.getWeixin(),
                        record.getEmail(),
                        partyId == null ? "" : partyMap.get(partyId).getName(),

                        toBranchId == null ? "" : branchMap.get(toBranchId).getName(),
                        DateUtils.formatDate(record.getSaveStartTime(), DateUtils.YYYYMM),
                        DateUtils.formatDate(record.getOverDate(), DateUtils.YYYYMM),
                        record.getCountry(),
                        record.getSchool(),

                        record.getAbroadType() == null ? "" : MemberConstants.MEMBER_STAY_ABROAD_TYPE_MAP_MAP.get(record.getAbroadType()),
                        u.getMemberStatus() == MemberConstants.MEMBER_STATUS_OUT ? "是" : "否",
                        transferTime,
                        transferUnit
                };
            }else {

                values = new String[]{
                        record.getCode(),
                        u.getCode(),
                        u.getRealname(),
                        u.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(u.getGender()),
                        u.getNation(),

                        DateUtils.formatDate(u.getBirth(), DateUtils.YYYY_MM_DD),
                        DateUtils.formatDate(u.getGrowTime(), DateUtils.YYYY_MM_DD),
                        student == null ? "" : student.getEduLevel(),
                        u.getNativePlace(),
                        u.getIdcard(),

                        MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(u.getPoliticalStatus()),
                        record.getMobile(),
                        record.getWeixin(),
                        record.getQq(),
                        record.getEmail(),

                        partyId == null ? "" : partyMap.get(partyId).getName(),
                        toBranchId == null ? "" : branchMap.get(toBranchId).getName(),
                        record.getStayReason(),
                        DateUtils.formatDate(record.getStartTime(), DateUtils.YYYYMM),
                        DateUtils.formatDate(record.getOverDate(), DateUtils.YYYYMM),

                        u.getMemberStatus() == MemberConstants.MEMBER_STATUS_OUT ? "是" : "否",
                        transferTime,
                        transferUnit
                };
            }

            Row row = sheet.createRow(rowNum++);
            row.setHeight((short) (35.7 * 18));
            for (int j = 0; j < columnCount; j++) {

                Cell cell = row.createCell(j);
                String value = values[j];
                if (StringUtils.isBlank(value)) value = "-";
                cell.setCellValue(value);
                cell.setCellStyle(ExportHelper.getBodyStyle(wb));
            }
        }

        return wb;
    }
}
