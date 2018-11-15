package service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import sys.utils.PropertiesUtils;

@Component
public class LoadBeanProcessor implements BeanFactoryPostProcessor {

    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.defaultListableBeanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;

        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.rootBeanDefinition(PropertiesUtils.getString("login.service"));
        //用于设置指定的类中需要引入的其他bean
        //beanDefinitionBuilder.addPropertyValue("otherBeanName","otherBeanName");
        this.defaultListableBeanFactory.registerBeanDefinition("loginService",
                beanDefinitionBuilder.getBeanDefinition());
    }
}