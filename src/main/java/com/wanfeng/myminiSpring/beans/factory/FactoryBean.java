package com.wanfeng.myminiSpring.beans.factory;

public interface FactoryBean<T> {
    T getObject() throws Exception;

    boolean isSingleton();
}
