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


    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
