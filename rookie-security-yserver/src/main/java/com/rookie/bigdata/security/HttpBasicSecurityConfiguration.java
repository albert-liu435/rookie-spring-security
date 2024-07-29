package com.rookie.bigdata.security;

import com.rookie.bigdata.security.web.authentication.www.MyBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Class Explicit
 * @Description
 * @Author rookie
 * @Date 2024/7/3 9:03
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
//@EnableWebSecurity()
public class HttpBasicSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )

//                .httpBasic(withDefaults())
                .httpBasic(httpBasic -> httpBasic
                        //自定义AuthenticationEntryPoint
                        .authenticationEntryPoint(new MyBasicAuthenticationEntryPoint()))


                .formLogin(withDefaults());
//                     .exceptionHandling((exceptions) -> exceptions
//                .withObjectPostProcessor(new ObjectPostProcessor<ExceptionTranslationFilter>() {
//                    @Override
//                    public <O extends ExceptionTranslationFilter> O postProcess(O filter) {
//                        filter.setAuthenticationTrustResolver(new MfaTrustResolver());
//                        return filter;
//                    }
//                })
//        );
        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
