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
public class UserDetailsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDetailsServiceApplication.class, args);


    }


    @Bean
    MapCustomUserRepository userRepository() {
        // the hashed password was calculated using the following code
        // the hash should be done up front, so malicious users cannot discover the
        // password
        // PasswordEncoder encoder =
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPassword1 = encoder.encode("password");

        // the raw password is "password"
        String encodedPassword = "{bcrypt}$2a$10$h/AJueu7Xt9yh3qYuAXtk.WZJ544Uc2kdOKlHu2qQzCh/A3rq46qm";

        CustomUser customUser = new CustomUser(1L, "user@example.com", encodedPassword);
        Map<String, CustomUser> emailToCustomUser = new HashMap<>();
        emailToCustomUser.put(customUser.getEmail(), customUser);
        return new MapCustomUserRepository(emailToCustomUser);
    }



}
