package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.global.CacheService;
import service.sys.SysOnlineStaticService;

/**
 * Created by lm on 2017/9/17.
 */
@Component
public class OnlineStatic implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheService cacheService;
    @Autowired
    private SysOnlineStaticService sysOnlineStaticService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            // 刷新菜单数量统计（临时放这里2017-4-15）
            logger.debug("刷新缓存数量...");
            cacheService.refreshCacheCounts();

            logger.debug("在线用户数量统计...");
            sysOnlineStaticService.stat();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
