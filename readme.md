# Ioc容器
- 使用BeanDefinitionRegistry注册Bean的信息（如Bean属于哪个class）
- 使用SingletonBeanRegistry注册Bean实例
- AbstractBeanFactory利用模版模式，定义了获取Bean的流程
  - getBean()直接获取Bean
  - 不存在bean，getBeanDefinition（）获取Bean的定义
  - createBean（）根据Bean的定义创建出实例并返回

# 为Bean注入属性
- 属性List在BeanDefinition中，通过BeanDefinition创建Bean时，为Bean设置属性再返回Bean

# 为Bean注入Bean
- 注入属性时，value设置为BeanReference对象，对象包括Bean的name,遍历属性注入时，如果是BeanReference类，就通过BeanName获取对应的Bean进行注入

# BeanFactoryPostProcessor和BeanPostProcessor
- 第一个是在BeanDefinition注册完成后执行
- 第二个有两个方法，分别在Bean构造前后执行

# ApplicationContext
- postProcessor的注册自动完成，通过遍历IOC容器寻找，并提前进行实例化

# 到现在 Bean的生命周期是
XML -> BeanDefinition -> BeanDefinition被BeanFactoryPostProcessor
-> BeanPostProcessor前置处理 -> Bean的初始化 -> BeanPostProcessor后置处理
-> 使用


# 学习心得
总体逻辑不复杂
但是Spring作为一个基础框架，使用大量继承，相当于地基比较大，可以承载更多东西，这部分设计比较复杂