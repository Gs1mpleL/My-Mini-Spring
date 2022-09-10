package com.wanfeng.myminiSpring.context.event;

import com.wanfeng.myminiSpring.beans.BeansException;
import com.wanfeng.myminiSpring.beans.factory.BeanFactory;
import com.wanfeng.myminiSpring.beans.factory.BeanFactoryAware;
import com.wanfeng.myminiSpring.context.ApplicationEvent;
import com.wanfeng.myminiSpring.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new HashSet<>();
    private BeanFactory beanFactory;
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
