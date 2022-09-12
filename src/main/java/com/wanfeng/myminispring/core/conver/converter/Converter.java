package com.wanfeng.myminispring.core.conver.converter;

/**
 * 定义转换器的规范
 */
public interface Converter<S, T> {


    /**
     * 类型转换
     */
    T convert(S source);
}