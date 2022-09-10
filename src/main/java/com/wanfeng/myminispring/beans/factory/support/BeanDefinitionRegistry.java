package com.wanfeng.myminispring.beans.factory.support;

import com.wanfeng.myminispring.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition注册接口
 */
public interface BeanDefinitionRegistry {
    /**
     * 向注册表中注Bean的定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 是否包含
     */
    boolean containsBeanDefinition(String beanName);
}
