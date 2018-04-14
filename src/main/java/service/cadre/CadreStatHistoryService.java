package service.cadre;

import domain.cadre.CadreStatHistory;
import domain.cadre.CadreViewExample;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.analysis.StatCadreService;
import service.cpc.CpcAllocationService;
import sys.constants.CadreConstants;
import sys.utils.DateUtils;
import sys.utils.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class CadreStatHistoryService extends BaseMapper {

    @Autowired
    private CpcAllocationService cpcAllocationService;
    @Autowired
    private StatCadreService statCadreService;
    @Autowired
    private CadreExportService cadreExportService;
    @Autowired
    protected SpringProps springProps;

    // 保存历史信息表
    public void saveExport(byte type) throws IOException {

        long start = System.currentTimeMillis();

        Workbook wb = null;
        switch (type) {
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE:

                byte status = CadreConstants.CADRE_STATUS_MIDDLE;
                CadreViewExample example = new CadreViewExample();
                example.createCriteria().andStatusEqualTo(status);
                example.setOrderByClause("sort_order desc");
                wb = cadreExportService.export(status, example, 0);

                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CADRE:

                wb = statCadreService.toXlsx();
                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC:

                wb = cpcAllocationService.cpcInfo_Xlsx();
                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT:

                wb = cpcAllocationService.cpcStat_Xlsx();
                break;
        }

        String savePath = FILE_SEPARATOR + "stat_history" + FILE_SEPARATOR
                + type + FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMM")
                + UUID.randomUUID().toString() + ".xlsx";

        String absoluetPath = springProps.uploadPath + savePath;
        FileUtils.mkdirs(absoluetPath);
        FileOutputStream output = new FileOutputStream(absoluetPath);
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
        record.setType(type);
        cadreStatHistoryMapper.insertSelective(record);
    }
}
