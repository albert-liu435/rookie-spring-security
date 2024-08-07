## 请求流程

### 第一次请求

在浏览器中请求：http://localhost:8888/private

由于第一次请求没有经过认证，所以会进入到AnonymousAuthenticationFilter过滤器中，然后进行过滤

```java
@Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {
	Supplier<SecurityContext> deferredContext = this.securityContextHolderStrategy.getDeferredContext();
	//由于是匿名调用，这里会产生一个AnonymousAuthenticationToken并保存到上下文中
	this.securityContextHolderStrategy
		.setDeferredContext(defaultWithAnonymous((HttpServletRequest) req, deferredContext));
	chain.doFilter(req, res);
}

	private SecurityContext defaultWithAnonymous(HttpServletRequest request, SecurityContext currentContext) {
	//从当前安全上下文中获取Authentication,此时为null
		Authentication currentAuthentication = currentContext.getAuthentication();
		//由于第一次为空，此时会创建一个AnonymousAuthenticationToken
		if (currentAuthentication == null) {
			Authentication anonymous = createAuthentication(request);
			if (this.logger.isTraceEnabled()) {
				this.logger.trace(LogMessage.of(() -> "Set SecurityContextHolder to " + anonymous));
			}
			else {
				this.logger.debug("Set SecurityContextHolder to anonymous SecurityContext");
			}
			//并创建一个空的安全上下文，将AnonymousAuthenticationToken设置进去并返回SecurityContext
			SecurityContext anonymousContext = this.securityContextHolderStrategy.createEmptyContext();
			anonymousContext.setAuthentication(anonymous);
			return anonymousContext;
		}
		else {
			if (this.logger.isTraceEnabled()) {
				this.logger.trace(LogMessage.of(() -> "Did not set SecurityContextHolder since already authenticated "
						+ currentAuthentication));
			}
		}
		return currentContext;
	}
```
然后经过AuthorizationFilter进行认证

```java
@Override
public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
		throws ServletException, IOException {

	HttpServletRequest request = (HttpServletRequest) servletRequest;
	HttpServletResponse response = (HttpServletResponse) servletResponse;

	if (this.observeOncePerRequest && isApplied(request)) {
		chain.doFilter(request, response);
		return;
	}

	if (skipDispatch(request)) {
		chain.doFilter(request, response);
		return;
	}

	String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName();
	request.setAttribute(alreadyFilteredAttributeName, Boolean.TRUE);
	try {
	    //认证失败，然后会抛出AccessDeniedException异常
		AuthorizationDecision decision = this.authorizationManager.check(this::getAuthentication, request);
		this.eventPublisher.publishAuthorizationEvent(this::getAuthentication, request, decision);
		if (decision != null && !decision.isGranted()) {
			throw new AccessDeniedException("Access Denied");
		}
		chain.doFilter(request, response);
	}
	finally {
		request.removeAttribute(alreadyFilteredAttributeName);
	}
}
```
后面由ExceptionTranslationFilter来处理异常,并最终重定向到登录页面，处理异常的逻辑是在ExceptionTranslationFilter中

主要处理异常代码如下

```
private void handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, AccessDeniedException exception) throws ServletException, IOException {
      //获取认证信息
   Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();
   //判断是否为匿名用户
   boolean isAnonymous = this.authenticationTrustResolver.isAnonymous(authentication);
   //是匿名用户或者为RememberMeAuthenticationToken
   if (isAnonymous || this.authenticationTrustResolver.isRememberMe(authentication)) {
      if (logger.isTraceEnabled()) {
         logger.trace(LogMessage.format("Sending %s to authentication entry point since access is denied",
               authentication), exception);
      }
      //重定向到登陆页面 
      sendStartAuthentication(request, response, chain,
            new InsufficientAuthenticationException(
                  this.messages.getMessage("ExceptionTranslationFilter.insufficientAuthentication",
                        "Full authentication is required to access this resource")));
   }
   else {
      if (logger.isTraceEnabled()) {
         logger.trace(
               LogMessage.format("Sending %s to access denied handler since access is denied", authentication),
               exception);
      }
      this.accessDeniedHandler.handle(request, response, exception);
   }
}
```

第二次请求

此时是登陆页面，在登陆页面输入正确的账号和密码点击登录进入过滤器，如下

AbstractAuthenticationProcessingFilter

```java
private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
    //匹配/login uri是否成功
	if (!requiresAuthentication(request, response)) {
		chain.doFilter(request, response);
		return;
	}
	try {
        //从请求中获取账号和密码，然后封装成UsernamePasswordAuthenticationToken，然后最终通过AbstractUserDetailsAuthenticationProvider#authenticate()进行认证
		Authentication authenticationResult = attemptAuthentication(request, response);
		if (authenticationResult == null) {
			// return immediately as subclass has indicated that it hasn't completed
			return;
		}
		this.sessionStrategy.onAuthentication(authenticationResult, request, response);
		// Authentication success
		if (this.continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}
		successfulAuthentication(request, response, chain, authenticationResult);
	}
	catch (InternalAuthenticationServiceException failed) {
		this.logger.error("An internal error occurred while trying to authenticate the user.", failed);
		unsuccessfulAuthentication(request, response, failed);
	}
	catch (AuthenticationException ex) {
		// Authentication failed
		unsuccessfulAuthentication(request, response, ex);
	}
}
```
认证成功之后会进入到下面方法

```
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
   SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
   context.setAuthentication(authResult);
   this.securityContextHolderStrategy.setContext(context);
   //这里会将认证信息保存到session中，session存储的为SecurityContext
   this.securityContextRepository.saveContext(context, request, response);
   if (this.logger.isDebugEnabled()) {
      this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
   }
   this.rememberMeServices.loginSuccess(request, response, authResult);
   if (this.eventPublisher != null) {
      this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
   }
   this.successHandler.onAuthenticationSuccess(request, response, authResult);
}
```

后续再进行请求的时候，会带着这个session进行请求

