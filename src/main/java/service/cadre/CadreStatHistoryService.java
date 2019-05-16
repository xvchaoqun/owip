package service.cadre;

import domain.cadre.CadreStatHistory;
import domain.cadre.CadreViewExample;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.SpringProps;
import service.analysis.StatCadreService;
import service.unit.UnitPostAllocationService;
import sys.constants.CadreConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class CadreStatHistoryService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UnitPostAllocationService unitPostAllocationService;
    @Autowired
    private StatCadreService statCadreService;
    @Autowired
    private CadreExportService cadreExportService;
    @Autowired
    protected SpringProps springProps;

    // 保存历史信息表
    public void saveExport(byte type) throws IOException {

        long start = System.currentTimeMillis();

        boolean hasKjCadre = CmTag.getBoolProperty("hasKjCadre");

        Workbook wb = null;
        switch (type) {
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE:

                byte status = CadreConstants.CADRE_STATUS_MIDDLE;
                CadreViewExample example = new CadreViewExample();
                example.createCriteria().andStatusEqualTo(status);
                example.setOrderByClause("sort_order desc");
                wb = cadreExportService.export(status, example, 0, null);

                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CADRE_CJ:

                wb = statCadreService.toXlsx(CadreConstants.CADRE_TYPE_CJ);
                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CADRE_KJ:
                if(hasKjCadre) {
                    wb = statCadreService.toXlsx(CadreConstants.CADRE_TYPE_KJ);
                }
                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC:

                wb = unitPostAllocationService.cpcStat_Xlsx(CadreConstants.CADRE_TYPE_CJ);
                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC_KJ:
                if(hasKjCadre) {
                    wb = unitPostAllocationService.cpcStat_Xlsx(CadreConstants.CADRE_TYPE_KJ);
                }
                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT:

                wb = unitPostAllocationService.cpcInfo_Xlsx(CadreConstants.CADRE_TYPE_CJ);
                break;
            case CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT_KJ:
                if(hasKjCadre) {
                    wb = unitPostAllocationService.cpcInfo_Xlsx(CadreConstants.CADRE_TYPE_KJ);
                }
                break;
        }

        if(wb==null){

            logger.info("无相关信息，无法备份[{}]。", CadreConstants.CADRE_STAT_HISTORY_TYPE_MAP.get(type));
            return;
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
