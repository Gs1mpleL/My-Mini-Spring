package com.wanfeng.myminispring.BeanProcessor;

import com.wanfeng.miniSpring.beans.BeansException;
import com.wanfeng.miniSpring.beans.PropertyValue;
import com.wanfeng.miniSpring.beans.factory.ConfigurableListableBeanFactory;
import com.wanfeng.miniSpring.beans.factory.config.BeanDefinition;
import com.wanfeng.miniSpring.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("执行BeanFactoryPostProcessor，在BeanDefinition注册完成后执行");
        BeanDefinition person = beanFactory.getBeanDefinition("person");
        person.getPropertyValues().addPropertyValue(new PropertyValue("name","被修改"));
    }
}
