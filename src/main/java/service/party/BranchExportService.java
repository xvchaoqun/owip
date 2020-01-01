package service.party;

import domain.member.Member;
import domain.party.Branch;
import domain.party.BranchMemberView;
import domain.party.BranchMemberViewExample;
import domain.party.Party;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.unit.UnitService;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class BranchExportService extends BaseMapper {

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
    
    // 导出列表中支部的所有委员
    public SXSSFWorkbook export(BranchMemberViewExample example) {
        
        
        //Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<BranchMemberView> records = branchMemberViewMapper.selectByExample(example);
        
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
            headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() + "支部委员");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }
        
        int count = records.size();
        String[] titles = {"工作证号", "姓名", "所在单位", "所属分党委", "所属支部", "类别",
                "任职时间","性别", "民族", "身份证号",
                "出生时间", "政治面貌", "党派加入时间", "到校时间", /*"岗位类别",
                "主岗等级",*/ "专业技术职务", "职称级别", /*"管理岗位等级",*/ "办公电话",
                "手机号", "所在党组织"};
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
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 400));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));// 性别
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 出生时间
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        /*sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));// 主岗等级*/
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        /*sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));*/
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150)); // 手机号
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 500));
        
        for (int i = 0; i < count; i++) {
            BranchMemberView record = records.get(i);
            SysUserView sysUser = record.getUser();
            Member member = memberService.get(record.getUserId());
            
            Map<String, String> cadreParty = CmTag.getCadreParty(record.getIsOw(), record.getOwGrowTime(), record.getOwPositiveTime(), "中共党员",
                    record.getDpTypeId(), record.getDpGrowTime(), true);
            String partyName = cadreParty.get("partyName");
            String partyAddTime = cadreParty.get("growTime");
            
            
            String partyFullName = ""; // 所在党组织
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

            Unit unit = unitMap.get(record.getUnitId());
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    unit == null ? "" : unit.getName(),
                    partyMap.get(record.getGroupPartyId()).getName(),
                    record.getGroupBranchId()==null?"":branchMap.get(record.getGroupBranchId()).getName(),
                    metaTypeService.getName(record.getTypeId()),

                    DateUtils.formatDate(record.getAssignDate(), DateUtils.YYYYMM),
                    record.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(record.getGender()),
                    record.getNation(),
                    record.getIdcard(),
                    
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                    partyName,
                    partyAddTime,
                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYY_MM_DD),
                    /*record.getPostClass(),
                    
                    record.getMainPostLevel(),*/
                    record.getProPost(),
                    record.getProPostLevel(),
                    /*record.getManageLevel(),*/
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
