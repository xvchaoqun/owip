package service.cadre;

import domain.cadre.CadreStatHistory;
import domain.cadre.CadreViewExample;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.analysis.StatCadreService;
import sys.constants.SystemConstants;
import sys.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class CadreStatHistoryService extends BaseMapper {

    @Autowired
    private StatCadreService statCadreService;
    @Autowired
    private CadreExportService cadreExportService;
    @Autowired
    protected SpringProps springProps;

    public void asyncStatAll() throws IOException {
        try {
            saveCadreExport();
            saveStatCadreExport();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 保存中层干部信息表
    public void saveCadreExport() throws IOException {

        long start = System.currentTimeMillis();

        byte status = SystemConstants.CADRE_STATUS_MIDDLE;
        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");
        SXSSFWorkbook wb = cadreExportService.export(status, example);
        String savePath = FILE_SEPARATOR + "cadre_stat_history" + FILE_SEPARATOR
                + UUID.randomUUID().toString() + ".xlsx";

        FileUtils.mkdirs(springProps.uploadPath + savePath);
        FileOutputStream output = new FileOutputStream(savePath);
        wb.write(output);
        output.close();

        long end = System.currentTimeMillis();

        Date now = new Date();
        CadreStatHistory record = new CadreStatHistory();
        record.setSavePath(savePath);
        record.setCreateTime(now);
        record.setStatDate(now);
        record.setDownloadCount(0);
        record.setDuration((int) (end - start));
        record.setType(SystemConstants.CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE);
        cadreStatHistoryMapper.insertSelective(record);
    }

    // 保存中层干部情况统计表
    public void saveStatCadreExport() throws IOException {

        long start = System.currentTimeMillis();

        XSSFWorkbook wb = statCadreService.toXlsx();
        String savePath = FILE_SEPARATOR + "cadre_stat_history" + FILE_SEPARATOR
                + UUID.randomUUID().toString() + ".xlsx";
        FileOutputStream output = new FileOutputStream(new File(springProps.uploadPath + savePath));
        wb.write(output);
        output.close();

        long end = System.currentTimeMillis();

        Date now = new Date();
        CadreStatHistory record = new CadreStatHistory();
        record.setSavePath(savePath);
        record.setCreateTime(now);
        record.setStatDate(now);
        record.setDownloadCount(0);
        record.setDuration((int) (end - start));
        record.setType(SystemConstants.CADRE_STAT_HISTORY_TYPE_STAT_CADRE);
        cadreStatHistoryMapper.insertSelective(record);
    }
}
