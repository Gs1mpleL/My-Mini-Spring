package com.wanfeng.miniSpring.context.support;

import com.wanfeng.miniSpring.beans.BeansException;
import com.wanfeng.miniSpring.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;

    protected final void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;

    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
