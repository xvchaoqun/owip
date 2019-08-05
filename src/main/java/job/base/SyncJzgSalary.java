package job.base;

import ext.service.SyncService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SyncJzgSalary implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SyncService syncService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("同步本月在职工资信息...");
        try {
            syncService.syncJzgSalary(true);
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
