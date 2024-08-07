## BasicAuthenticationFilter

BasicAuthenticationFilter：该过滤器用于处理基本认证，即通过HTTP消息头传递的用户名和密码。在进行基本认证时，前端应用程序会将用户凭据(base64编码的)添加到HTTP请求标头中。BasicAuthenticationFilter在此通过解码这些凭据并将其发送到AuthenticationManager进行身份验证。

该过滤器是由HttpBasicConfigurer进行配置完成的。我们首先看一下HttpBasicConfigurer

### HttpBasicConfigurer

```java
public final class HttpBasicConfigurer<B extends HttpSecurityBuilder<B>>
		extends AbstractHttpConfigurer<HttpBasicConfigurer<B>, B> {
    //匹配基于HTTP请求头的请求的一个类。它允许你根据请求中的某个HTTP头部的值来决定是否允许请求通过
	private static final RequestHeaderRequestMatcher X_REQUESTED_WITH = new RequestHeaderRequestMatcher(
			"X-Requested-With", "XMLHttpRequest");

	private static final String DEFAULT_REALM = "Realm";
	//AuthenticationEntryPoint接口通常用于实现的未授权访问处理逻辑,可以通过自定义进行实现
	private AuthenticationEntryPoint authenticationEntryPoint;
	//用于构建用户额外信息的补充，后续可以根据org.springframework.security.core.Authentication#getDetails()方法进行额外信息的获取
	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
	//HttpBasic的默认未授权访问处理
	private BasicAuthenticationEntryPoint basicAuthEntryPoint = new BasicAuthenticationEntryPoint();
	//策略接口用于保存安全上下文SecurityContext
	private SecurityContextRepository securityContextRepository;


	public HttpBasicConfigurer() {
		realmName(DEFAULT_REALM);
		LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<>();
		entryPoints.put(X_REQUESTED_WITH, new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        //AuthenticationEntryPoint委托类，默认封装了BasicAuthenticationEntryPoint
		DelegatingAuthenticationEntryPoint defaultEntryPoint = new DelegatingAuthenticationEntryPoint(entryPoints);
		defaultEntryPoint.setDefaultEntryPoint(this.basicAuthEntryPoint);
		this.authenticationEntryPoint = defaultEntryPoint;
	}


	
	
	public HttpBasicConfigurer<B> realmName(String realmName) {
		this.basicAuthEntryPoint.setRealmName(realmName);
		this.basicAuthEntryPoint.afterPropertiesSet();
		return this;
	}

	/**
	 * 可以设置自定义的AuthenticationEntryPoint
	 */
	public HttpBasicConfigurer<B> authenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		return this;
	}

	/**
	 * Specifies a custom {@link AuthenticationDetailsSource} to use for basic
	 * authentication. The default is {@link WebAuthenticationDetailsSource}.
	 * @param authenticationDetailsSource the custom {@link AuthenticationDetailsSource}
	 * to use
	 * @return {@link HttpBasicConfigurer} for additional customization
	 */
	public HttpBasicConfigurer<B> authenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
		return this;
	}

	/**
	 * Specifies a custom {@link SecurityContextRepository} to use for basic
	 * authentication. The default is {@link RequestAttributeSecurityContextRepository}.
	 * @param securityContextRepository the custom {@link SecurityContextRepository} to
	 * use
	 * @return {@link HttpBasicConfigurer} for additional customization
	 * @since 6.1
	 */
	public HttpBasicConfigurer<B> securityContextRepository(SecurityContextRepository securityContextRepository) {
		this.securityContextRepository = securityContextRepository;
		return this;
	}

	@Override
	public void init(B http) {
		registerDefaults(http);
	}

    //主要用于配置异常处理ExceptionHandlingConfigurer和登出LogoutConfigurer
	private void registerDefaults(B http) {
        
		ContentNegotiationStrategy contentNegotiationStrategy = http.getSharedObject(ContentNegotiationStrategy.class);
		if (contentNegotiationStrategy == null) {
			contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
		}
		MediaTypeRequestMatcher restMatcher = new MediaTypeRequestMatcher(contentNegotiationStrategy,
				MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_XML, MediaType.MULTIPART_FORM_DATA,
				MediaType.TEXT_XML);
		restMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
		MediaTypeRequestMatcher allMatcher = new MediaTypeRequestMatcher(contentNegotiationStrategy, MediaType.ALL);
		allMatcher.setUseEquals(true);
		RequestMatcher notHtmlMatcher = new NegatedRequestMatcher(
				new MediaTypeRequestMatcher(contentNegotiationStrategy, MediaType.TEXT_HTML));
		RequestMatcher restNotHtmlMatcher = new AndRequestMatcher(
				Arrays.<RequestMatcher>asList(notHtmlMatcher, restMatcher));
		RequestMatcher preferredMatcher = new OrRequestMatcher(
				Arrays.asList(X_REQUESTED_WITH, restNotHtmlMatcher, allMatcher));
		registerDefaultEntryPoint(http, preferredMatcher);
		registerDefaultLogoutSuccessHandler(http, preferredMatcher);
	}

	private void registerDefaultEntryPoint(B http, RequestMatcher preferredMatcher) {
		ExceptionHandlingConfigurer<B> exceptionHandling = http.getConfigurer(ExceptionHandlingConfigurer.class);
		if (exceptionHandling == null) {
			return;
		}
		exceptionHandling.defaultAuthenticationEntryPointFor(postProcess(this.authenticationEntryPoint),
				preferredMatcher);
	}

	private void registerDefaultLogoutSuccessHandler(B http, RequestMatcher preferredMatcher) {
		LogoutConfigurer<B> logout = http.getConfigurer(LogoutConfigurer.class);
		if (logout == null) {
			return;
		}
		LogoutConfigurer<B> handler = logout.defaultLogoutSuccessHandlerFor(
				postProcess(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT)), preferredMatcher);
	}

	@Override
	public void configure(B http) {
        //获取认证管理器，默认实现类为ProviderManager
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		BasicAuthenticationFilter basicAuthenticationFilter = new BasicAuthenticationFilter(authenticationManager,
				this.authenticationEntryPoint);
		if (this.authenticationDetailsSource != null) {
			basicAuthenticationFilter.setAuthenticationDetailsSource(this.authenticationDetailsSource);
		}
		if (this.securityContextRepository != null) {
			basicAuthenticationFilter.setSecurityContextRepository(this.securityContextRepository);
		}
		RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
		if (rememberMeServices != null) {
			basicAuthenticationFilter.setRememberMeServices(rememberMeServices);
		}
		basicAuthenticationFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
		basicAuthenticationFilter = postProcess(basicAuthenticationFilter);
		http.addFilter(basicAuthenticationFilter);
	}

}

```

