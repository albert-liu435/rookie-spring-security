package com.rookie.bigdata.security.config;

import com.rookie.bigdata.security.web.authentication.www.MyBasicAuthenticationEntryPoint;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;

/**
 * @Class HttpBasicConfigurerCustomizer
 * @Description
 * @Author rookie
 * @Date 2024/7/26 15:56
 * @Version 1.0
 */
public class HttpBasicConfigurerCustomizer implements Customizer<HttpBasicConfigurer<HttpSecurity>> {
    @Override
    public void customize(HttpBasicConfigurer<HttpSecurity> httpSecurityHttpBasicConfigurer) {

        httpSecurityHttpBasicConfigurer
                .authenticationEntryPoint(new MyBasicAuthenticationEntryPoint());
    }
}
