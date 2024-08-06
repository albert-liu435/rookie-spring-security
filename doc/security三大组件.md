## security三大组件

Spring security 能够进行认证与权限校验，离不开Spring security中的三大全局组件。三大全局组件为 InitializeUserDetailsBeanManagerConfigurer、InitializeAuthenticationProviderBeanManagerConfigurer和EnableGlobalAuthenticationAutowiredConfigurer

Spring security 能够进行认证与权限校验，离不开Spring security中的三大全局组件。三大全局组件为 InitializeUserDetailsBeanManagerConfigurer、InitializeAuthenticationProviderBeanManagerConfigurer和EnableGlobalAuthenticationAutowiredConfigurer



### InitializeUserDetailsBeanManagerConfigurer

1、InitializeUserDetailsBeanManagerConfigurer的初始化过程在AuthenticationConfiguration中，首先创建InitializeUserDetailsBeanManagerConfigurer

```java
@Bean
	public static InitializeUserDetailsBeanManagerConfigurer initializeUserDetailsBeanManagerConfigurer(
			ApplicationContext context) {
		return new InitializeUserDetailsBeanManagerConfigurer(context);
	}
```

并将其注入到	private List<GlobalAuthenticationConfigurerAdapter> globalAuthConfigurers = Collections.emptyList()属性中;

InitializeUserDetailsBeanManagerConfigurer源码可以看到如果spring容器中存在UserDetailsService和PasswordEncoder实例，就从容器中获取并添加到AuthenticationManagerBuilder中等待后续进行构建

```java
@Order(InitializeUserDetailsBeanManagerConfigurer.DEFAULT_ORDER)
class InitializeUserDetailsBeanManagerConfigurer extends GlobalAuthenticationConfigurerAdapter {

	static final int DEFAULT_ORDER = Ordered.LOWEST_PRECEDENCE - 5000;
	
	private final ApplicationContext context;
	
	/**
	 * @param context
	 */
	InitializeUserDetailsBeanManagerConfigurer(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.apply(new InitializeUserDetailsManagerConfigurer());
	}
	
	class InitializeUserDetailsManagerConfigurer extends GlobalAuthenticationConfigurerAdapter {
	
		private final Log logger = LogFactory.getLog(getClass());
	
		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
		    //从spring容器中获取UserDetailsService
			List<BeanWithName<UserDetailsService>> userDetailsServices = getBeansWithName(UserDetailsService.class);
			if (auth.isConfigured()) {
				if (!userDetailsServices.isEmpty()) {
					this.logger.warn("Global AuthenticationManager configured with an AuthenticationProvider bean. "
							+ "UserDetailsService beans will not be used for username/password login. "
							+ "Consider removing the AuthenticationProvider bean. "
							+ "Alternatively, consider using the UserDetailsService in a manually instantiated "
							+ "DaoAuthenticationProvider.");
				}
				return;
			}
	        //userDetailsServices为空的话直接返回
			if (userDetailsServices.isEmpty()) {
				return;
			}
			//userDetailsServices如果大于0 则也直接返回
			else if (userDetailsServices.size() > 1) {
				List<String> beanNames = userDetailsServices.stream().map(BeanWithName::getName).toList();
				this.logger.warn(LogMessage.format("Found %s UserDetailsService beans, with names %s. "
						+ "Global Authentication Manager will not use a UserDetailsService for username/password login. "
						+ "Consider publishing a single UserDetailsService bean.", userDetailsServices.size(),
						beanNames));
				return;
			}
			//获取userDetailsServices
			var userDetailsService = userDetailsServices.get(0).getBean();
			var userDetailsServiceBeanName = userDetailsServices.get(0).getName();
			//获取PasswordEncoder
			PasswordEncoder passwordEncoder = getBeanOrNull(PasswordEncoder.class);
			UserDetailsPasswordService passwordManager = getBeanOrNull(UserDetailsPasswordService.class);
			CompromisedPasswordChecker passwordChecker = getBeanOrNull(CompromisedPasswordChecker.class);
			DaoAuthenticationProvider provider;
			if (passwordEncoder != null) {
				provider = new DaoAuthenticationProvider(passwordEncoder);
			}
			else {
				provider = new DaoAuthenticationProvider();
			}
			provider.setUserDetailsService(userDetailsService);
			if (passwordManager != null) {
				provider.setUserDetailsPasswordService(passwordManager);
			}
			if (passwordChecker != null) {
				provider.setCompromisedPasswordChecker(passwordChecker);
			}
			provider.afterPropertiesSet();
			auth.authenticationProvider(provider);
			this.logger.info(LogMessage.format(
					"Global AuthenticationManager configured with UserDetailsService bean with name %s",
					userDetailsServiceBeanName));
		}
	
		/**
		 * @return a bean of the requested class if there's just a single registered
		 * component, null otherwise.
		 */
		private <T> T getBeanOrNull(Class<T> type) {
			String[] beanNames = InitializeUserDetailsBeanManagerConfigurer.this.context.getBeanNamesForType(type);
			if (beanNames.length != 1) {
				return null;
			}
			return InitializeUserDetailsBeanManagerConfigurer.this.context.getBean(beanNames[0], type);
		}
	
		/**
		 * @return a list of beans of the requested class, along with their names. If
		 * there are no registered beans of that type, the list is empty.
		 */
		private <T> List<BeanWithName<T>> getBeansWithName(Class<T> type) {
			List<BeanWithName<T>> beanWithNames = new ArrayList<>();
			String[] beanNames = InitializeUserDetailsBeanManagerConfigurer.this.context.getBeanNamesForType(type);
			for (String beanName : beanNames) {
				T bean = InitializeUserDetailsBeanManagerConfigurer.this.context.getBean(beanNames[0], type);
				beanWithNames.add(new BeanWithName<T>(bean, beanName));
			}
			return beanWithNames;
		}
	
		static class BeanWithName<T> {
	
			private final T bean;
	
			private final String name;
	
			BeanWithName(T bean, String name) {
				this.bean = bean;
				this.name = name;
			}
	
			T getBean() {
				return this.bean;
			}
	
			String getName() {
				return this.name;
			}
	
		}
	
	}

}


```

