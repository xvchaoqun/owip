package job.cet;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.cet.CetShortMsgService;

public class CetTrainBegin implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CetShortMsgService cetShortMsgService;

    // 通知1： 培训班开班前一天通知
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("培训班开班前一天通知...");
        try {
            cetShortMsgService.projectOpenMsg(null);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
