package com.wanfeng.myminispring.beans.factory;

import com.wanfeng.myminispring.beans.BeansException;

public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
