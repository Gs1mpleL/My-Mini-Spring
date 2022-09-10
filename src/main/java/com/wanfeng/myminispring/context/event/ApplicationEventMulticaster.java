package com.wanfeng.myminispring.context.event;
import com.wanfeng.myminispring.context.ApplicationEvent;
import com.wanfeng.myminispring.context.ApplicationListener;

public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
