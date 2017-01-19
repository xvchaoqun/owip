package service.analysis;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@Service
public class CadreStatExportService1 extends BaseMapper {

    @Autowired
    private StatCadreService cadreStatService;

    private static Sheet fromSheet = null;
    private static float heightInPoints;
    //private static Map<String, List> dataMap;

    public XSSFWorkbook toXlsx() throws IOException {

        //InputStream is = new FileInputStream("cadre_template.xlsx");
        InputStream is = getClass().getResourceAsStream("/xlsx/cadre_template.xlsx");
        XSSFWorkbook wb0=new XSSFWorkbook(is);
        fromSheet = wb0.getSheetAt(0);
        heightInPoints = fromSheet.getRow(1).getHeightInPoints();
        System.out.println("heightInPoints=" + heightInPoints);

        XSSFWorkbook wb =new XSSFWorkbook();

        createSheet(wb, null);
        createSheet(wb, SystemConstants.UNIT_TYPE_ATTR_JG);
        createSheet(wb, SystemConstants.UNIT_TYPE_ATTR_XY);
        createSheet(wb, SystemConstants.UNIT_TYPE_ATTR_FS);

        return wb;
    }

    public void createSheet(XSSFWorkbook wb, String type)  {

        String sheetName = "所有中层干部";
        String title = "所有干部";
        if(type==SystemConstants.UNIT_TYPE_ATTR_JG){
            sheetName = "机关及直属单位";
            title = "机关部处及直属、教辅单位";
        }else if(type==SystemConstants.UNIT_TYPE_ATTR_XY){
            sheetName = "学部、院、系所";
            title = "学部、院、系所";
        }else if(type==SystemConstants.UNIT_TYPE_ATTR_FS){
            sheetName = "附属单位";
            title = "附属单位";
        }
        Sheet sheet=wb.createSheet(sheetName);
        // Set the width (in units of 1/256th of a character width)
        //sheet.setDefaultColumnWidth(10*256);
        //sheet.setDefaultRowHeightInPoints(17.25f);
        sheet.setDefaultColumnWidth(fromSheet.getDefaultColumnWidth());
        sheet.setColumnWidth(0, fromSheet.getColumnWidth(0));
        sheet.setColumnWidth(1, fromSheet.getColumnWidth(1));
        PrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)
        //ps.setVResolution((short)600);
        ps.setPaperSize(PrintSetup.A4_SMALL_PAPERSIZE); //纸张类型
        //sheet.setPrintGridlines(true);

        sheet.setMargin(Sheet.TopMargin, fromSheet.getMargin(Sheet.TopMargin));
        sheet.setMargin(Sheet.RightMargin, fromSheet.getMargin(Sheet.RightMargin));
        sheet.setMargin(Sheet.BottomMargin, fromSheet.getMargin(Sheet.BottomMargin));
        sheet.setMargin(Sheet.LeftMargin, fromSheet.getMargin(Sheet.LeftMargin));
        Header header = sheet.getHeader();
        //自定义页眉,并设置页眉 左中右显示信息
        header.setRight(HSSFHeader.font("宋体", "regular") +
                HSSFHeader.fontSize((short) 11) + "截至" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        int rowNum = 0;
        rowNum = titleRow(sheet, rowNum, wb, "北京师范大学中层领导干部情况统计表（"+title+"）");
        rowNum = headerRow(sheet, rowNum, wb);
        rowNum = row1(sheet, rowNum, wb);
        rowNum = row234(sheet, rowNum, wb);
        rowNum = row56(sheet, rowNum, wb);
        rowNum = row78(sheet, rowNum, wb);
        rowNum = row9_15(sheet, rowNum, wb);
        rowNum = row16_21(sheet, rowNum, wb);
        rowNum = row22_25(sheet, rowNum, wb);
        row26(sheet, rowNum, wb);

        rowNum = 3;
        Map<String, List> dataMap = cadreStatService.stat(type);
        for (Map.Entry<String, List> entry : dataMap.entrySet()) {

            List rowData = entry.getValue();
            createDataCells(wb, 2, sheet.getRow(rowNum), rowData);
            rowNum++;
        }
    }

