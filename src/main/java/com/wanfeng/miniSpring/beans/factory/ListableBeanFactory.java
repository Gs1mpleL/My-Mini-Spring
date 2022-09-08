package com.wanfeng.miniSpring.beans.factory;

import com.wanfeng.miniSpring.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory{
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回定义的所有bean的名称
     *
     * @return
     */
    String[] getBeanDefinitionNames();
}
