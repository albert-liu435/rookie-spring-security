package com.rookie.bigdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author rookie
 * @Description Controller for "/".
 * @Date 2024/6/26 21:10
 * @Version 1.0
 */
@Controller
public class IndexController {

    /**
     * localhost:8888/hello
     * Basic Auth认证， user:password
     * @return
     */
    @GetMapping("/")
    public String index() {

        return "index";
    }

}