    private static int row1(Sheet sheet,int rowNum,  XSSFWorkbook wb) {

        Row row1 = sheet.createRow(rowNum);
        row1.setHeightInPoints(heightInPoints);
        createCell(wb, row1, getThStyle(wb), 0, "总数");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 1);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);

        return rowNum + 1;
    }

    public static void createDataCells(XSSFWorkbook wb, int columnNum, Row row, List data){

        int size = data.size();
        for (int i = 0; i < size; i++) {
            XSSFCellStyle thStyle = getThStyle(wb);
            thStyle.getFont().setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
            if(columnNum==3 || columnNum == 9){
                thStyle.setBorderRight(CellStyle.BORDER_DOUBLE);
            }
            if(i%2==0) {
                createNumCell(wb, row, thStyle, columnNum, (Integer)data.get(i));
            }else {
                createCell(wb, row, thStyle, columnNum, String.valueOf(data.get(i)));
            }
            columnNum++;
        }
    }

    private static int row234(Sheet sheet,int rowNum,  XSSFWorkbook wb) {

        Row row2 = sheet.createRow(rowNum);
        row2.setHeightInPoints(heightInPoints);
        createCell(wb, row2, getThStyle(wb), 0, "正处");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 1);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);

        rowNum++;
        Row row3 = sheet.createRow(rowNum);
        row3.setHeightInPoints(heightInPoints);
        createCell(wb, row3, getThStyle(wb), 0, "副处");
        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 1);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);

        rowNum++;
        Row row4 = sheet.createRow(rowNum);
        row4.setHeightInPoints(heightInPoints);
        createCell(wb, row4, getThStyle(wb), 0, "聘任制（无级别）");
        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 1);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);

        return rowNum + 1;
    }

    private static int row56(Sheet sheet,int rowNum,  XSSFWorkbook wb) {

        Row row5 = sheet.createRow(rowNum);
        row5.setHeightInPoints(heightInPoints);
        createCell(wb, row5, getThStyle(wb), 0, "民\r\n族");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum+1, 0);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);
        createCell(wb, row5, getThStyle(wb), 1, "汉族");

        rowNum++;
        Row row6 = sheet.createRow(rowNum);
        row6.setHeightInPoints(heightInPoints);
        createCell(wb, row6, getThStyle(wb), 1, "少数民族");

        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum-1, 0, rowNum, 1);
        setRegionBorder(sheet, cellRangeAddress, wb);

        return rowNum + 1;
    }

    private static int row78(Sheet sheet,int rowNum,  XSSFWorkbook wb) {

        Row row7 = sheet.createRow(rowNum);
        row7.setHeightInPoints(heightInPoints);
        createCell(wb, row7, getThStyle(wb), 0, "党\r\n派");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum+1, 0);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);
        createCell(wb, row7, getThStyle(wb), 1, "中共党员");

        rowNum++;
        Row row8 = sheet.createRow(rowNum);
        row8.setHeightInPoints(heightInPoints);
        createCell(wb, row8, getThStyle(wb), 1, "民主党派");

        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum-1, 0, rowNum, 1);
        setRegionBorder(sheet, cellRangeAddress, wb);

        return rowNum + 1;
    }

    private static int row9_15(Sheet sheet,int rowNum,  XSSFWorkbook wb) {

        Row row9 = sheet.createRow(rowNum);
        row9.setHeightInPoints(heightInPoints);
        createCell(wb, row9, getThStyle(wb), 0, "年\r\n龄\r\n分\r\n布");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum+6, 0);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);
        createCell(wb, row9, getThStyle(wb), 1, "30岁及以下");

        rowNum++;
        Row row10 = sheet.createRow(rowNum);
        row10.setHeightInPoints(heightInPoints);
        createCell(wb, row10, getThStyle(wb), 1, "31-35岁");

        rowNum++;
        Row row11 = sheet.createRow(rowNum);
        row11.setHeightInPoints(heightInPoints);
        createCell(wb, row11, getThStyle(wb), 1, "36-40岁");

        rowNum++;
        Row row12 = sheet.createRow(rowNum);
        row12.setHeightInPoints(heightInPoints);
        createCell(wb, row12, getThStyle(wb), 1, "41-45岁");

        rowNum++;
        Row row13 = sheet.createRow(rowNum);
        row13.setHeightInPoints(heightInPoints);
        createCell(wb, row13, getThStyle(wb), 1, "46-50岁");

        rowNum++;
        Row row14 = sheet.createRow(rowNum);
        row14.setHeightInPoints(heightInPoints);
        createCell(wb, row14, getThStyle(wb), 1, "51-55岁");

        rowNum++;
        Row row15 = sheet.createRow(rowNum);
        row15.setHeightInPoints(heightInPoints);
        createCell(wb, row15, getThStyle(wb), 1, "55岁以上");

        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum-6, 0, rowNum, 1);
        setRegionBorder(sheet, cellRangeAddress, wb);

        return rowNum + 1;
    }

    private static int row16_21(Sheet sheet,int rowNum,  XSSFWorkbook wb) {

        Row row16 = sheet.createRow(rowNum);
        row16.setHeightInPoints(heightInPoints);
        createCell(wb, row16, getThStyle(wb), 0, "职\r\n称\r\n分\r\n布");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum+5, 0);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);
        createCell(wb, row16, getThStyle(wb), 1, "正高(总)");

        rowNum++;
        Row row17 = sheet.createRow(rowNum);
        row17.setHeightInPoints(heightInPoints);
        createCell(wb, row17, getThStyle(wb), 1, "正高(二级)");

        rowNum++;
        Row row18 = sheet.createRow(rowNum);
        row18.setHeightInPoints(heightInPoints);
        createCell(wb, row18, getThStyle(wb), 1, "正高(三级)");

        rowNum++;
        Row row19 = sheet.createRow(rowNum);
        row19.setHeightInPoints(heightInPoints);
        createCell(wb, row19, getThStyle(wb), 1, "正高(四级)");

        rowNum++;
        Row row20 = sheet.createRow(rowNum);
        row20.setHeightInPoints(heightInPoints);
        createCell(wb, row20, getThStyle(wb), 1, "副高");

        rowNum++;
        Row row21 = sheet.createRow(rowNum);
        row21.setHeightInPoints(heightInPoints);
        createCell(wb, row21, getThStyle(wb), 1, "中级及以下");

        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum-5, 0, rowNum, 1);
        setRegionBorder(sheet, cellRangeAddress, wb);

        return rowNum + 1;
    }

    private static int row22_25(Sheet sheet,int rowNum,  XSSFWorkbook wb) {

        Row row22 = sheet.createRow(rowNum);
        row22.setHeightInPoints(heightInPoints);
        createCell(wb, row22, getThStyle(wb), 0, "学\r\n历\r\n分\r\n布");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum+3, 0);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);
        createCell(wb, row22, getThStyle(wb), 1, "博士");

        rowNum++;
        Row row23 = sheet.createRow(rowNum);
        row23.setHeightInPoints(heightInPoints);
        createCell(wb, row23, getThStyle(wb), 1, "硕士");

        rowNum++;
        Row row24 = sheet.createRow(rowNum);
        row24.setHeightInPoints(heightInPoints);
        createCell(wb, row24, getThStyle(wb), 1, "学士");

        rowNum++;
        Row row25 = sheet.createRow(rowNum);
        row25.setHeightInPoints(heightInPoints);
        createCell(wb, row25, getThStyle(wb), 1, "大专");

        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum-3, 0, rowNum, 1);
        setRegionBorder(sheet, cellRangeAddress, wb);

        return rowNum + 1;
    }

    private static int row26(Sheet sheet,int rowNum,  XSSFWorkbook wb) {

        Row row26 = sheet.createRow(rowNum);
        row26.setHeightInPoints(heightInPoints);
        createCell(wb, row26, getThStyle(wb), 0, "专职干部");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 1);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);

        return rowNum + 1;
    }

    private static int titleRow(Sheet sheet,int rowNum,  XSSFWorkbook wb, String title) {

        Row header = sheet.createRow(rowNum);
        header.setHeightInPoints(fromSheet.getRow(0).getHeightInPoints());
        createCell(wb, header, getTitleStyle(wb), 0, title);
        sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 13));

        return rowNum + 1;
    }

    private static int  headerRow(Sheet sheet, int rowNum, XSSFWorkbook wb) {

        Row row1 = sheet.createRow(rowNum);
        row1.setHeightInPoints(heightInPoints);
        createCell(wb, row1, getThStyle(wb), 0, "类别");
        CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 0, rowNum + 1, 1);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);

        createCell(wb, row1, getThStyle(wb), 2, "总体");
        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 2, rowNum, 3);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);
        RegionUtil.setBorderRight(CellStyle.BORDER_DOUBLE, cellRangeAddress, sheet, wb);

        createCell(wb, row1, getThStyle(wb), 4, "行政级别");
        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 4, rowNum, 9);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);
        RegionUtil.setBorderRight(CellStyle.BORDER_DOUBLE, cellRangeAddress, sheet, wb);

        createCell(wb, row1, getThStyle(wb), 10, "性别");
        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, 10, rowNum, 13);
        sheet.addMergedRegion(cellRangeAddress);
        setRegionBorder(sheet, cellRangeAddress, wb);

        Row row2 = sheet.createRow(rowNum +1);
        row2.setHeightInPoints(heightInPoints);
        rowNum = rowNum +1;
        int columnNum = 0;
        cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum, rowNum, columnNum+1);
        setRegionBorder(sheet, cellRangeAddress, wb);
        columnNum = columnNum+2;
        createCell(wb, row2, getThStyle(wb), columnNum, "人数");
        columnNum++;
        XSSFCellStyle thStyle = getThStyle(wb);
        thStyle.setBorderRight(CellStyle.BORDER_DOUBLE);
        createCell(wb, row2, thStyle, columnNum, "比率");
        //cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum, rowNum, columnNum);
        //sheet.addMergedRegion(cellRangeAddress);
        //RegionUtil.setBorderRight(CellStyle.BORDER_DOUBLE, cellRangeAddress, sheet, wb);

        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "正处");
        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "比率");
        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "副处");
        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "比率");
        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "无级别");
        columnNum++;
        createCell(wb, row2, thStyle, columnNum, "比率");
        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "男");
        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "比率");
        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "女");
        columnNum++;
        createCell(wb, row2, getThStyle(wb), columnNum, "比率");

        return rowNum + 1;
    }

    public static void setRegionBorder(Sheet sheet, CellRangeAddress cellRangeAddress, XSSFWorkbook wb) {

        RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
        RegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
    }

    private static XSSFCellStyle getThStyle(XSSFWorkbook wb){

        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);

        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeight((short) (20*11));
        cellStyle.setFont(font);

        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

        return cellStyle;
    }

    private static XSSFCellStyle getTitleStyle(XSSFWorkbook wb){

        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        font.setFontName("华文中宋");
        font.setFontHeight((short) (20*18));
        cellStyle.setFont(font);

        return cellStyle;
    }

    private static void createCell(XSSFWorkbook wb, Row row, CellStyle style, int column, String text) {
        Cell cell=row.createCell(column);
        cell.setCellValue(text);
        cell.setCellStyle(style);
    }

    private static void createNumCell(XSSFWorkbook wb, Row row, CellStyle style, int column, double num) {
        Cell cell=row.createCell(column);
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(num);
        cell.setCellStyle(style);
    }
}
