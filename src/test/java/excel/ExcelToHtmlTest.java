package excel;

import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import sys.utils.ExcelToHtmlUtils;

import java.io.*;

/**
 * @author Devil
 * @功能描述 POI 读取 Excel 转 HTML 支持 03xls 和 07xlsx 版本  包含样式
 * @创建时间 2015/4/19 21:34
 */
public class ExcelToHtmlTest {

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {

        int maxRow = 11;
        int maxCol = 13;
        String path = "E:\\excel\\附件6 统计表三.xls";//E://Microsoft Excel 工作表.xlsx

        //String path = "E:\\excel\\附件8 统计表五.xls";//E://Microsoft Excel 工作表.xlsx

        /*
        int maxRow = 19;
        int maxCol = 22;
        //String path = "E:\\excel\\附件5 统计表二.xls";//E://Microsoft Excel 工作表.xlsx
        String path = "E:\\excel\\text.xls";//E://Microsoft Excel 工作表.xlsx
        */

        String html = ExcelToHtmlUtils.toHtml(path, true, maxRow, maxCol);

    }

    @Test
    public void t4() throws IOException {

        String path = "E:\\excel\\附件5 统计表二.xls";
        InputStream is = null;
        FileOutputStream outputStream = null;

        try {

            //本地excel文件
            File sourcefile = new File(path);
            is = new FileInputStream(sourcefile);
            Workbook wb = WorkbookFactory.create(is);

            //获取第 1 个sheet的第 16 row的第 4 cell
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(15);
            Cell cell = row.getCell(3);
            String value = ExcelToHtmlUtils.getCellValue(cell);
            cell.setCellValue(10);//将单元格的内容修改为 10


            cell = row.getCell(7);
            cell.setCellValue(10);//将单元格的内容修改为 10
            cell = row.getCell(11);
            cell.setCellValue(10);//将单元格的内容修改为 10

            row = sheet.getRow(12);
            cell = row.getCell(7);
            cell.setCellValue(11);//将单元格的内容修改为 10
            cell = row.getCell(11);
            cell.setCellValue(10);//将单元格的内容修改为 10

            sheet.setForceFormulaRecalculation(true);//强制执行sheet的函数
            outputStream = new FileOutputStream("E:\\excel\\text.xls");

            wb.write(outputStream);

        } catch (Exception e) {
            e.getMessage();
        } finally {

            is.close();
            outputStream.close();
        }
    }
}