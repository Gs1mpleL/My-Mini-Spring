package com.wanfeng.myminispring.beans.factory.config;

import com.wanfeng.myminispring.beans.factory.HierarchicalBeanFactory;
import com.wanfeng.myminispring.core.conver.ConversionService;
import com.wanfeng.myminispring.util.StringValueResolver;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory,SingletonBeanRegistry {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例bean
     */
    void destroySingletons();
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    String resolveEmbeddedValue(String value);

    void setConversionService(ConversionService conversionService);
    ConversionService getConversionService();
}
