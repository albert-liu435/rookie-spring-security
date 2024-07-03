## Spring Security最小化配置启动流程

Spring Security最小化配置相关的类主要如下

```java
org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration
org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
org.springframework.security.config.annotation.web.configuration.SpringWebMvcImportSelector
org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
org.springframework.security.config.annotation.web.configuration.HttpSecurityConfiguration
org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
```

#### 入口SpringBootWebSecurityConfiguration

spring security boot最小配置初始化的入口为SpringBootWebSecurityConfiguration类开始的。执行注解@Import导入的SpringWebMvcImportSelector类，当环境下面存在DispatcherServlet，即为spring MVC环境的情况下，会导入org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration到Spring容器中。并实例化里面的springSecurityHandlerMappingIntrospectorBeanDefinitionRegistryPostProcessor。然后执行org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration#securityFilterChainRegistration()对DelegatingFilterProxyRegistrationBean进行实例化，这个bean的作用就是用来给我们的web应用中注册一个类型是DelegatingFilterProxy的过滤器，(这和在springboot应用中注册普通过滤器的方式是一样的，注册普通过滤器时给容器中注册的是FilterRegistrationBean )这个DelegatingFilterProxy 从名字看就知道是一个代理过滤器，它会把请求代理给内部的目标过滤器，这个目标过滤器的名称是通过DelegatingFilterProxyRegistrationBean的构造方法传递过去的，也就是名称为springSecurityFilterChain。

```java
	@Bean
	static BeanDefinitionRegistryPostProcessor springSecurityHandlerMappingIntrospectorBeanDefinitionRegistryPostProcessor() {
		return new BeanDefinitionRegistryPostProcessor() {
			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
			}

			@Override
			public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                //容器中是否包含名称为mvcHandlerMappingIntrospector的Bean定义信息，如果 存在就直接返回
				if (!registry.containsBeanDefinition(HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME)) {
					return;
				}

				String hmiRequestTransformerBeanName = HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME + "RequestTransformer";
                 //容器中是否包含名称为mvcHandlerMappingIntrospectorRequestTransformer的Bean定义信息
				if (!registry.containsBeanDefinition(hmiRequestTransformerBeanName)) {
                    //HandlerMappingIntrospectorRequestTransformer的Bean定义信息，Spring后续会根据该信息实例化Bean
					BeanDefinition hmiRequestTransformer = BeanDefinitionBuilder
						.rootBeanDefinition(HandlerMappingIntrospectorRequestTransformer.class)
						.addConstructorArgReference(HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME)
						.getBeanDefinition();
					registry.registerBeanDefinition(hmiRequestTransformerBeanName, hmiRequestTransformer);
				}
				//获取springSecurityFilterChain的Bean定义，此时已经存在，可以参考org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration#springSecurityFilterChain()方法定义了该Bean的信息
				BeanDefinition filterChainProxy = registry
					.getBeanDefinition(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
				//判断是否为该Bean实例类型
				if (!filterChainProxy.getResolvableType().isInstance(CompositeFilterChainProxy.class)) {
                    //定义HandlerMappingIntrospectorCacheFilterFactoryBean  BeanDefinition
					BeanDefinitionBuilder hmiCacheFilterBldr = BeanDefinitionBuilder
						.rootBeanDefinition(HandlerMappingIntrospectorCacheFilterFactoryBean.class)
						.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
					
					ManagedList<BeanMetadataElement> filters = new ManagedList<>();
					filters.add(hmiCacheFilterBldr.getBeanDefinition());
					filters.add(filterChainProxy);
                    //定义CompositeFilterChainProxy的BeanDefinition,含有HandlerMappingIntrospectorCacheFilterFactoryBean和filterChainProxy的BeanDefinition,后续实例化的时候会用到
					BeanDefinitionBuilder compositeSpringSecurityFilterChainBldr = BeanDefinitionBuilder
						.rootBeanDefinition(CompositeFilterChainProxy.class)
						.addConstructorArgValue(filters);

					registry.removeBeanDefinition(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
					registry.registerBeanDefinition(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME,
							compositeSpringSecurityFilterChainBldr.getBeanDefinition());
				}
			}
		};
	}

```

#### 启动tomcat容器

由于springboot默认使用tomcat作为容器，所以这里以tomcat为例，在创建org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext#createWebServer()，最终会调用org.springframework.boot.web.servlet.ServletContextInitializerBeans#addServletContextInitializerBean()方法将DelegatingFilterProxyRegistrationBean加入到initializers中，后续通过org.springframework.boot.web.servlet.AbstractFilterRegistrationBean#addRegistration()方法方法将DelegatingFilterProxy加入到ApplicationContextFacade,可以看到加入的Filter即(DelegatingFilterProxyRegistrationBean)都已经包含了Spring上下文对象applicationContext,所以后续进行Bean实例化的时候，在这些Filter中也是可以通过applicationContext直接获取的



