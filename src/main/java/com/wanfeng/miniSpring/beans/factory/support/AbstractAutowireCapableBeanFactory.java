package com.wanfeng.miniSpring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.wanfeng.miniSpring.beans.BeansException;
import com.wanfeng.miniSpring.beans.PropertyValue;
import com.wanfeng.miniSpring.beans.factory.BeanFactoryAware;
import com.wanfeng.miniSpring.beans.factory.DisposableBean;
import com.wanfeng.miniSpring.beans.factory.InitializingBean;
import com.wanfeng.miniSpring.beans.factory.config.AutowireCapableBeanFactory;
import com.wanfeng.miniSpring.beans.factory.config.BeanDefinition;
import com.wanfeng.miniSpring.beans.factory.config.BeanPostProcessor;
import com.wanfeng.miniSpring.beans.factory.config.BeanReference;

import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {
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
            // 创建Bean实例
            bean = createBeanInstance(beanDefinition);
            // 为Bean的成员赋值，即属性注入
            applyPropertyValues(beanName,bean,beanDefinition);
            // 执行Bean的构造方法，BeanPostProcessor的前置和后置处理方法
            initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        //注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        addSingleton(beanName, bean);
        return bean;
    }
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                // 如果value是BeanReference,通过beanName去Ioc拿实例
                if (value instanceof BeanReference){
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
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


    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        // 实现BeanFactoryAware接口的Bean，
        if (bean instanceof BeanFactoryAware) {
            // 实现接口方法时，调用传入的参数就是对应的factory
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        //执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Throwable ex) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", ex);
        }

        //执行BeanPostProcessor的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    /**
     *   初始化前执行
     */
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 如果实例化的Bean是有初始化方法，就执行初始化方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
        System.out.println("执行bean[" + beanName + "]的初始化方法");
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException {

        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }
}
