package com.wanfeng.beans.factory.support;


import com.wanfeng.beans.BeansException;
import com.wanfeng.beans.factory.config.BeanDefinition;

/**
 * Bean实例化策略
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
