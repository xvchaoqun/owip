package job.pm;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.pm.Pm3GuideService;

public class Pm3notice implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Pm3GuideService pm3GuideService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("发送组织生活模块报送和审批提醒");
        pm3GuideService.timingNotice();
    }
}
