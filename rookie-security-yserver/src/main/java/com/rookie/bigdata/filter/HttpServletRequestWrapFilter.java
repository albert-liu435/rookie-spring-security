package com.rookie.bigdata.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class HttpServletRequestWrapFilter
 * @Description TODO
 * @Author rookie
 * @Date 2024/8/1 10:08
 * @Version 1.0
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)    // 可以指定优先级，不填的话默认为最小的优先级
@Slf4j
public class HttpServletRequestWrapFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("request body servlet request wrap filter");


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    private static class RequestBodyServletRequestWrapper extends HttpServletRequestWrapper {

        /**
         * 请求体数据
         */
        private final byte[] requestBody;
        /**
         * 重写的参数 Map
         */
        private final Map<String, String[]> paramMap;

        public RequestBodyServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);

            // 重写 requestBody
            requestBody = IOUtils.toByteArray(request.getReader(), StandardCharsets.UTF_8);

            // 重写参数 Map
            paramMap = new HashMap<>();

            if (requestBody.length == 0) {
                return;
            }


            JSON.parseObject(getRequestBody()).forEach((key, value) -> paramMap.put(key, new String[]{String.valueOf(value)}));
        }

        public String getRequestBody() {

            return new String(requestBody, StandardCharsets.UTF_8);
//            return StringUtils.toEncodedString(requestBody, StandardCharsets.UTF_8);
        }

        // ~ get
        // -----------------------------------------------------------------------------------------------------------------

        @Override
        public Map<String, String[]> getParameterMap() {
            return paramMap;
        }

        @Override
        public String getParameter(String key) {
            String[] valueArr = paramMap.get(key);
            if (valueArr == null || valueArr.length == 0) {
                return null;
            }
            return valueArr[0];
        }

        @Override
        public String[] getParameterValues(String key) {
            return paramMap.get(key);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(paramMap.keySet());
        }

        // ~ read
        // -----------------------------------------------------------------------------------------------------------------

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {

                }

                @Override
                public int read() {
                    return inputStream.read();
                }
            };
        }
    }
}
