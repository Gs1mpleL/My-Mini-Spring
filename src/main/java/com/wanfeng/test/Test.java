package com.wanfeng.test;

import com.wanfeng.beans.factory.PropertyValue;
import com.wanfeng.beans.factory.PropertyValues;
import com.wanfeng.beans.factory.config.BeanDefinition;
import com.wanfeng.beans.factory.support.DefaultListableBeanFactory;
import com.wanfeng.service.HelloService;

public class Test {
    public static void main(String[] args) {
        createBeanWithProp();
    }

    private static void createBeanTest() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        factory.registerBeanDefinition("helloService",beanDefinition);
        HelloService helloService = (HelloService) factory.getBean("helloService");
        helloService.sayHello();
    }

    private static void createBeanWithProp(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(Person.class);
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name","张三"));
        propertyValues.addPropertyValue(new PropertyValue("age",12));
        beanDefinition.setPropertyValues(propertyValues);
        factory.registerBeanDefinition("person",beanDefinition);
        Person person = (Person) factory.getBean("person");
        System.out.println(person);
    }
}

