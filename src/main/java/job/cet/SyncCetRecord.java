package job.cet;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.cet.CetRecordService;

// 同步归档所有培训记录
public class SyncCetRecord implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CetRecordService cetRecordService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("归档培训学时...");
        try {
            cetRecordService.syncAllUpperTrain();
            cetRecordService.syncAllProjectObj();
            cetRecordService.syncAllUnitTrian();
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
