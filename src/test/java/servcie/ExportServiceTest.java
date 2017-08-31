package servcie;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import service.analysis.StatCadreService;
import service.member.MemberStayExportService;
import service.party.PartyExportService;
import sys.constants.SystemConstants;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fafa on 2015/11/9.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ExportServiceTest {

    @Autowired
    StatCadreService cadreStatService;

    @Autowired
    MemberStayExportService memberStayExportService;

    @Autowired
    PartyExportService partyExportService;

    public XSSFFont getFont(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setUnderline(Font.U_SINGLE); //下划线
        return font;
    }

    private XSSFRichTextString UnderLineIndex(String str, Font font) {
        XSSFRichTextString richString = null;
        richString = new XSSFRichTextString(str);
        richString.applyFont(6, 30, font);  //下划线的起始位置，结束位置
        richString.applyFont(39, 50, font);

        return richString;
    }

    @Test
    public void ex() throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/dw-2-1.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue().replace("name", "是山水电费水电费水电费是的上大三的大队东师范").replace("branchCount", "13");
        cell.setCellValue(str);
        //cell.setCellValue(UnderLineIndex(str, getFont(wb)));

        int startRow = 5;
        int rowCount = 20;
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount-1);
        for (int i = 0; i < rowCount; i++) {

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);
        }


        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test111.xlsx"));
        wb.write(output);
        output.close();
    }

    @Test
    public void ex2() throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/pcs/pr-3.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue().replace("stage", "一下一上")
                .replace("deadline", "9月6号前");
        cell.setCellValue(str);
        //cell.setCellValue(UnderLineIndex(str, getFont(wb)));

        row = sheet.getRow(1);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("party", "中共北京师范大学教育学部委员会");
        cell.setCellValue(str);

        row = sheet.getRow(2);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("mc", "22")
                .replace("tc", "11").replace("sc", "11").replace("rc", "11");
        cell.setCellValue(str);


        row = sheet.getRow(3);
        cell = row.getCell(0);
        str = cell.getStringCellValue().replace("nextStageShort", "二下");
        cell.setCellValue(str);


        int startRow = 5;
        int rowCount = 8;
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount-1);
        for (int i = 0; i < rowCount; i++) {

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);
        }

        row = sheet.getRow(startRow);
        cell = row.getCell(0);
        cell.setCellValue("日期： 2017年8月31日");

        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test111.xlsx"));
        wb.write(output);
        output.close();
    }

    @Test
    public void stat() throws IOException {

        XSSFWorkbook wb = cadreStatService.toXlsx();
        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test2.xlsx"));

        wb.write(output);
        output.close();
    }
    @Test
    public void partyExportService() throws IOException {

        XSSFWorkbook wb = partyExportService.toXlsx();
        ExportHelper.save(wb, "D:/tmp/test23.xlsx");
    }
    @Test
    public void memberStayExportService() throws IOException {

        SXSSFWorkbook wb = memberStayExportService.toXlsx(SystemConstants.MEMBER_STAY_TYPE_ABROAD);
        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test222.xlsx"));

        wb.write(output);
        output.close();

        wb = memberStayExportService.toXlsx(SystemConstants.MEMBER_STAY_TYPE_INTERNAL);
        output = new FileOutputStream(new File("D:/tmp/test222.xlsx"));

        wb.write(output);
        output.close();
    }

}
