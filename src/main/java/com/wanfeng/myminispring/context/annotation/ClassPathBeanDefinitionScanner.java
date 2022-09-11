package com.wanfeng.myminispring.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.wanfeng.myminispring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.wanfeng.myminispring.beans.factory.config.BeanDefinition;
import com.wanfeng.myminispring.beans.factory.support.BeanDefinitionRegistry;
import com.wanfeng.myminispring.stereotype.Component;

import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "com.wanfeng.myminispring.context.annotation.internalAutowiredAnnotationProcessor";

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                // 解析bean的作用域
                String beanScope = resolveBeanScope(candidate);
                if (StrUtil.isNotEmpty(beanScope)) {
                    candidate.setScope(beanScope);
                }
                //生成bean的名称
                String beanName = determineBeanName(candidate);
                //注册BeanDefinition
                registry.registerBeanDefinition(beanName, candidate);
            }
        }
        // 处理完宝扫描后，再注册一个 处理@Autowired和@Value注解的BeanPostProcessor
        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 获取bean的作用域
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }

        return StrUtil.EMPTY;
    }


    /**
     * 生成bean的名称
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
