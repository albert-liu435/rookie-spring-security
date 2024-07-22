package com.rookie.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DmoApplication
 * @Description https://docs.spring.io/spring-security/reference/index.html
 * Hello Security application.
 * @Author rookie
 * @Date 2023/2/28 23:26
 * @Version 1.0
 */
@SpringBootApplication
public class EncryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptApplication.class, args);


    }



}
