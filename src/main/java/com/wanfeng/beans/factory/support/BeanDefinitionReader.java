package com.wanfeng.beans.factory.support;

import com.wanfeng.beans.BeansException;
import com.wanfeng.core.io.Resource;
import com.wanfeng.core.io.ResourceLoader;

/**
 * 读取bean定义信息即BeanDefinition的接口
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;
}
