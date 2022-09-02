package com.wanfeng.beans.factory;


import com.wanfeng.beans.BeansException;
/**
 * 定义获取Bean的方法
 */
public interface BeanFactory {
    /**
     * 获取Bean
     * @throws BeansException Bean不存在
     */
    Object getBean(String name) throws BeansException;
}
