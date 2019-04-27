package bean;

import controller.global.OpException;
import domain.base.MetaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class XlsUpload {

    public static Logger logger = LoggerFactory.getLogger(XlsUpload.class);

    // 每行数据key从0开始计数
    public static Map<Integer, String> getXlsTitleRow(XSSFSheet sheet) {

        List<Map<Integer, String>> xlsRows = new ArrayList<>();
        XSSFRow rowTitle = sheet.getRow(0);

        int cellNum = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum(); // 列数

        Map<Integer, String> xlsRow = new HashMap<>();
        for (int j = 0; j < cellNum; j++) {

            XSSFCell cell = rowTitle.getCell(j);
            String val = null;
            if (cell != null) {
                val = getCellValue(cell);
            }

            xlsRow.put(j, StringUtils.trimToNull(val));
        }

        return xlsRow;
    }

    // 每行数据key从0开始计数
    public static List<Map<Integer, String>> getXlsRows(XSSFSheet sheet) {

        List<Map<Integer, String>> xlsRows = new ArrayList<>();
        XSSFRow rowTitle = sheet.getRow(0);
        //System.out.println(rowTitle);
        if (null == rowTitle) return xlsRows; // 第一行标题

        int cellNum = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum(); // 列数

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            // 行数据如果为空，不处理
            if (row == null) continue;

            boolean allColumnIsNull = true;
            Map<Integer, String> xlsRow = new HashMap<>();
            for (int j = 0; j < cellNum; j++) {

                XSSFCell cell = row.getCell(j);
                String val = null;
                if (cell != null) {
                    val = getCellValue(cell);
                    if (StringUtils.isNotBlank(val)) {
                        allColumnIsNull = false;
                    }
                }

                xlsRow.put(j, StringUtils.trimToNull(val));
            }
            // 如果所有的列都是空的，不处理
            if (!allColumnIsNull) xlsRows.add(xlsRow);
        }

        return xlsRows;
    }

    public static String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            //return cell.getCellFormula();
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
            //System.out.println("format:"+format+";;;;;value:"+cell.getNumericCellValue());
            SimpleDateFormat sdf = null;
            if (format == 14 || format == 31 || format == 57 || format == 58
                    || (176 <= format && format <= 178) || (182 <= format && format <= 196)
                    || (210 <= format && format <= 213) || (208 == format)) { // 日期
                sdf = new SimpleDateFormat(DateUtils.YYYY_MM_DD);
            } else if (format == 22) { // 时间
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            }  else if (format == 20 || format == 32 || format == 183 || (200 <= format && format <= 209)) { // 时间
                sdf = new SimpleDateFormat("HH:mm");
            } else { // 不是日期格式
                //return String.valueOf(cell.getNumericCellValue());
                cell.setCellType(CellType.STRING);
                return cell.getStringCellValue();
            }
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            if (date == null || "".equals(date)) {
                return "";
            }
            String result = "";
            try {
                result = sdf.format(date);
            } catch (Exception e) {
                logger.error("异常", e);
                return "";
            }
            return result;
        }
        return "";
    }
}
