package service.unit;

import domain.unit.Unit;
import domain.unit.UnitExample;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by lm on 2018/6/5.
 */
@Service
public class UnitExportService extends BaseMapper{

    @Autowired
    private MetaTypeService metaTypeService;

    public XSSFWorkbook toXlsx(UnitExample example) throws IOException {

        List<Unit> records = unitMapper.selectByExample(example);

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/unit/unit.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);

        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);

        for (int i = 0; i < rowCount; i++) {

            Unit record = records.get(i);
            int column = 0;
            row = sheet.getRow(startRow++);

            cell = row.getCell(column++);
            cell.setCellValue(record.getCode());

            cell = row.getCell(column++);
            cell.setCellValue(record.getName());

            cell = row.getCell(column++);
            cell.setCellValue(metaTypeService.getName(record.getTypeId()));

            cell = row.getCell(column++);
            cell.setCellValue(DateUtils.formatDate(record.getWorkTime(), "yyyy.MM"));

            cell = row.getCell(column++);
            cell.setCellValue(record.getUrl());

            cell = row.getCell(column++);
            cell.setCellValue(record.getRemark());
        }

        return wb;
    }
}
