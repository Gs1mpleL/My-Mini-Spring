package com.wanfeng.miniSpring.beans.factory;

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
