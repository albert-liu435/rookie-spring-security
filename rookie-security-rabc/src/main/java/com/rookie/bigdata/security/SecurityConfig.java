package com.rookie.bigdata.security;

import com.rookie.bigdata.security.customizer.AuthorizeHttpRequestsCustomizer;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Class SecurityConfig
 * @Description spring security 配置类
 * @Author rookie
 * @Date 2024/7/2 17:29
 * @Version 1.0
 */
@Configuration
public class SecurityConfig {


    /**
     * 这个Bean实例化会替代org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration.SecurityFilterChainConfiguration#defaultSecurityFilterChain这个方法的实例化
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.authorizeHttpRequests(new AuthorizeHttpRequestsCustomizer());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
}
