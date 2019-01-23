package job.crs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.crs.CrsShortMsgService;

/**
 * Created by lm on 2018/4/24.
 */
public class ApplyFinishMsg implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CrsShortMsgService crsShortMsgService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("竞争上岗报名截止通知领导...");
        try {
            crsShortMsgService.sendApplyFinishMsg();
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}