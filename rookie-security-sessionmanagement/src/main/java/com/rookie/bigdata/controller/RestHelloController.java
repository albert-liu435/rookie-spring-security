package com.rookie.bigdata.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return "private";
    }

    /**
     * http://localhost:8888/sessionManagement
     * 用户名:密码 admin:admin
     * @return
     */
    @RequestMapping("/sessionManagement")
    public String sessionManagement() {
        return "sessionManagement";
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
