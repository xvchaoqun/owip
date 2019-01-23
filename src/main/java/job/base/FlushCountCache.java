package job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.global.CacheService;

public class FlushCountCache implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheService cacheService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            // 刷新菜单数量统计
            logger.debug("刷新缓存数量...");
            cacheService.refreshCacheCounts();

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
