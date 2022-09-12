package com.wanfeng.myminispring.core.conver.converter;

/**
 * 转换器注册器，定义注册器的规范
 */
public interface ConverterRegistry {

    void addConverter(Converter<?, ?> converter);

    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

    void addConverter(GenericConverter converter);
}
