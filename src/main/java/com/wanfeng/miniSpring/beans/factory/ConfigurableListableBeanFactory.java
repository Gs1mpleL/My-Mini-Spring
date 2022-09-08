package com.wanfeng.miniSpring.beans.factory;

import com.wanfeng.miniSpring.beans.BeansException;
import com.wanfeng.miniSpring.beans.factory.config.AutowireCapableBeanFactory;
import com.wanfeng.miniSpring.beans.factory.config.BeanDefinition;
import com.wanfeng.miniSpring.beans.factory.config.BeanPostProcessor;
import com.wanfeng.miniSpring.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 根据名称查找BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例实例
     */
    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}

