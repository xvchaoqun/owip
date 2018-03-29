package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.source.SyncService;

/**
 * Created by lm on 2017/9/17.
 */
@Component
public class SyncBKS implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SyncService sysUserSyncService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("同步本科生库...");
        try {
            sysUserSyncService.syncAllBks(true);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
