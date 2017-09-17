package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.abroad.PassportService;

/**
 * Created by lm on 2017/9/17.
 */
@Component
public class PassportExpire implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PassportService passportService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("证件过期扫描...");
        try {
            passportService.expire();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
