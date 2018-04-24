package job.abroad;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.abroad.AbroadShortMsgService;

/**
 * Created by lm on 2017/9/17.
 */
public class ApplySelfSendApprovalMsgToAdmin implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AbroadShortMsgService abroadShortMsgService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("因私审批每天定时通知干部管理员...");
        try {
            abroadShortMsgService.sendAbroadApprovalMsgToAdmin();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
