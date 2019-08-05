package job.base;

import ext.service.SyncService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lm on 2017/9/17.
 */
public class SyncBKS implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SyncService syncService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("同步本科生库...");
        try {
            syncService.syncAllBks(true);
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
