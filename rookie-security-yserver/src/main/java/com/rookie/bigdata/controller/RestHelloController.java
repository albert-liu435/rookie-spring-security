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


    @RequestMapping("/code")
    public String hello() {
        return "code";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }


    @RequestMapping("/app/hello")
    public String appHello(){

        return "appHello";
    }

    @RequestMapping("/web/hello")
    public String webHello(){

        return "webHello";
    }
}
