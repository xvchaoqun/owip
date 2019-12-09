package job.member;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.party.MemberService;

public class updateIntegrityJob implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberService memberService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("更新党员信息完整度");
        try {
            memberService.updateIntegrity();

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
