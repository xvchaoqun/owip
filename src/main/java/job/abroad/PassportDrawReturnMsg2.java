package job.abroad;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.abroad.AbroadShortMsgService;

public class PassportDrawReturnMsg2 implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AbroadShortMsgService abroadShortMsgService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.debug("领取证件之后催交证件短信通知...");
        try {
            abroadShortMsgService.sendReturnMsg2();
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
