## SecurityContextHolderFilter

SecurityContextHolderFilter 负责在请求处理过程中管理安全上下文。安全上下文是指存储了当前用户的认证信息（如身份、权限等）的对象，在整个请求处理过程中需要被使用。
具体来说，SecurityContextHolderFilter 主要完成以下几个任务：

从请求中获取安全上下文：当请求到达后端应用程序时，SecurityContextHolderFilter 会尝试从请求中提取安全相关的信息，例如认证凭证、权限信息等。
将安全上下文与当前线程绑定：获取到安全上下文后，SecurityContextHolderFilter 会将其绑定到当前线程中。Spring Security 使用 ThreadLocal 来实现线程本地变量存储，确保在同一线程内安全上下文的传递。
允许在请求处理过程中访问安全上下文：一旦安全上下文与当前线程绑定成功，整个请求处理过程中的代码均可通过 SecurityContextHolder 来获取当前用户的安全信息，而无需显式地传递安全上下文。

总之，`SecurityContextHolderFilter` 在 Spring Security 中扮演着确保安全上下文正确传播和管理的重要角色，它是整个安全框架中的关键组成部分。通过合理地配置和使用 `SecurityContextHolderFilter`，可以确保安全上下文在请求处理过程中得到正确管理和传递，从而实现应用程序的安全防护。

SecurityContextHolderFilter是由SecurityContextConfigurer进行配置,所以我们首先看一下SecurityContextConfigurer源码

### SecurityContextConfigurer

```java

public final class SecurityContextConfigurer<H extends HttpSecurityBuilder<H>>
		extends AbstractHttpConfigurer<SecurityContextConfigurer<H>, H> {
	//这个默认为true,当为true时使用的是SecurityContextHolderFilter过滤器，为false时使用的是SecurityContextPersistenceFilter过滤器，由于SecurityContextPersistenceFilter已经过期不再使用，所以我们只看SecurityContextHolderFilter这个过滤器就可以了
	private boolean requireExplicitSave = true;

	public SecurityContextConfigurer() {
	}

	//设置SecurityContextRepository用于SecurityContext的保存及获取
	public SecurityContextConfigurer<H> securityContextRepository(SecurityContextRepository securityContextRepository) {
		getBuilder().setSharedObject(SecurityContextRepository.class, securityContextRepository);
		return this;
	}

	public SecurityContextConfigurer<H> requireExplicitSave(boolean requireExplicitSave) {
		this.requireExplicitSave = requireExplicitSave;
		return this;
	}

	boolean isRequireExplicitSave() {
		return this.requireExplicitSave;
	}

	SecurityContextRepository getSecurityContextRepository() {
        //首先从容器中获取SecurityContextRepository
		SecurityContextRepository securityContextRepository = getBuilder()
			.getSharedObject(SecurityContextRepository.class);
        //如果容器中不存在SecurityContextRepository实例，则创建一个包含RequestAttributeSecurityContextRepository和HttpSessionSecurityContextRepository的委托类实例DelegatingSecurityContextRepository
		if (securityContextRepository == null) {
			securityContextRepository = new DelegatingSecurityContextRepository(
					new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
		}
		return securityContextRepository;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void configure(H http) {
        //获取SecurityContextRepository实例
		SecurityContextRepository securityContextRepository = getSecurityContextRepository();
		if (this.requireExplicitSave) {
            //创建SecurityContextHolderFilter过滤器实例并将该实例注入到容器中
			SecurityContextHolderFilter securityContextHolderFilter = postProcess(
					new SecurityContextHolderFilter(securityContextRepository));
            //设置保存安全上下文SecurityContext的策略实例
			securityContextHolderFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
            //将SecurityContextHolderFilter添加到HttpSecurity中
			http.addFilter(securityContextHolderFilter);
		}
		else {
			SecurityContextPersistenceFilter securityContextFilter = new SecurityContextPersistenceFilter(
					securityContextRepository);
			securityContextFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
			SessionManagementConfigurer<?> sessionManagement = http.getConfigurer(SessionManagementConfigurer.class);
			SessionCreationPolicy sessionCreationPolicy = (sessionManagement != null)
					? sessionManagement.getSessionCreationPolicy() : null;
			if (SessionCreationPolicy.ALWAYS == sessionCreationPolicy) {
				securityContextFilter.setForceEagerSessionCreation(true);
				http.addFilter(postProcess(new ForceEagerSessionCreationFilter()));
			}
			securityContextFilter = postProcess(securityContextFilter);
			http.addFilter(securityContextFilter);
		}
	}

}

```

### SecurityContextHolderFilter

```java
public class SecurityContextHolderFilter extends GenericFilterBean {
	//用于设置到请求Request中，用于标识该请求是否经过该过滤器
	private static final String FILTER_APPLIED = SecurityContextHolderFilter.class.getName() + ".APPLIED";

	private final SecurityContextRepository securityContextRepository;

	private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
		.getContextHolderStrategy();

	public SecurityContextHolderFilter(SecurityContextRepository securityContextRepository) {
		Assert.notNull(securityContextRepository, "securityContextRepository cannot be null");
		this.securityContextRepository = securityContextRepository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        //过滤
		doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
        //判断该请求是否经过该过滤器，默认第一次没有经过
		if (request.getAttribute(FILTER_APPLIED) != null) {
			chain.doFilter(request, response);
			return;
		}
        //设置FILTER_APPLIED到请求request中，用来标识该请求已经通过该过滤器
		request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
        //获取Supplier<SecurityContext>，其中SecurityContext会用户认证的信息
		Supplier<SecurityContext> deferredContext = this.securityContextRepository.loadDeferredContext(request);
		try {
            //保存Supplier<SecurityContext>
			this.securityContextHolderStrategy.setDeferredContext(deferredContext);
			chain.doFilter(request, response);
		}
		finally {
            //清除安全上下文
			this.securityContextHolderStrategy.clearContext();
            //从请求request中删除FILTER_APPLIED
			request.removeAttribute(FILTER_APPLIED);
		}
	}
	public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}

}

```

参考：[SpringSecurity6 | 核心过滤器](https://juejin.cn/post/7298699367790313508)