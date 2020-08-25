package job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.common.IPropertyMapper;
import service.global.CacheService;

import java.lang.reflect.Method;

public class FlushMetadata implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected CacheService cacheService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("刷新元数据缓存...");
        try {
            cacheService.flushMetadata();

            for (Method method : IPropertyMapper.class.getDeclaredMethods()) {

                cacheService.refreshPropertyCaches(method.getName());
            }

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
