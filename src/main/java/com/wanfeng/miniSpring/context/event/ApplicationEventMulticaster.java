package com.wanfeng.miniSpring.context.event;
import com.wanfeng.miniSpring.context.ApplicationEvent;
import com.wanfeng.miniSpring.context.ApplicationListener;

public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
