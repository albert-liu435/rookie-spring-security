package com.rookie.bigdata.filter;

import cn.hutool.core.lang.UUID;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Class APIUIDFIlter
 * @Description 用来设置请求的唯一ID
 * @Author rookie
 * @Date 2024/8/1 9:53
 * @Version 1.0
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)    // 可以指定优先级，不填的话默认为最小的优先级
@Slf4j
public class APIUIDFilter implements Filter {

    public static final String REQUEST_ID_KEY = "request-id";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        log.info("Filter-APIUIDFilter");

        String requestId = ((HttpServletRequest) request).getHeader("X-Request-ID");

        if (request instanceof HttpServletRequest) {


            if (StringUtils.isEmpty(requestId)) {
                requestId = UUID.randomUUID().toString();
            }

            MDC.put(REQUEST_ID_KEY, requestId);
        }

        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).setHeader("X-Request-ID", requestId);
        }
//        log.info("原生Filter-APIUIDFilter2");

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_ID_KEY);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

