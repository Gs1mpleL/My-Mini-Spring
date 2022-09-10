package com.wanfeng.myminispring.aop;

import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;
@Data
public class AdvisedSupport {
    private boolean proxyTargetClass = false;
    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;
    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }
}
