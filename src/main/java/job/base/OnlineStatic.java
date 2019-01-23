package job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.sys.SysOnlineStaticService;

/**
 * Created by lm on 2017/9/17.
 */
public class OnlineStatic implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysOnlineStaticService sysOnlineStaticService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            logger.debug("在线用户数量统计...");
            sysOnlineStaticService.stat();

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
