package com.wanfeng.myminispring.context.support;

import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.beans.factory.ConfigurableListableBeanFactory;
import com.wanfeng.myminispring.beans.factory.config.BeanFactoryPostProcessor;
import com.wanfeng.myminispring.beans.factory.config.BeanPostProcessor;
import com.wanfeng.myminispring.context.ConfigurableApplicationContext;
import com.wanfeng.myminispring.core.conver.ConversionService;
import com.wanfeng.myminispring.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String CONVERSION_SERVICE_BEAN_NAME = "conversionService";
    @Override
    public void refresh() throws BeansException {
        //创建BeanFactory(直接new一个实现好的BeanFactory)，并加载BeanDefinition，目前实现的是从XML中加载BeanDefinition
        refreshBeanFactory();
        // 获得new出来的那个BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //添加ApplicationContextAwareProcessor，让继承自ApplicationContextAware的bean能感知bean
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 执行BeanFactoryPostProcessor，如果是BeanFactory，需要手动执行，使用ApplicationContext在这里就自动进行了
        invokeBeanFactoryPostProcessors(beanFactory);
        //BeanPostProcessor需要提前与其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);
        //遍历BeanDefinition，实例化所有单例bean
        finishBeanFactoryInitialization(beanFactory);
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        //设置类型转换器
        if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME)) {
            Object conversionService = beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME);
            if (conversionService instanceof ConversionService) {
                beanFactory.setConversionService((ConversionService) conversionService);
            }
        }

        //提前实例化单例bean
        beanFactory.preInstantiateSingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 遍历容器找到BeanFactoryPostProcessor相关的类，自动执行
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 获取BeanDefinition已经注册的所有的BeanPostProcessors，把他们添加到BeanFactory的BeanPostProcessor缓存中
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }


    public void registerShutdownHook() {
        Thread shutdownHook = new Thread() {
            public void run() {
                doClose();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    protected void doClose() {
        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }
}
