package service.party;

import domain.party.PartyStaticView;
import domain.party.PartyStaticViewExample;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import persistence.party.PartyStaticViewMapper;
import service.BaseMapper;
import sys.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PartyExportService extends BaseMapper {

    @Autowired
    private PartyStaticViewMapper partyStaticViewMapper;

   /* public static void main(String[] args) throws IOException {

        XSSFWorkbook xssfSheets = toXlsx();
        ExportHelper.save(xssfSheets, "D:/tmp/test2.xlsx");
    }*/
    // 汇总导出
    public XSSFWorkbook toXlsx() throws IOException {

        List<PartyStaticView> records = partyStaticViewMapper.selectByExample(new PartyStaticViewExample());
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/party_stat_template.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row;

        int cpRow = 3;
        int rowCount = records.size();
        if (rowCount > 1)
            ExcelUtils.copyRowsToEnd(wb, sheet, cpRow, rowCount - 1);

        int startRow = cpRow;
        
        for (int i = 0; i < rowCount; i++) {

            PartyStaticView p = records.get(i);
            int column = 0;
            row = sheet.getRow(startRow++);

            XSSFCell cell = row.getCell(column++);
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
        }

        return wb;
    }

    private static void  toggleCellBgColor(XSSFWorkbook wb, XSSFCell cell, int i){

        if(i%2==0) {

            XSSFCellStyle style = wb.createCellStyle();
            style.cloneStyleFrom(cell.getCellStyle());
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
    }
    private static int getValue(BigDecimal val) {

        if (val == null) return 0;
        return val.intValue();
    }
}
