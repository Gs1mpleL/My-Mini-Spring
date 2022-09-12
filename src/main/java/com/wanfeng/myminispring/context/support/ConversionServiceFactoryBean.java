package com.wanfeng.myminispring.context.support;

import com.wanfeng.myminispring.beans.factory.FactoryBean;
import com.wanfeng.myminispring.beans.factory.InitializingBean;
import com.wanfeng.myminispring.core.conver.ConversionService;
import com.wanfeng.myminispring.core.conver.converter.Converter;
import com.wanfeng.myminispring.core.conver.converter.ConverterFactory;
import com.wanfeng.myminispring.core.conver.converter.ConverterRegistry;
import com.wanfeng.myminispring.core.conver.converter.GenericConverter;
import com.wanfeng.myminispring.core.conver.support.DefaultConversionService;
import com.wanfeng.myminispring.core.conver.support.GenericConversionService;

import java.util.Set;

/**
 * 类型转换的FactoryBean
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {
    private Set<?> converters;

    private GenericConversionService conversionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    @Override
    public ConversionService getObject() throws Exception {
        return conversionService;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
