package com.wanfeng.beans.factory.support;

import com.wanfeng.beans.BeansException;
import com.wanfeng.beans.factory.BeanFactory;
import com.wanfeng.beans.factory.config.BeanDefinition;

/**
 * 抽象Bean工厂，可以创建Bean，注册Bean定义
 * 模版模式，创建Bean和BeanDefinition的方法交给子类来实现
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
