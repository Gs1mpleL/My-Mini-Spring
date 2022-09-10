package com.wanfeng.miniSpring.beans.factory;

public interface FactoryBean<T> {
    T getObject() throws Exception;

    boolean isSingleton();
}
