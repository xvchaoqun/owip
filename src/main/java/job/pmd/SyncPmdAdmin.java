package job.pmd;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.pmd.PmdService;

/**
 * Created by lm on 2018/4/2.
 */
public class SyncPmdAdmin implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PmdService pmdService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("定时同步党费收缴管理员...");
        pmdService.syncAdmin();
    }
}
