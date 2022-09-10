package com.wanfeng.myminiSpring.beans.factory;

import com.wanfeng.myminiSpring.beans.BeansException;

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
