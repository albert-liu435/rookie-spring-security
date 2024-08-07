### DisableEncodeUrlFilter

禁止使用HttpServletResponse对URL进行编码
该过滤器用于禁用对URL进行编码的功能。它的作用是阻止Spring Security对URL进行自动编码，从而使得URL可以保持原始状态。
在某些情况下，用户可能希望禁用Spring Security对URL的编码，例如在特定的代理服务器或反向代理服务器上，因为这些代理服务器可能会自己处理URL的编码。此时就可以使用 DisableEncodeUrlFilter 来禁用Spring Security对URL的编码。
当你在Spring Security配置中加入 DisableEncodeUrlFilter 时，它将会在过滤器链中起作用，禁止Spring Security对URL进行编码。具体来说，它会覆盖默认的 DefaultSecurityFilterChain 配置，以确保 URL 编码被禁用。

参考：[SpringSecurity6 | 核心过滤器](https://juejin.cn/post/7298699367790313508)

