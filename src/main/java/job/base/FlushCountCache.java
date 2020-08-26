package job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.analysis.StatCadreService;
import service.global.CacheService;
import sys.constants.CadreConstants;
import sys.tags.CmTag;

public class FlushCountCache implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheService cacheService;
    @Autowired
    private StatCadreService statCadreService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            // 刷新菜单数量统计
            logger.debug("刷新缓存数量...");
            cacheService.refreshCacheCounts();

            // 更新干部数据统计缓存
            statCadreService.refreshStatCache(CadreConstants.CADRE_TYPE_CJ);
            if(CmTag.getBoolProperty("hasKjCadre")) {
                statCadreService.refreshStatCache(CadreConstants.CADRE_TYPE_KJ);
            }

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
