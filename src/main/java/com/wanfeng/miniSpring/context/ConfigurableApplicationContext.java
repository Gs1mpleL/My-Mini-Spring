package com.wanfeng.miniSpring.context;

import com.wanfeng.miniSpring.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{
    void refresh() throws BeansException;
}
