package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.cadre.CadreStatHistoryService;
import sys.constants.SystemConstants;

/**
 * Created by lm on 2017/9/17.
 */
@Component
public class CadreBackup implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CadreStatHistoryService cadreStatHistoryService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("备份干部历史数据文件...");
        try {
            for (Byte type : SystemConstants.CADRE_STAT_HISTORY_TYPE_MAP.keySet()) {

                cadreStatHistoryService.saveExport(type);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
