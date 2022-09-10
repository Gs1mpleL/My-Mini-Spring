package com.wanfeng.myminispring.aop;

public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}
