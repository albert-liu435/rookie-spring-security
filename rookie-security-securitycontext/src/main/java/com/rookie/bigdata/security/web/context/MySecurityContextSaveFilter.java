package com.rookie.bigdata.security.web.context;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * @Class MySecurityContextHolderFilter
 * @Description
 * @Author rookie
 * @Date 2024/7/29 16:30
 * @Version 1.0
 */
public class MySecurityContextSaveFilter  extends GenericFilterBean {


    private static final String FILTER_APPLIED = MySecurityContextSaveFilter.class.getName() + ".APPLIED";

    private final SecurityContextRepository securityContextRepository;

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    public MySecurityContextSaveFilter(SecurityContextRepository securityContextRepository) {
        Assert.notNull(securityContextRepository, "securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (request.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter(request, response);
            return;
        }
        request.setAttribute(FILTER_APPLIED, Boolean.TRUE);


        SecurityContext context = securityContextHolderStrategy.getContext();
        this.securityContextRepository.saveContext(context,request,response);

//        Supplier<SecurityContext> deferredContext = this.securityContextRepository.loadDeferredContext(request);
        try {
//            this.securityContextHolderStrategy.setDeferredContext(deferredContext);
            chain.doFilter(request, response);
        }
        finally {
//            this.securityContextHolderStrategy.clearContext();
            request.removeAttribute(FILTER_APPLIED);
        }
    }
}
