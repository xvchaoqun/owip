package service.party;

import domain.base.MetaType;
import domain.member.Member;
import domain.party.*;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.party.PartyStaticViewMapper;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.unit.UnitService;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PartyExportService extends BaseMapper {
    
    @Autowired
    private PartyStaticViewMapper partyStaticViewMapper;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected MemberService memberService;
    
    /* public static void main(String[] args) throws IOException {
 
         XSSFWorkbook xssfSheets = toXlsx();
         ExportHelper.save(xssfSheets, "D:/tmp/test2.xlsx");
     }*/
    // 汇总导出
    public XSSFWorkbook toXlsx() throws IOException {
        
        List<PartyStaticView> records = partyStaticViewMapper.selectByExample(new PartyStaticViewExample());
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/party/party_stat_template.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);
        
        int cpRow = 3;
        int rowCount = records.size();
        if (rowCount > 1)
            ExcelUtils.copyRowsToEnd(wb, sheet, cpRow, rowCount - 1);
        
        int startRow = cpRow;
        
        for (int i = 0; i < rowCount; i++) {
            
            PartyStaticView p = records.get(i);
            int column = 0;
            row = sheet.getRow(startRow++);
            
            cell = row.getCell(column++);
            cell.setCellValue(p.getName());
            toggleCellBgColor(wb, cell, i);
            /**学生党员数**/
            // 本科生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getBks()));
            toggleCellBgColor(wb, cell, i);
            // 硕士生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getSs()));
            toggleCellBgColor(wb, cell, i);
            // 博士生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getBs()));
            toggleCellBgColor(wb, cell, i);
            // 总数
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getStudent()));
            toggleCellBgColor(wb, cell, i);
            
            /**教师党员数**/
            // 在职教师
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getTeacher()));
            toggleCellBgColor(wb, cell, i);
            // 退休教师
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getTeacherRetire()));
            toggleCellBgColor(wb, cell, i);
            // 总数
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getTeacherTotal()));
            toggleCellBgColor(wb, cell, i);
            
            //党员总数
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getStudent()) + getValue(p.getTeacherTotal()));
            toggleCellBgColor(wb, cell, i);
            
            /**学生党支部数**/
            // 本科生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getBksBranch()));
            toggleCellBgColor(wb, cell, i);
            // 硕士生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getSsBranch()));
            toggleCellBgColor(wb, cell, i);
            // 博士生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getBsBranch()));
            toggleCellBgColor(wb, cell, i);
            // 硕博混合
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getSbBranch()));
            toggleCellBgColor(wb, cell, i);
            // 本硕博混合
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getBsbBranch()));
            toggleCellBgColor(wb, cell, i);
            // 总数
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getStudentBranchTotal()));
            toggleCellBgColor(wb, cell, i);
            
            /**教工党支部数**/
            // 在职
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getTeacherBranch()));
            toggleCellBgColor(wb, cell, i);
            // 退休
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getRetireBranch()));
            toggleCellBgColor(wb, cell, i);
            // 总数
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getTeacherBranchTotal()));
            toggleCellBgColor(wb, cell, i);
            
            //党支部总数
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getStudentBranchTotal()) + getValue(p.getTeacherBranchTotal()));
            toggleCellBgColor(wb, cell, i);
            
            /**学生党员数（正式党员）**/
            // 本科生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getPositiveBks()));
            toggleCellBgColor(wb, cell, i);
            // 硕士生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getPositiveSs()));
            toggleCellBgColor(wb, cell, i);
            // 博士生
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getPositiveBs()));
            toggleCellBgColor(wb, cell, i);
            // 总数
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getPositiveStudent()));
            toggleCellBgColor(wb, cell, i);
            
            /**教师党员数（正式党员）**/
            // 在职教师
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getPositiveTeacher()));
            toggleCellBgColor(wb, cell, i);
            // 退休教师
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getPositiveTeacherRetire()));
            toggleCellBgColor(wb, cell, i);
            // 总数
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getPositiveTeacherTotal()));
            toggleCellBgColor(wb, cell, i);
            
            //党员总数（正式党员）
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getPositiveStudent()) + getValue(p.getPositiveTeacherTotal()));
            toggleCellBgColor(wb, cell, i);
            
            //教职工申请入党总数（统计 申请至领取志愿书 五个阶段）
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getTeacherApplyCount()));
            toggleCellBgColor(wb, cell, i);
            
            //学生申请入党总数（统计 申请至领取志愿书 五个阶段）
            cell = row.getCell(column++);
            cell.setCellValue(getValue(p.getStudentApplyCount()));
            toggleCellBgColor(wb, cell, i);
        }
        
        return wb;
    }
    
    // 导出列表中分党委的所有委员
    public SXSSFWorkbook export(PartyMemberViewExample example) {
        
        
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        
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
            headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() + "分党委委员");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }
        
        int count = records.size();
        String[] titles = {"工作证号", "姓名", "所在单位", "所属分党委", "职务",
                "分工", "任职时间", "性别", "民族", "身份证号",
                "出生时间", "党派", "党派加入时间", "到校时间", "岗位类别",
                "主岗等级", "专业技术职务", "专技职务等级", "管理岗位等级", "办公电话",
                "手机号", "所属党组织"};
        int columnCount = titles.length;
        Row firstRow = sheet.createRow(rowNum++);
        firstRow.setHeight((short) (35.7 * 12));
        for (int i = 0; i < columnCount; i++) {
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(ExportHelper.getHeadStyle(wb));
        }
        
        int columnIndex = 0;
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 工作证号
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 400));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200)); // 分工
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 出生时间
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));// 主岗等级
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150)); // 手机号
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 500));
        
        for (int i = 0; i < count; i++) {
            PartyMemberView record = records.get(i);
            SysUserView sysUser = record.getUser();
            Member member = memberService.get(record.getUserId());
            
            Map<String, String> cadreParty = CmTag.getCadreParty(record.getIsOw(), record.getOwGrowTime(), "中共党员",
                    record.getDpTypeId(), record.getDpGrowTime(), true);
            String partyName = cadreParty.get("partyName");
            String partyAddTime = cadreParty.get("growTime");
            
            
            String partyFullName = ""; // 所属党组织
            if (member != null && member.getStatus() == MemberConstants.MEMBER_STATUS_NORMAL) {
                if (record.getPartyId() != null) {
                    Party party = partyMap.get(record.getPartyId());
                    if (party != null) {
                        partyFullName = party.getName();
                        if (record.getBranchId() != null) {
                            Branch branch = branchMap.get(record.getBranchId());
                            if (branch != null) {
                                partyFullName += "-" + branch.getName();
                            }
                        }
                    }
                }
            }
            
            List<String> typeNames = new ArrayList();
            String[] _typeIds = StringUtils.split(record.getTypeIds(), ",");
            if (_typeIds != null && _typeIds.length > 0) {
                for (String typeId : _typeIds) {
                    MetaType metaType = metaTypeMap.get(Integer.valueOf(typeId));
                    if (metaType != null) typeNames.add(metaType.getName());
                }
            }
            Unit unit = unitMap.get(record.getUnitId());
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    unit == null ? "" : unit.getName(),
                    partyMap.get(record.getGroupPartyId()).getName(),
                    metaTypeService.getName(record.getPostId()),
                    
                    StringUtils.join(typeNames, ","),
                    DateUtils.formatDate(record.getAssignDate(), DateUtils.YYYYMM),
                    record.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(record.getGender()),
                    record.getNation(),
                    record.getIdcard(),
                    
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                    partyName,
                    partyAddTime,
                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYY_MM_DD),
                    record.getPostClass(),
                    
                    record.getMainPostLevel(),
                    record.getProPost(),
                    record.getProPostLevel(),
                    record.getManageLevel(),
                    record.getOfficePhone(),
                    
                    record.getMobile(),
                    partyFullName
            };
            
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
    
    private static void toggleCellBgColor(XSSFWorkbook wb, XSSFCell cell, int i) {
        
        if (i % 2 == 0) {
            
            XSSFCellStyle style = wb.createCellStyle();
            style.cloneStyleFrom(cell.getCellStyle());
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
    }
    
    private static int getValue(BigDecimal val) {
        
        if (val == null) return 0;
        return val.intValue();
    }
}
