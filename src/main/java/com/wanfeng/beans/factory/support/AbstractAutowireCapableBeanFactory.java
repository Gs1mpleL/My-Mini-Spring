package com.wanfeng.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.wanfeng.beans.BeansException;
import com.wanfeng.beans.factory.PropertyValue;
import com.wanfeng.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    /**
     * 实例化策略 使用默认
     */
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }




    /**
     * 实例化Bean并装入IOC
     */
    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);
            applyPropertyValues(beanName,bean,beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                //通过反射设置Bean的参数
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception ex) {
            throw new BeansException("Error setting property values for bean: " + beanName, ex);
        }
    }


    /**
     * 创建实例化对象
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    /**
     * 获取实例化策略
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
