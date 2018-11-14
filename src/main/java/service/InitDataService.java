package service;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import service.sys.SchedulerJobService;
import sys.utils.PropertiesUtils;

/**
 * Created by fafa on 2016/6/17.
 */
@Component
public class InitDataService extends BaseController implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    protected SchedulerJobService schedulerJobService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ApplicationContext applicationContext = event.getApplicationContext();
        {
            // 动态注入bean
            BeanDefinition bean = new GenericBeanDefinition();
            bean.setBeanClassName(PropertiesUtils.getString("login.service"));
            DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            fty.registerBeanDefinition("loginService", bean);

            /*LoginService loginService = (LoginService) applicationContext.getBean("loginService");
            loginService.tryLogin("", "");*/
        }

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
