package com.wanfeng.myminispring.core.conver.converter;

/**
 * 类型转换器工厂 获取一个转换器，目标类型可以是工厂提供的目标类型子类
 */
public interface ConverterFactory<S, R> {

    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}