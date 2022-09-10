package com.wanfeng.myminispring.beans.factory.support;


import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.beans.factory.config.BeanDefinition;

/**
 * Bean实例化策略
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
