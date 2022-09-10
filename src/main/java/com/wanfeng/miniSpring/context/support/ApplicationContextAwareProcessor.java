package com.wanfeng.miniSpring.context.support;

import com.wanfeng.miniSpring.beans.BeansException;
import com.wanfeng.miniSpring.beans.factory.config.BeanPostProcessor;
import com.wanfeng.miniSpring.context.ApplicationContext;
import com.wanfeng.miniSpring.context.ApplicationContextAware;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
