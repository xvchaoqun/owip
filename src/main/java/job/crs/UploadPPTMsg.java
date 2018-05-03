package job.crs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.crs.CrsShortMsgService;

/**
 * Created by lm on 2018/5/3.
 */
public class UploadPPTMsg implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CrsShortMsgService crsShortMsgService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("竞争上岗通知上传ppt...");
        try {
            crsShortMsgService.sendUploadPptMsg();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
