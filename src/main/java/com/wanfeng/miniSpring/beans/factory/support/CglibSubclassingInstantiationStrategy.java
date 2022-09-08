package com.wanfeng.miniSpring.beans.factory.support;

import com.wanfeng.miniSpring.beans.BeansException;
import com.wanfeng.miniSpring.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{
    /**
     * 使用CgLib生成动态子类来实例化对象
     */
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, argsTemp, proxy) -> proxy.invokeSuper(obj,argsTemp));
        return enhancer.create();
    }
}
