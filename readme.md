# Ioc容器
- 使用BeanDefinitionRegistry注册Bean的信息（如Bean属于哪个class）
- 使用SingletonBeanRegistry注册Bean实例
- AbstractBeanFactory利用模版模式，定义了获取Bean的流程
  - getBean()直接获取Bean
  - 不存在bean，getBeanDefinition（）获取Bean的定义
  - createBean（）根据Bean的定义创建出实例并返回

# 为Bean注入属性
- 属性List在BeanDefinition中，通过BeanDefinition创建Bean时，为Bean设置属性再返回Bean