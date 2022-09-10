package com.wanfeng.myminiSpring.context;

import com.wanfeng.myminiSpring.beans.BeansException;

public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
