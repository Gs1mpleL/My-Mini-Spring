package com.wanfeng.myminispring.beans.factory.support;

import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.beans.factory.DisposableBean;
import com.wanfeng.myminispring.beans.factory.ObjectFactory;
import com.wanfeng.myminispring.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 单例Bean注册表的默认实现
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {	//一级缓存
        private Map<String, Object> singletonObjects = new HashMap<>();

        //二级缓存
        private Map<String, Object> earlySingletonObjects = new HashMap<>();

        //三级缓存
        private Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

        private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

        @Override
        public Object getSingleton(String beanName) {
            Object singletonObject = singletonObjects.get(beanName);
            if (singletonObject == null) {
                singletonObject = earlySingletonObjects.get(beanName);
                if (singletonObject == null) {
                    ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                    if (singletonFactory != null) {
                        singletonObject = singletonFactory.getObject();
                        //从三级缓存放进二级缓存
                        earlySingletonObjects.put(beanName, singletonObject);
                        singletonFactories.remove(beanName);
                    }
                }
            }
            return singletonObject;
        }

        @Override
        public void addSingleton(String beanName, Object singletonObject) {
            singletonObjects.put(beanName, singletonObject);
            earlySingletonObjects.remove(beanName);
            singletonFactories.remove(beanName);
        }

        protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
            singletonFactories.put(beanName, singletonFactory);
        }

        public void registerDisposableBean(String beanName, DisposableBean bean) {
            disposableBeans.put(beanName, bean);
        }

        public void destroySingletons() {
            Set<String> beanNames = disposableBeans.keySet();
            for (String beanName : beanNames) {
                DisposableBean disposableBean = disposableBeans.remove(beanName);
                try {
                    disposableBean.destroy();
                } catch (Exception e) {
                    throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
                }
            }
        }
}

