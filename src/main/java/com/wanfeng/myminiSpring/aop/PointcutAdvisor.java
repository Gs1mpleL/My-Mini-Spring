package com.wanfeng.myminiSpring.aop;

public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}
