package com.wanfeng.test;

import com.wanfeng.beans.factory.config.BeanDefinition;
import com.wanfeng.beans.factory.support.DefaultListableBeanFactory;
import com.wanfeng.service.HelloService;

public class Demo {
    public static void main(String[] args) {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        factory.registerBeanDefinition("helloService",beanDefinition);
        HelloService helloService = (HelloService) factory.getBean("helloService");
        helloService.sayHello();
    }
}