### InitializeAuthenticationProviderBeanManagerConfigurer

InitializeAuthenticationProviderBeanManagerConfigurer用于设置AuthenticationProvider到AuthenticationManagerBuilder，AuthenticationManagerBuilder用于进行身份验证

```java

@Order(InitializeAuthenticationProviderBeanManagerConfigurer.DEFAULT_ORDER)
class InitializeAuthenticationProviderBeanManagerConfigurer extends GlobalAuthenticationConfigurerAdapter {

	static final int DEFAULT_ORDER = InitializeUserDetailsBeanManagerConfigurer.DEFAULT_ORDER - 100;

	private final ApplicationContext context;

	/**
	 * @param context the ApplicationContext to look up beans.
	 */
	InitializeAuthenticationProviderBeanManagerConfigurer(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.apply(new InitializeAuthenticationProviderManagerConfigurer());
	}

	class InitializeAuthenticationProviderManagerConfigurer extends GlobalAuthenticationConfigurerAdapter {

		private final Log logger = LogFactory.getLog(getClass());

		@Override
		public void configure(AuthenticationManagerBuilder auth) {
			if (auth.isConfigured()) {
				return;
			}
			List<BeanWithName<AuthenticationProvider>> authenticationProviders = getBeansWithName(
					AuthenticationProvider.class);
			if (authenticationProviders.isEmpty()) {
				return;
			}
			else if (authenticationProviders.size() > 1) {
				List<String> beanNames = authenticationProviders.stream().map(BeanWithName::getName).toList();
				this.logger.info(LogMessage.format("Found %s AuthenticationProvider beans, with names %s. "
						+ "Global Authentication Manager will not be configured with AuthenticationProviders. "
						+ "Consider publishing a single AuthenticationProvider bean, or wiring your Providers directly "
						+ "using the DSL.", authenticationProviders.size(), beanNames));
				return;
			}
			var authenticationProvider = authenticationProviders.get(0).getBean();
			var authenticationProviderBeanName = authenticationProviders.get(0).getName();

			auth.authenticationProvider(authenticationProvider);
			this.logger.info(LogMessage.format(
					"Global AuthenticationManager configured with AuthenticationProvider bean with name %s",
					authenticationProviderBeanName));
		}

		/**
		 * @return a bean of the requested class if there's just a single registered
		 * component, null otherwise.
		 */
		private <T> T getBeanOrNull(Class<T> type) {
			String[] beanNames = InitializeAuthenticationProviderBeanManagerConfigurer.this.context
				.getBeanNamesForType(type);
			if (beanNames.length != 1) {
				return null;
			}
			return InitializeAuthenticationProviderBeanManagerConfigurer.this.context.getBean(beanNames[0], type);
		}

		/**
		 * @return a list of beans of the requested class, along with their names. If
		 * there are no registered beans of that type, the list is empty.
		 */
		private <T> List<BeanWithName<T>> getBeansWithName(Class<T> type) {
			List<BeanWithName<T>> beanWithNames = new ArrayList<>();
			String[] beanNames = InitializeAuthenticationProviderBeanManagerConfigurer.this.context
				.getBeanNamesForType(type);
			for (String beanName : beanNames) {
				T bean = InitializeAuthenticationProviderBeanManagerConfigurer.this.context.getBean(beanNames[0], type);
				beanWithNames.add(new BeanWithName<T>(bean, beanName));
			}
			return beanWithNames;
		}

		static class BeanWithName<T> {

			private final T bean;

			private final String name;

			BeanWithName(T bean, String name) {
				this.bean = bean;
				this.name = name;
			}

			T getBean() {
				return this.bean;
			}

			String getName() {
				return this.name;
			}

		}

	}

}

```

### EnableGlobalAuthenticationAutowiredConfigurer

EnableGlobalAuthenticationAutowiredConfigurer只是获取EnableGlobalAuthentication注解的bean,然后打印出来

```java
	private static class EnableGlobalAuthenticationAutowiredConfigurer extends GlobalAuthenticationConfigurerAdapter {

		private final ApplicationContext context;

		private static final Log logger = LogFactory.getLog(EnableGlobalAuthenticationAutowiredConfigurer.class);

		EnableGlobalAuthenticationAutowiredConfigurer(ApplicationContext context) {
			this.context = context;
		}

		@Override
		public void init(AuthenticationManagerBuilder auth) {
			Map<String, Object> beansWithAnnotation = this.context
				.getBeansWithAnnotation(EnableGlobalAuthentication.class);
			if (logger.isTraceEnabled()) {
				logger.trace(LogMessage.format("Eagerly initializing %s", beansWithAnnotation));
			}
		}

	}
```









