package com.wanfeng.myminispring.aop;

public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
