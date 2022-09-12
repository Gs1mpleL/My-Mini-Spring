package com.wanfeng.myminispring.beans.factory.config;

import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.beans.PropertyValues;

/**
 * AOP的PostProcessor
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    /**
     * Bean实例化前执行
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * bean实例化之后，设置属性之前执行
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName)
            throws BeansException;

    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
