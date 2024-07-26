package com.rookie.bigdata.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
public class SecurityConfiguration {


//    @Bean
//    @Order(1)
//    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
//        //这里可以按照自己的需求配置不同的SecurityConfigurer,包括认证方式等等信息
//        http.securityMatcher("/app/**")
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults())
//                .exceptionHandling((exceptionHandling) ->
//                        exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
//                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//                                    response.setContentType(MediaType.APPLICATION_JSON.toString());
//                                    response.getWriter().write("{\"message:\":\"您无权访问01\"}");
//                                })
//                                .accessDeniedHandler((request, response, accessDeniedException) -> {
//                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//                                    response.setContentType(MediaType.APPLICATION_JSON.toString());
//                                    response.getWriter().write("{\"message:\":\"您无权访问02\"}");
//                                }))
//        ;
//        return http.build();
//    }
//
//    @Bean
//    @Order(2)
//    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
//        //这里可以按照自己的需求配置不同的SecurityConfigurer,包括认证方式等等信息
//        http.securityMatcher("/web/**")
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults());
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )

                .authorizeHttpRequests(new Customizer<AuthorizeHttpRequestsConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
                    @Override
                    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
                        authorizationManagerRequestMatcherRegistry
//                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/code").permitAll()
//                                .requestMatchers("/code1").access();
                                .anyRequest().authenticated();
                    }
                })
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
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
