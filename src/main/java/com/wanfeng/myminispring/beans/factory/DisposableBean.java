package com.wanfeng.myminispring.beans.factory;

public interface DisposableBean {
    void destroy() throws Exception;
}
