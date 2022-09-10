package com.wanfeng.myminiSpring.beans.factory;

import com.wanfeng.myminiSpring.beans.BeansException;
import com.wanfeng.myminiSpring.beans.factory.config.AutowireCapableBeanFactory;
import com.wanfeng.myminiSpring.beans.factory.config.BeanDefinition;
import com.wanfeng.myminiSpring.beans.factory.config.BeanPostProcessor;
import com.wanfeng.myminiSpring.beans.factory.config.ConfigurableBeanFactory;

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

