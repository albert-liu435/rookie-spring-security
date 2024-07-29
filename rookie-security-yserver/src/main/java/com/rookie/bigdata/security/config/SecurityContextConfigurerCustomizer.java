package com.rookie.bigdata.security.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * @Class SecurityContextConfigurerCustomizer
 * @Description
 * @Author rookie
 * @Date 2024/7/29 15:15
 * @Version 1.0
 */
public class SecurityContextConfigurerCustomizer implements Customizer<SecurityContextConfigurer<HttpSecurity>> {
    @Override
    public void customize(SecurityContextConfigurer<HttpSecurity> httpSecuritySecurityContextConfigurer) {
        //这个也是spring security的默认实现，我们可以定义自己的实现
        SecurityContextRepository securityContextRepository = new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());

        httpSecuritySecurityContextConfigurer
                .securityContextRepository(securityContextRepository);
    }
}
