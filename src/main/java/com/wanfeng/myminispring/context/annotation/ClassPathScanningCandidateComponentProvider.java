package com.wanfeng.myminispring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.wanfeng.myminispring.beans.factory.config.BeanDefinition;
import com.wanfeng.myminispring.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 提供 扫描包内带有@Component注解的类，返回所有class集合
 */
public class ClassPathScanningCandidateComponentProvider {
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        // 扫描有包下的注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }
}
