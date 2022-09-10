package com.wanfeng.myminiSpring.beans.factory;

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
