package com.rookie.bigdata.security.web.authentication.www;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Class MyBasicAuthenticationEntryPoint
 * @Description
 * @Author rookie
 * @Date 2024/7/26 17:38
 * @Version 1.0
 */
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {


    private String realmName;

    @Override
    public void afterPropertiesSet() {
        //realmName 不赋值会报错，因为下面 Assert.hasText的代码，也可以将其去掉
        this.realmName="MyrealmName";
        Assert.hasText(this.realmName, "realmName must be specified");
    }

    /**
     * 自定义请求失败后的响应信息
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\"" + this.realmName + "\"");
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        //接口返回json
        PrintWriter pw = response.getWriter();
        String responseText = "{\"code\":401,\"data\":\"Unauthorized\"}".toString();
        pw.print(responseText);
        pw.close();//注意关闭输出流
    }

}
