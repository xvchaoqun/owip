package job.party;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.party.PartyService;

public class UpdateIntegrityJob implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PartyService partyService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("更新党员信息完整度");
        try {
            partyService.updateIntegrity();

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
