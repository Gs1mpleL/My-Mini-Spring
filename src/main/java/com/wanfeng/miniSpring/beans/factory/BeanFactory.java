package com.wanfeng.miniSpring.beans.factory;


import com.wanfeng.miniSpring.beans.BeansException;
/**
 * 定义获取Bean的方法
 */
public interface BeanFactory {
    /**
     * 获取Bean
     * @throws BeansException Bean不存在
     */
    Object getBean(String name) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
