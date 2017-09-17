package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.abroad.PassportDrawService;

/**
 * Created by lm on 2017/9/17.
 */
@Component
public class PassportDrawReturnMsg implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PassportDrawService passportDrawService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.debug("领取证件之后催交证件短信通知...");
        try {
            passportDrawService.sendReturnMsg();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
