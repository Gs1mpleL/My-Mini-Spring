package com.wanfeng.beans.factory.config;

import com.wanfeng.beans.BeansException;
import com.wanfeng.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
