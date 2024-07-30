package com.rookie.bigdata.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
public class HttpSecuritySecurityContextConfigurerController {


    /**
     *http://localhost:8888/httpSecuritySecurityContextConfigurer/code basic认证：user:password
     * @return
     */
    @RequestMapping("/httpSecuritySecurityContextConfigurer/code")
    public String httpSecuritySecurityContextConfigurerCode() {
        return "httpSecuritySecurityContextConfigurerCode";
    }


    /**
     *http://localhost:8888/httpSecuritySecurityContextConfigurer/context basic认证：user:password
     * @return
     */
    @RequestMapping("/httpSecuritySecurityContextConfigurer/context")
    public String securityContext() {
        SecurityContext context = SecurityContextHolder.getContext();
//        SecurityContext context = SecurityContextHolder.createEmptyContext();

//        Authentication authentication =
//                new TestingAuthenticationToken("username", "password", "ROLE_USER");
//        context.setAuthentication(authentication);

        Authentication authentication = context.getAuthentication();
        log.info("获取Authentication：{},{}", authentication.getPrincipal(), authentication.getCredentials());


//        SecurityContextHolder.setContext(context);
        return "securityContext";
    }

}
