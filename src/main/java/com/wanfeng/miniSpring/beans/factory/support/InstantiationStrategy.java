package com.wanfeng.miniSpring.beans.factory.support;


import com.wanfeng.miniSpring.beans.BeansException;
import com.wanfeng.miniSpring.beans.factory.config.BeanDefinition;

/**
 * Bean实例化策略
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
