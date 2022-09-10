package com.wanfeng.myminispring.aop;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 被代理的真实对象
 */
@Data
@AllArgsConstructor
public class TargetSource {
    private final Object target;
    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }

}
