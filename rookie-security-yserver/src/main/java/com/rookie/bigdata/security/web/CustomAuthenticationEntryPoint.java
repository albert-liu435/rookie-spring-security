package com.rookie.bigdata.security.web;

import com.rookie.bigdata.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * @Class CustomAuthenticationEntryPoint
 * @Description
 * @Author rookie
 * @Date 2024/8/1 10:35
 * @Version 1.0
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtils.unauthorizedResponse(response, authException.getMessage());
    }
}
