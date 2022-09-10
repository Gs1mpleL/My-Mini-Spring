package com.wanfeng.myminispring.test;

import cn.hutool.core.io.IoUtil;
import com.wanfeng.myminiSpring.beans.PropertyValue;
import com.wanfeng.myminiSpring.beans.PropertyValues;
import com.wanfeng.myminiSpring.beans.factory.config.BeanDefinition;
import com.wanfeng.myminiSpring.beans.factory.config.BeanReference;
import com.wanfeng.myminiSpring.beans.factory.support.DefaultListableBeanFactory;
import com.wanfeng.myminiSpring.beans.factory.xml.XmlBeanDefinitionReader;
import com.wanfeng.myminiSpring.context.support.ClassPathXmlApplicationContext;
import com.wanfeng.myminiSpring.core.io.DefaultResourceLoader;
import com.wanfeng.myminiSpring.core.io.Resource;
import com.wanfeng.myminispring.Bean.Car;
import com.wanfeng.myminispring.Bean.Person;
import com.wanfeng.myminispring.BeanProcessor.MyBeanFactoryPostProcessor;
import com.wanfeng.myminispring.BeanProcessor.MyBeanPostProcessor;
import com.wanfeng.myminispring.service.HelloService;
import org.junit.Test;

import java.io.IOException;


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

    @Test
    public void BeanFactoryPostProcessor(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");
        // 此时BeanDefinition都加载完成了
        // 在Bean实例化之前可以更改BeanDefinitions的属性
        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        // BeanPostProcessor在实例化时起作用
        beanFactory.addBeanPostProcessor(new MyBeanPostProcessor());
        System.out.println(beanFactory.getBean("person"));
    }

    @Test
    public void applicationContextTest(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
    }


    @Test
    public void initAndDestroyMethod() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();  //或者手动关闭 applicationContext.close();
    }

    @Test
    public void testAwareInterface(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        System.out.println("实际的ac"+applicationContext);
    }

    @Test
    public void testPrototypeBean(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Object car = applicationContext.getBean("car");

        Object car1 = applicationContext.getBean("car");
        System.out.println(car1 == car);
    }

    @Test
    public void factoryBean(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Car carFactory = (Car) applicationContext.getBean("carFactory");
        System.out.println(carFactory);
    }
}

