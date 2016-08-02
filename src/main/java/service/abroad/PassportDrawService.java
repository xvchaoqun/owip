package service.abroad;

import domain.abroad.*;
import domain.cadre.Cadre;
import domain.sys.SysUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PassportDrawService extends BaseMapper {

    public List<PassportDrawFile> getPassportDrawFiles(int drawId){

        PassportDrawFileExample example = new PassportDrawFileExample();
        example.createCriteria().andDrawIdEqualTo(drawId);
        return passportDrawFileMapper.selectByExample(example);
    }

    @Transactional
    public int insertSelective(PassportDraw record){

        return passportDrawMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        passportDrawMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportDrawExample example = new PassportDrawExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        passportDrawMapper.deleteByExample(example);
    }
    // 领取证件
    @Transactional
    public void drawPassport(PassportDraw record){

        updateByPrimaryKeySelective(record);

        // 将证件标记为已借出
        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        Passport _record = new Passport();
        _record.setId(passport.getId());
        _record.setIsLent(true);
        passportMapper.updateByPrimaryKeySelective(_record);
    }

    // 归还证件
    @Transactional
    public void returnPassport(PassportDraw record){

        updateByPrimaryKeySelective(record);

        // 将证件标记为已借出
        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(record.getId());
        Passport passport = passportMapper.selectByPrimaryKey(passportDraw.getPassportId());
        Passport _record = new Passport();
        _record.setId(passport.getId());
        _record.setIsLent(false);
        passportMapper.updateByPrimaryKeySelective(_record);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PassportDraw record){
        return passportDrawMapper.updateByPrimaryKeySelective(record);
    }

    // 使用记录导出
    public void passportDraw_export(PassportDrawExample example, HttpServletResponse response) {

        List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);
        int rownum = passportDrawMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        int rowNum = 0;
        {
            Row titleRow = sheet.createRow(rowNum);
            titleRow.setHeight((short) (35.7 * 30));
            Cell headerCell = titleRow.createCell(0);
            XSSFCellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
            XSSFFont font = wb.createFont();
            // 设置字体加粗
            font.setFontName("宋体");
            font.setFontHeight((short) 350);
            cellStyle.setFont(font);
            headerCell.setCellStyle(cellStyle);
            headerCell.setCellValue(PropertiesUtils.getString("site.school") + "中层干部因私出国（境）证件使用记录");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 14));
            rowNum++;
        }

        XSSFRow firstRow = (XSSFRow) sheet.createRow(rowNum++);

        String[] titles = {"序号", "工作证号", "姓名", "所在单位及职务", "证件名称",
                "证件号码", "申请日期", "申请编码", "因私出国（境）行程"
                , "出行时间", "回国时间", "前往国家或地区", "因私出国境事由", "借出日期", "归还日期"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(getHeadStyle(wb));
        }
        sheet.setColumnWidth(0, (short) (35.7 * 50));
        sheet.setColumnWidth(1, (short) (35.7 * 100));
        sheet.setColumnWidth(2, (short) (35.7 * 50));
        sheet.setColumnWidth(3, (short) (35.7 * 250));
        sheet.setColumnWidth(4, (short) (35.7 * 150));
        sheet.setColumnWidth(5, (short) (35.7 * 100));
        sheet.setColumnWidth(6, (short) (35.7 * 100));
        sheet.setColumnWidth(7, (short) (35.7 * 100));
        sheet.setColumnWidth(8, (short) (35.7 * 180));
        sheet.setColumnWidth(9, (short) (35.7 * 100));
        sheet.setColumnWidth(10, (short) (35.7 * 100));
        sheet.setColumnWidth(11, (short) (35.7 * 150));
        sheet.setColumnWidth(12, (short) (35.7 * 150));
        sheet.setColumnWidth(13, (short) (35.7 * 100));
        sheet.setColumnWidth(14, (short) (35.7 * 100));

        for (int i = 0; i < rownum; i++) {
            PassportDraw passportDraw = passportDraws.get(i);
            Cadre cadre = passportDraw.getCadre();
            SysUser sysUser = passportDraw.getUser();
            Passport passport = passportDraw.getPassport();
            ApplySelf applySelf = passportDraw.getApplySelf();
            String xingcheng = "";
            String startDate = "";
            String endDate = "";
            String toCountry = "";
            String reason = passportDraw.getReason();

            if(passportDraw.getType()== SystemConstants.PASSPORT_DRAW_TYPE_SELF){
                xingcheng = "S"+applySelf.getId();
                startDate = DateUtils.formatDate(applySelf.getApplyDate(), DateUtils.YYYY_MM_DD);
                endDate = DateUtils.formatDate(applySelf.getEndDate(), DateUtils.YYYY_MM_DD);
                toCountry = applySelf.getToCountry();
                reason = applySelf.getReason();
            }else if(passportDraw.getType()==SystemConstants.PASSPORT_DRAW_TYPE_TW){
                xingcheng = "T"+passportDraw.getId();
                startDate = DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD);
                endDate = DateUtils.formatDate(passportDraw.getEndDate(), DateUtils.YYYY_MM_DD);
                toCountry="台湾";
            }else if(passportDraw.getType()==SystemConstants.PASSPORT_DRAW_TYPE_OTHER){
                xingcheng = "Q"+passportDraw.getId();
            }


            String[] values = {
                    String.valueOf(i+1),
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    cadre.getTitle(),
                    passport.getPassportClass().getName(),
                    passport.getCode(),
                    DateUtils.formatDate(passportDraw.getApplyDate(), DateUtils.YYYY_MM_DD),
                    String.format("A%s", passportDraw.getId()),
                    xingcheng,
                    startDate, endDate, toCountry, reason,
                    DateUtils.formatDate(passportDraw.getDrawTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(passportDraw.getRealReturnDate(), DateUtils.YYYY_MM_DD),
            };

            Row row = sheet.createRow(rowNum++);

            for (int j = 0; j < titles.length; j++) {
                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(getBodyStyle(wb));
            }
        }
        try {
            String fileName = "因私出国（境）证件使用记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        //font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static XSSFCellStyle getHeadStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
       /* cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);*/
        return cellStyle;
    }
}
