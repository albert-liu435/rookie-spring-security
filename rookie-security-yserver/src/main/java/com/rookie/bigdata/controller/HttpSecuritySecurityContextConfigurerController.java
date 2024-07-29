package com.rookie.bigdata.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class httpSecuritySecurityContextConfigurerController
 * @Description
 * @Author rookie
 * @Date 2024/7/29 16:42
 * @Version 1.0
 */

@RestController
public class HttpSecuritySecurityContextConfigurerController {


    @RequestMapping("/httpSecuritySecurityContextConfigurer/code")
    public String httpSecuritySecurityContextConfigurerCode() {
        return "httpSecuritySecurityContextConfigurerCode";
    }


}
