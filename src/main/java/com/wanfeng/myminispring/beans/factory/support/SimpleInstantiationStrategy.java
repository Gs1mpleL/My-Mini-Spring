package com.wanfeng.myminispring.beans.factory.support;



import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.beans.factory.config.BeanDefinition;


import java.lang.reflect.Constructor;

public class SimpleInstantiationStrategy implements InstantiationStrategy{

    /**
     * 默认实例化策略，直接空构造
     */
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            Constructor constructor = beanClass.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
