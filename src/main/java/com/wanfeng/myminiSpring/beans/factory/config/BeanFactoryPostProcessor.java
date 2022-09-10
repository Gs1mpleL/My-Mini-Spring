package com.wanfeng.myminiSpring.beans.factory.config;

import com.wanfeng.myminiSpring.beans.BeansException;
import com.wanfeng.myminiSpring.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
