package com.wanfeng.myminispring.core.conver;

/**
 * 转换服务接口，定义规范，是否可以转化，转化
 */
public interface ConversionService {
    /**
     * 是否可以转换
     */
    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    /**
     * 转换
     */
    <T> T convert(Object source, Class<T> targetType);
}