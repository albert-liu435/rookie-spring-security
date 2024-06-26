package com.rookie.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Author rookie
 * @Description Hello Security application.
 * @Date 2024/6/26 21:10
 * @Version 1.0
 */
@SpringBootApplication
public class HelloSecurityApplication {

    public static void main(String[] args) {

        //UserDetailsServiceAutoConfiguration
        SpringApplication.run(HelloSecurityApplication.class, args);
    }



}