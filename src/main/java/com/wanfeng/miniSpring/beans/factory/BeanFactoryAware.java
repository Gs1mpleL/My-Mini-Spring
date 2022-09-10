package com.wanfeng.miniSpring.beans.factory;

import com.wanfeng.miniSpring.beans.BeansException;

public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
