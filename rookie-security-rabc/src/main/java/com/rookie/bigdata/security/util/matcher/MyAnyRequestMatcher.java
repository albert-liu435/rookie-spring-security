package com.rookie.bigdata.security.util.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @Class MyAnyRequestMatcher
 * @Description 参照AnyRequestMatcher
 * @Author rookie
 * @Date 2024/7/2 17:51
 * @Version 1.0
 */
public class MyAnyRequestMatcher implements RequestMatcher {

    public static final RequestMatcher INSTANCE = new MyAnyRequestMatcher();

    private MyAnyRequestMatcher() {
    }

    /**
     * 始终返回true,即匹配所有的请求的uri
     *
     * @param request the request to check for a match
     * @return
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean equals(Object obj) {
        return obj instanceof MyAnyRequestMatcher
                || obj instanceof com.rookie.bigdata.security.util.matcher.MyAnyRequestMatcher;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "my any request";
    }
}
