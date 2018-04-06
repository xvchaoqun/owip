package service.cet;

import domain.cet.CetTrain;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lm on 2017/8/27.
 */
@Service
public class CetExportService extends BaseMapper {

    /**
     * 附件7. xxx党校培训情况统计表.xlsx
     */
    public XSSFWorkbook cetTrainStatExport(int trainId) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cet/train-stat.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

        row = sheet.getRow(2);
        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("sn", /*cetTrain.getSn()*/ "");
        cell.setCellValue(str);

        row = sheet.getRow(3);
        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("trainName", cetTrain.getName());
        cell.setCellValue(str);

        row = sheet.getRow(4);
        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("time", DateUtils.formatDate(cetTrain.getStartDate(), DateUtils.YYYY_MM_DD_CHINA)
                        + "——" + DateUtils.formatDate(cetTrain.getEndDate(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);


        /*List<PcsPrPartyBean> records = iPcsMapper.selectPcsPrPartyBeans(configId, stage, null, null, null, new RowBounds());
        int startRow = 10;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 分党委名称
            cell = row.getCell(column++);
            cell.setCellValue(bean.getName());
        }*/

        // 合计
        /*row = sheet.getRow(startRow++);
        int column = 2;

        // 党员总数
        cell = row.getCell(column++);
        cell.setCellValue(memberCount);

        // 正式党员数
        cell = row.getCell(column++);
        cell.setCellValue(positiveCount);
        */

        return wb;
    }

}
