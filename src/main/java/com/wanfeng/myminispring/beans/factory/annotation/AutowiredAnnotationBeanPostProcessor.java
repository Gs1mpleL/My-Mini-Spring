package com.wanfeng.myminispring.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.beans.PropertyValues;
import com.wanfeng.myminispring.beans.factory.BeanFactory;
import com.wanfeng.myminispring.beans.factory.BeanFactoryAware;
import com.wanfeng.myminispring.beans.factory.ConfigurableListableBeanFactory;
import com.wanfeng.myminispring.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Field;

/**
 * 处理@Autowired和@Value注解的BeanPostProcessor
 *
 * @author derekyi
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
		//处理@Value注解
		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Value valueAnnotation = field.getAnnotation(Value.class);
			if (valueAnnotation != null) {
				String value = valueAnnotation.value();
				value = beanFactory.resolveEmbeddedValue(value);
				BeanUtil.setFieldValue(bean, field.getName(), value);
			}
		}

		//处理@Autowired注解
		for (Field field : fields) {
			Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
			if (autowiredAnnotation != null) {
				Class<?> fieldType = field.getType();
				String dependentBeanName = null;
				Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
				Object dependentBean = null;
				if (qualifierAnnotation != null) {
					dependentBeanName = qualifierAnnotation.value();
					dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
				} else {
					// 如果没有Qualifier，就会去BeanDefinition的注册Map中遍历，如果存在这个类型
					// 且只有一个就直接返回他，但是如果有多个，就报错
					dependentBean = beanFactory.getBean(fieldType);
				}
				BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
			}
		}
		return pvs;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return null;
	}
}
