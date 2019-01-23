package job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.ext.SyncService;

/**
 * Created by lm on 2017/9/17.
 */
public class SyncRetireSalary implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SyncService syncService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("同步本月离退休信息...");
        try {
            syncService.syncRetireSalary(true);
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
