package com.wanfeng.myminispring.test;

import cn.hutool.core.io.IoUtil;
import com.wanfeng.myminispring.aop.AdvisedSupport;
import com.wanfeng.myminispring.aop.ClassFilter;
import com.wanfeng.myminispring.aop.MethodMatcher;
import com.wanfeng.myminispring.aop.TargetSource;
import com.wanfeng.myminispring.aop.aspectJ.AspectJExpressionPointcut;
import com.wanfeng.myminispring.aop.aspectJ.AspectJExpressionPointcutAdvisor;
import com.wanfeng.myminispring.aop.framework.CglibAopProxy;
import com.wanfeng.myminispring.aop.framework.JdkDynamicAopProxy;
import com.wanfeng.myminispring.aop.framework.ProxyFactory;
import com.wanfeng.myminispring.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.wanfeng.myminispring.beans.PropertyValue;
import com.wanfeng.myminispring.beans.PropertyValues;
import com.wanfeng.myminispring.beans.factory.config.BeanDefinition;
import com.wanfeng.myminispring.beans.factory.config.BeanReference;
import com.wanfeng.myminispring.beans.factory.support.DefaultListableBeanFactory;
import com.wanfeng.myminispring.beans.factory.xml.XmlBeanDefinitionReader;
import com.wanfeng.myminispring.context.support.ClassPathXmlApplicationContext;
import com.wanfeng.myminispring.core.io.DefaultResourceLoader;
import com.wanfeng.myminispring.core.io.Resource;
import com.wanfeng.myminispring.Bean.Car;
import com.wanfeng.myminispring.Bean.Person;
import com.wanfeng.myminispring.BeanProcessor.MyBeanFactoryPostProcessor;
import com.wanfeng.myminispring.BeanProcessor.MyBeanPostProcessor;
import com.wanfeng.myminispring.service.HelloService;
import com.wanfeng.myminispring.service.HelloServiceBeforeAdvice;
import com.wanfeng.myminispring.service.HelloServiceInterceptor;
import com.wanfeng.myminispring.service.IService;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


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

    @Test
    public void aspectJTest() throws NoSuchMethodException {
        // 创建一个切面表达式处理器
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.wanfeng.myminispring.service.HelloService.*(..))");
        Class<HelloService> clazz = HelloService.class;
        Method method = clazz.getDeclaredMethod("sayHello");
        // 判断切面表达式是否成立
        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    @Test
    public void jdkProxyTest(){
        IService service = new HelloService();
        AdvisedSupport advisedSupport = new AdvisedSupport();
        // 代理对象
        TargetSource targetSource = new TargetSource(service);
        // 方法拦截器
        HelloServiceInterceptor methodInterceptor = new HelloServiceInterceptor();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.wanfeng.myminispring.service.HelloService.*(..))");
        // 方法匹配器
        MethodMatcher methodMatcher = pointcut.getMethodMatcher();

        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);

        IService proxy = (IService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.sayHello();
    }

    @Test
    public void testCglibDynamicProxy() throws Exception {
        HelloService service = new HelloService();
        AdvisedSupport advisedSupport = new AdvisedSupport();
        // 代理对象
        TargetSource targetSource = new TargetSource(service);
        // 方法拦截器
        HelloServiceInterceptor methodInterceptor = new HelloServiceInterceptor();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.wanfeng.myminispring.service.HelloService.*(..))");
        // 方法匹配器
        MethodMatcher methodMatcher = pointcut.getMethodMatcher();

        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);
        HelloService proxy = (HelloService) new CglibAopProxy(advisedSupport).getProxy();
        proxy.sayHello();
    }
    @Test
    public void testBeforeAdvice(){
        HelloServiceBeforeAdvice helloServiceBeforeAdvice = new HelloServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor methodBeforeAdviceInterceptor = new MethodBeforeAdviceInterceptor(helloServiceBeforeAdvice);
        HelloService service = new HelloService();
        AdvisedSupport advisedSupport = new AdvisedSupport();
        // 代理对象
        TargetSource targetSource = new TargetSource(service);

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.wanfeng.myminispring.service.HelloService.*(..))");
        // 方法匹配器
        MethodMatcher methodMatcher = pointcut.getMethodMatcher();

        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodBeforeAdviceInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);
        ProxyFactory proxyFactory = new ProxyFactory(advisedSupport);
        ((IService) proxyFactory.getProxy()).sayHello();
    }

    @Test
    public void advisorTest(){
        HelloService helloService = new HelloService();
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new HelloServiceBeforeAdvice());
        // 设置表达式
        advisor.setExpression("execution(* com.wanfeng.myminispring.service.HelloService.*(..))");
        // 设置处理器
        advisor.setAdvice(methodInterceptor);
        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(helloService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(helloService);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
//			advisedSupport.setProxyTargetClass(true);   //JDK or CGLIB

            IService proxy = (IService) new ProxyFactory(advisedSupport).getProxy();
            proxy.sayHello();
        }
    }

    @Test
    public void testAutoProxy(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        ((IService) applicationContext.getBean("helloService")).sayHello();
    }

    @Test
    public void propTest(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Car car = (Car) applicationContext.getBean("car");
        System.out.println(car);
    }

    @Test
    public void ComponentTest(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        ((HelloService) applicationContext.getBean("service")).sayHello();
    }
}

