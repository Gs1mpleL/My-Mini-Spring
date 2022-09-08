package com.wanfeng.myminispring.BeanProcessor;

import com.wanfeng.beans.BeansException;
import com.wanfeng.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Bean实例化执行构造方法前，执行方法.....");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Bean实例化执行构造方法后，执行方法");
        return bean;
    }
}
