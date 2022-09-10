package com.wanfeng.miniSpring.context;

import com.wanfeng.miniSpring.beans.BeansException;

public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
