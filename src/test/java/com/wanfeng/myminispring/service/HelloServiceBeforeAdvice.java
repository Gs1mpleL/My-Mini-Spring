package com.wanfeng.myminispring.service;

import com.wanfeng.myminispring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class HelloServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("BeforeAdvice: do something before the earth explodes");
    }
}
