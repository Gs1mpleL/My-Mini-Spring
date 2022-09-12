package com.wanfeng.myminispring.beans.factory.config;

/**
 * 单例Bean注册表
 */
public interface SingletonBeanRegistry {
    /**
     * 获取单例Bean
     */
    Object getSingleton(String beanName);
    void addSingleton(String beanName, Object singletonObject);
}
