package com.rookie.bigdata.controller;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Class RestHelloController
 * @Description
 * @Author rookie
 * @Date 2024/7/2 14:44
 * @Version 1.0
 */

@RestController
public class RestHelloController {

    /**
     * http://localhost:8888/private
     * @return
     */
    @RequestMapping("/private")
    public String privateSource() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        ServletInputStream servletInputStream = request.getInputStream();
//        String requestURI = request.getRequestURI();

        return "private";
    }

//
//
//
//    @RequestMapping("/code")
//    public String hello() {
//
//
//        return "code";
//    }
//
//    @RequestMapping("/test")
//    public String test() {
//        return "test";
//    }


}
