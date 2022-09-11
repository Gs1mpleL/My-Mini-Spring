package com.wanfeng.myminispring.beans.factory;

import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.beans.PropertyValue;
import com.wanfeng.myminispring.beans.PropertyValues;
import com.wanfeng.myminispring.beans.factory.config.BeanDefinition;
import com.wanfeng.myminispring.beans.factory.config.BeanFactoryPostProcessor;
import com.wanfeng.myminispring.core.io.DefaultResourceLoader;
import com.wanfeng.myminispring.core.io.Resource;
import com.wanfeng.myminispring.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * 对于Bean的成员属性的value为#{???} 在BeanDefinition注册后，根据？？？，从配置文件中获取对应值
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {
    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String location;
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //加载属性配置文件
        Properties properties = loadProperties();

        //属性值替换占位符 就是修改BeanDefinition
        processProperties(beanFactory, properties);

        // 在BeanFactory中添加一个占位符解析器
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    private Properties loadProperties() {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition, properties);
        }
    }
    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof String) {
                //TODO 仅简单支持一个占位符的格式
                String strVal = (String) value;
                StringBuffer buf = new StringBuffer(strVal);
                int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
                int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    String propKey = strVal.substring(startIndex + 2, endIndex);
                    String propVal = properties.getProperty(propKey);
                    buf.replace(startIndex, endIndex + 1, propVal);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buf.toString()));
                }
            }
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        public String resolveStringValue(String strVal) throws BeansException {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }
    }

    private String resolvePlaceholder(String value, Properties properties) {
        //TODO 仅简单支持一个占位符的格式
        String strVal = value;
        StringBuffer buf = new StringBuffer(strVal);
        int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
        int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = strVal.substring(startIndex + 2, endIndex);
            String propVal = properties.getProperty(propKey);
            buf.replace(startIndex, endIndex + 1, propVal);
        }
        return buf.toString();
    }
}
