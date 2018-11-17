package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import service.sys.SchedulerJobService;
import service.sys.SysResourceService;

/**
 * Created by fafa on 2016/6/17.
 */
@Component
public class InitDataService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    protected SchedulerJobService schedulerJobService;
    @Autowired
    protected SysResourceService sysResourceService;
    @Autowired
    protected SpringProps springProps;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ApplicationContext applicationContext = event.getApplicationContext();

        if(applicationContext.getParent() == null) {//root application context 没有parent

            //locationService.toJSON();
            sysResourceService.getSortedSysResources(false);
            sysResourceService.getSortedSysResources(true);
            //getMetaMap();

            if(!springProps.devMode) {
                // 刷新数据文件
                //cacheService.flushLocation();
                //cacheService.flushMetadata();

                // 刷新菜单数量缓存
                //cacheService.refreshCacheCounts();

                // 启动所有已开启的任务
                schedulerJobService.runAllJobs();
            }
        }
    }
}
