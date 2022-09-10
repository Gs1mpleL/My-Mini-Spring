package com.wanfeng.miniSpring.context;

import com.wanfeng.miniSpring.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{
    void refresh() throws BeansException;

    /**
     * 向虚拟机中注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     */
    void registerShutdownHook();
}
