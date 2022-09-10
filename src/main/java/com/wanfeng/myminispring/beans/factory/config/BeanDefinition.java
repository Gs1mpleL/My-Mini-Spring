package com.wanfeng.myminispring.beans.factory.config;

import com.wanfeng.myminispring.beans.PropertyValues;
import lombok.Data;
import lombok.ToString;

/**
 * Bean的定义，用于存储Bean的相关信息
 */
@Data
@ToString
public class BeanDefinition {
    public static String SCOPE_SINGLETON = "singleton";

    public static String SCOPE_PROTOTYPE = "prototype";
    private String scope = SCOPE_SINGLETON;

    private boolean singleton = true;

    private boolean prototype = false;
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isPrototype() {
        return this.prototype;
    }
    /**
     * Bean的Class类型
     */
    private Class beanClass;

    /**
     * Bean的参数
     */
    private PropertyValues propertyValues;

    /**
     * Bean的初始化方法
     */
    private String initMethodName;

    /**
     * Bean的销毁方法
     */
    private String destroyMethodName;


    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }
    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }
}
