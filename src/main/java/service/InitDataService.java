package service;

import controller.BaseController;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by fafa on 2016/6/17.
 */
@Component
public class InitDataService extends BaseController implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(event.getApplicationContext().getParent() == null) {//root application context 没有parent

            //locationService.toJSON();
            sysResourceService.getSortedSysResources();
            //getMetaMap();

            // 刷新数据文件
            cacheService.flushLocation();
            cacheService.flushMetadata();
        }
    }
}
