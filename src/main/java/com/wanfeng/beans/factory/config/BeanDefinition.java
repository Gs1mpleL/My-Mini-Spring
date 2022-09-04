package com.wanfeng.beans.factory.config;

import com.wanfeng.beans.factory.PropertyValues;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Bean的定义，用于存储Bean的相关信息
 */
@Data
public class BeanDefinition {
    /**
     * Bean的Class类型
     */
    private Class beanClass;

    /**
     * Bean的参数
     */
    private PropertyValues propertyValues;




    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }
    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }
}
