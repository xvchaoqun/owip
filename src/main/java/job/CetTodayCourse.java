package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.cet.CetShortMsgService;

@Component
public class CetTodayCourse implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CetShortMsgService cetShortMsgService;

    // 发送当天的课
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("每日开课通知...");
        try {
            cetShortMsgService.todayCourse(null);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
