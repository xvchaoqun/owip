package job.sc;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scPublic.ScPublicService;

public class ScPublicFinish implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScPublicService scPublicService;

    // 公示时间那天的下午6点， 系统自动将公示转入到“公示结束”中来
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("干部任前公示结束系统自动扫描...");
        try {
            scPublicService.autoFinish();

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
