package com.wanfeng.myminispring.beans.factory;

import com.wanfeng.myminispring.beans.BeansException;

public interface ObjectFactory<T> {

    T getObject() throws BeansException;
}