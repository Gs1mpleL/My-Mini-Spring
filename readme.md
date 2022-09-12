# Ioc容器
- 使用BeanDefinitionRegistry注册Bean的信息（如Bean属于哪个class）
- 使用SingletonBeanRegistry注册Bean实例
- AbstractBeanFactory利用模版模式，定义了获取Bean的流程
  - getBean()直接获取Bean
  - 不存在bean，getBeanDefinition（）获取Bean的定义
  - createBean（）根据Bean的定义创建出实例并返回

## 为Bean注入属性
- 属性List在BeanDefinition中，通过BeanDefinition创建Bean时，为Bean设置属性再返回Bean

## 为Bean注入Bean
- 注入属性时，value设置为BeanReference对象，对象包括Bean的name,遍历属性注入时，如果是BeanReference类，就通过BeanName获取对应的Bean进行注入

## BeanFactoryPostProcessor和BeanPostProcessor
- 第一个是在BeanDefinition注册完成后执行
- 第二个有两个方法，分别在Bean构造前后执行

## ApplicationContext
- postProcessor的注册自动完成，通过遍历IOC容器寻找，并提前进行实例化

## 到现在 Bean的生命周期是
XML -> BeanDefinition -> BeanDefinition被BeanFactoryPostProcessor
-> BeanPostProcessor前置处理 -> Bean的初始化 -> BeanPostProcessor后置处理
-> 使用


## 学习心得
总体逻辑不复杂
但是Spring作为一个基础框架，使用大量继承，相当于地基比较大，可以承载更多东西，这部分设计比较复杂

## Bean的初始化和销毁
- 目前实现两种方法，一种是指定Bean的初始化和销毁方法名称，另一个种是实现两个接口
- 在BeanDefinition中添加String表示的初始化方法名称和销毁名称
- 在Bean实例话时，判断BeanDefinition中初始化方法字符串是否存在，存在就通过反射调用对应的方法
- 销毁方法注册在一个Map中，消费时直接拿出来进行执行

## Aware接口
解决了疑惑：为什么实现这个接口在方法中参数拿出来就是需要的ApplicationContext
- BeanFactoryAware：Bean初始化时会检查是否实现这个接口，如果实现，直接调用接口方法，参数就是this
这样就获得了这个BeanFactory
- ApplicationContextAware：手动实现一个BeanPostProcessor(在Bean构造前检查上条)，手动注册，在实例话时按照BeanPostProcessor自动执行

## prototypeBean
在BeanDefinition添加属性表示是否为prototypeBean
实例化时如果不是单例Bean，就不注入Ioc容器，这样下次就会重新创建新的实例

## FactoryBean
实现该接口，实现getObject()方法，来返回实例对象，当注入该工厂时，获取对象时直接返回getObject的结果

## ApplicationContext的Event机制
未完成，待后续补充

## 到目前为止，Bean的生命周期
XML
BeanDefinition
BeanFactory修改BeanDefinition
InstantiationAwarePostProcessor，生成代理对象，执行BeanPostProcessor的after后直接返回，不再继续创建
Bean的实例化
对FactoryBean的处理
BeanPostProcessor前置处理（如果实现Aware接口，执行对应方法）
执行Bean的初始化方法（执行自定义的Init()方法）
BeanPostProcessor后置处理
Bean的使用

## 目前对ApplicationContext的理解
主要是进行refresh()方法，来执行整个流程
- 创建一个BeanFactory，根据XML获取注册BeanDefinition到BeanFactory中
- 在BeanFactory中添加一个关于ApplicationContextAware的BeanPostProcessor
- 寻找并自动执行所有的BeanFactoryPostProcessor
- 寻找并添加所有BeanPostProcessor到BeanFactory
- 遍历BeanDefinition，实例化所有单例Bean
- 从这里开始就进入了BeanFactory的createBean()流程，来实例化Bean


# AOP
封装比较深，使用AspectJ切面表达式进行匹配，通过配置进行选择是JDK代理还是CGLIB代理
生成AOP对象的地方是在：
  进入BeanFactory的createBean()流程时，先判断该Bean是否需要被代理
如果需要进入代理流程，直接返回代理对象结束Bean实例化的流程（当然还有执行一下BeanPostProcessor的after()方法）

# 基础篇总结
Spring采用的整体架构为  接口->接口->抽象类->抽象类->抽象类->最终实现

接口继承：逐步完善功能需要的接口
抽象类：最上层抽象类定义整个流程，定义抽象方法，留给子抽象类完成 （模版模式）

好处：整体上形成一个分层次的结构，每一层只负责一部分事情，
新拓展功能时，根据功能选择一个合适的层次进行继承


# 拓展篇

## ${xxx}注入配置文件内容
主要是在BeanDefinition注册完成后执行一个BeanFactoryPostProcessor
逐个读取每个BeanDefinition的value，发现${xxx},就从配置文件中获取值，并替换

## 设置包扫描
解析XML时发现
<context:component-scan base-package="com.wanfeng.myminispring"/>
就会根据base-package去对应包下寻找@Component修饰的类，将其作为BeanDefinition注入的BeanFactory中

## @Value和@Autowire实现
在Bean实例化后，在为一个Bean注入属性前，遍历它的Filed，如果发现有@Value或@Autowire注解
就通过注册到BeanFactory的解析器解析Value，或根据类型寻找对应的要注入的Bean
通过反射直接注入Bean的字段中，再继续执行属性注入

## 类型转换器
FactoryBean应用，在Bean实例注入属性，@Value解析时生效

## 循环依赖的原因与解决
原因：BeanA实例化后，注入属性时，发现需要先实例化BeanB，实例化B后，注入属性时，又发现需要实例化BeanA
但是目前BeanA只有在注入属性后才会放入Bean缓存中，所有目前BeanB是拿不到BeanA的，就会出现死循环，直到栈溢出

解决：
普通Bean：
  新增一个二级缓存earlySingletonObjects，当Bean实例化之后，不需要等属性注入，就先放入earlySingletonObjects，如果其他模块需要这个Bean，先回去二级缓存中寻找
  增加二级缓存，不能解决代理Bean循环依赖，在Bean实例化后，放入二级缓存，但是如果是代理Bean，下来又会生成代理Bean，所以二级缓存中是一个Bean，实际生成的代理对象在一级缓存中，不是同一个对象

代理Bean：
  Bean实例化后，普通Bean放入三级缓存，需要代理的Bean，生成代理对象后放入三级缓存
  这样对Bean执行初始化后，获取Bean返回时，先从一级缓存中寻找，再去二级缓存，最后去三级缓存寻找
  三级缓存寻找到，放入二级缓存，证明这个Bean已经是实例化好的代理Bean