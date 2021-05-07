package job.party;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.party.BranchMemberGroupService;

public class BranchTranMsgJob implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchMemberGroupService branchMemberGroupService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            int count = branchMemberGroupService.tranRemind();
            if(count>0){
                logger.info("换届提醒支委委员人数：{}", count);
            }

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
