package job.party;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.member.MemberApplyService;

public class UpdateMemberApplyJob implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberApplyService memberApplyService;

    private static String url = "http://localhost:8080/api/member/memberApply_sync";

    private static String app = "ma";


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("学生党员发展系统推送发展党员数据");
        try {
            memberApplyService.acceptSmisData(url, app);
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
