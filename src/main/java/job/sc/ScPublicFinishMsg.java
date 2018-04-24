package job.sc;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.sc.scPublic.ScPublicService;

public class ScPublicFinishMsg implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScPublicService scPublicService;

    // 公示结束没有“确认”之前， 每天上午8:30， 下午2:30反复发短信提醒
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("干部任前公示结束确认短信系统自动扫描...");
        try {
            scPublicService.autoFinishMsg();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
