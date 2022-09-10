package com.wanfeng.myminispring.beans.factory.config;

import com.wanfeng.myminispring.beans.BeansException;

/**
 * AOP的PostProcessor
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
