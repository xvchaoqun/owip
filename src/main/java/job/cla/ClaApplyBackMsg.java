package job.cla;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.cla.ClaShortMsgService;

public class ClaApplyBackMsg implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClaShortMsgService claShortMsgService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("[干部请假审批]销假提醒...");
        try {

            claShortMsgService.applyBackMsg();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
