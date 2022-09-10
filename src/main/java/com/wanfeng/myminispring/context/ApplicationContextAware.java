package com.wanfeng.myminispring.context;

import com.wanfeng.myminispring.beans.BeansException;

public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
