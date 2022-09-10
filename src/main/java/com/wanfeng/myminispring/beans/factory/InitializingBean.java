package com.wanfeng.myminispring.beans.factory;

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
