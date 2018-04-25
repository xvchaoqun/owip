package job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.source.SyncService;

/**
 * Created by lm on 2017/9/17.
 */
public class SyncTeacherAbroadInfo implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SyncService syncService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("同步教职工党员出国境信息...");
        try {
            syncService.syncAllAbroad(true);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
