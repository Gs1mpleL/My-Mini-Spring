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

# Bean的初始化和销毁
- 目前实现两种方法，一种是指定Bean的初始化和销毁方法名称，另一个种是实现两个接口
- 在BeanDefinition中添加String表示的初始化方法名称和销毁名称
- 在Bean实例话时，判断BeanDefinition中初始化方法字符串是否存在，存在就通过反射调用对应的方法
- 销毁方法注册在一个Map中，消费时直接拿出来进行执行

# Aware接口
解决了疑惑：为什么实现这个接口在方法中参数拿出来就是需要的ApplicationContext
- BeanFactoryAware：Bean初始化时会检查是否实现这个接口，如果实现，直接调用接口方法，参数就是this
这样就获得了这个BeanFactory
- ApplicationContextAware：手动实现一个BeanPostProcessor(在Bean构造前检查上条)，手动注册，在实例话时按照BeanPostProcessor自动执行

# prototypeBean
在BeanDefinition添加属性表示是否为prototypeBean
实例化时如果不是单例Bean，就不注入Ioc容器，这样下次就会重新创建新的实例

# FactoryBean
实现该接口，实现getObject()方法，来返回实例对象，当注入该工厂时，获取对象时直接返回getObject的结果

# ApplicationContext的Event机制
未完成，待后续补充

# AOP
封装比较深，使用AspectJ切面表达式进行匹配，通过配置进行选择是JDK代理还是CGLIB代理