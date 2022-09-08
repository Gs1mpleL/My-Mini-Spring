package com.wanfeng.myminispring.test;

import cn.hutool.core.io.IoUtil;
import com.wanfeng.beans.factory.PropertyValue;
import com.wanfeng.beans.factory.PropertyValues;
import com.wanfeng.beans.factory.config.BeanDefinition;
import com.wanfeng.beans.factory.config.BeanReference;
import com.wanfeng.beans.factory.support.DefaultListableBeanFactory;
import com.wanfeng.core.io.DefaultResourceLoader;
import com.wanfeng.core.io.Resource;
import com.wanfeng.myminispring.Bean.Car;
import com.wanfeng.myminispring.Bean.Person;
import com.wanfeng.myminispring.service.HelloService;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class MyTest {

    @Test
    public void createBeanTest() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        factory.registerBeanDefinition("helloService",beanDefinition);
        HelloService helloService = (HelloService) factory.getBean("helloService");
        helloService.sayHello();
    }

    @Test
    public void createBeanWithProp(){
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
    @Test
    public void createBeanWithPropAndReference(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        // 注入Car
        BeanDefinition carDefinition = new BeanDefinition(Car.class);
        PropertyValues carP = new PropertyValues();
        carP.addPropertyValue(new PropertyValue("carName","MaSaLa"));
        carDefinition.setPropertyValues(carP);
        factory.registerBeanDefinition("car",carDefinition);
        Car car = (Car) factory.getBean("car");
        System.out.println(car);
        // 注入Person
        BeanDefinition personDefinition = new BeanDefinition(Person.class);
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name","张三"));
        propertyValues.addPropertyValue(new PropertyValue("age",12));
        propertyValues.addPropertyValue(new PropertyValue("car", new BeanReference("car")));
        personDefinition.setPropertyValues(propertyValues);
        factory.registerBeanDefinition("person",personDefinition);
        Person person = (Person) factory.getBean("person");
        System.out.println(person);
    }

    @Test
    public void loadResource() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:hello.txt");
        System.out.println(IoUtil.readUtf8(resource.getInputStream()));


        resource = resourceLoader.getResource("/Users/liuzhuohao/Documents/javaProject/My-Mini-Spring/src/main/resources/hello.txt");
        System.out.println(IoUtil.readUtf8(resource.getInputStream()));

        resource = resourceLoader.getResource("https://www.baidu.com");
        System.out.println(IoUtil.readUtf8(resource.getInputStream()));
    }
}

