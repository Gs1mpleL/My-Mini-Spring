package com.wanfeng.myminiSpring.beans.factory;

import com.wanfeng.myminiSpring.beans.BeansException;

public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
