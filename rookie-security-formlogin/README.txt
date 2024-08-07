输入:http://localhost:8888/private
由于第一次请求没有经过认证，所以会进入到AnonymousAuthenticationFilter过滤器中，然后进行过滤，主要方法如下
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

然后经过AuthorizationFilter进行认证，


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

后面由ExceptionTranslationFilter来处理异常,并最终重定向到登录页面

Security filter chain: [
  DisableEncodeUrlFilter
  WebAsyncManagerIntegrationFilter
  SecurityContextHolderFilter
  HeaderWriterFilter
  CorsFilter
  CsrfFilter
  LogoutFilter
  UsernamePasswordAuthenticationFilter
  DefaultLoginPageGeneratingFilter
  DefaultLogoutPageGeneratingFilter
  BasicAuthenticationFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  ExceptionTranslationFilter
  AuthorizationFilter
]
