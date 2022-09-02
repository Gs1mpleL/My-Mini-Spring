package com.wanfeng.beans.factory.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Bean的定义，用于存储Bean的相关信息
 */
@Data
@AllArgsConstructor
public class BeanDefinition {
    private Class beanClass;
}
