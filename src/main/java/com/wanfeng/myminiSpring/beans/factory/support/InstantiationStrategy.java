package com.wanfeng.myminiSpring.beans.factory.support;


import com.wanfeng.myminiSpring.beans.BeansException;
import com.wanfeng.myminiSpring.beans.factory.config.BeanDefinition;

/**
 * Bean实例化策略
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
