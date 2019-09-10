package job.cg;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.cg.CgMemberService;

public class NeedAdjustMember implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CgMemberService cgMemberService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("更新委员会和领导小组人员组成");
        try {
            cgMemberService.updateNeedAdjust();

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
