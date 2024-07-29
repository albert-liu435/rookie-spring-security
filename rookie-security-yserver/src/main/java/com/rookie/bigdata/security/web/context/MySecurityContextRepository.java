package com.rookie.bigdata.security.web.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Class MySecurityContextRepository
 * @Description 自定义实现 SecurityContextRepository  这里只是一个demo，在生产环境中，我们可以在通过将其保存在redis等缓存框架中
 * @Author rookie
 * @Date 2024/7/29 15:26
 * @Version 1.0
 */

@Slf4j
public class MySecurityContextRepository implements SecurityContextRepository {

    //生产环境可以使用第三方框架
    public Map<String, SecurityContext> securityContextMap = new HashMap<>();

    public static final String DEFAULT_REQUEST_ATTR_NAME = MySecurityContextRepository.class.getName()
            .concat(".SPRING_SECURITY_CONTEXT");

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();


    @Override
    public boolean containsContext(HttpServletRequest request) {
        return getContext(request) != null;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        return loadDeferredContext(requestResponseHolder.getRequest()).get();
    }

    @Override
    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
        Supplier<SecurityContext> supplier = () -> getContext(request);
//        return new MySupplierDeferredSecurityContext(supplier);
        return new MySupplierDeferredSecurityContext(supplier, this.securityContextHolderStrategy);
    }

    private SecurityContext getContext(HttpServletRequest request) {
        //获取SecurityContext,假设我们再第一次请求的时候，在header中没有code,在第二次请求中携带code
        if (request.getHeader("code") != null) {
            return securityContextMap.get(request.getHeader("code"));
        }
        return null;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
//        request.setAttribute(this.requestAttributeName, context);
        log.info("保存请求SecurityContext {}",context);
        securityContextMap.put("abcd", context);
    }


    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }
}
