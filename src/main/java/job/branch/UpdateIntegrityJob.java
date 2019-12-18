package job.branch;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.party.BranchService;

public class UpdateIntegrityJob implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchService branchService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("校验党支部信息完整度。。。");
        try {
            branchService.updateIntegrity();

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
