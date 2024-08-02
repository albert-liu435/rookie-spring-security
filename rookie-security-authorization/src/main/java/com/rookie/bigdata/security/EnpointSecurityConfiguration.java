package com.rookie.bigdata.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Class EnpointSecurityConfiguration
 * @Description
 * @Author rookie
 * @Date 2024/8/2 11:46
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
public class EnpointSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
////                .authorizeHttpRequests((authorize) ->authorize
////                        .requestMatchers("/endpoint").hasAuthority("USER")
////                        .anyRequest().authenticated()
////                )
//                //Matching Using Ant
////                Ant匹配模式
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/resource/**").hasAuthority("USER")
//                        .anyRequest().authenticated()
//                )

//                //提取路径
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/resource/{name}").access(new WebExpressionAuthorizationManager("#name == authentication.name"))
//                        .anyRequest().authenticated()
//                )

//                //Matching Using Regular Expressions
//
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(RegexRequestMatcher.regexMatcher("/resource/[A-Za-z0-9]+")).hasAuthority("USER")
//                        .anyRequest().denyAll()
//                )

////                Matching By Http Method
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(HttpMethod.GET).hasAuthority("read")
//                        .requestMatchers(HttpMethod.POST).hasAuthority("write")
//                        .anyRequest().denyAll()
//                )

////                Matching By Dispatcher Type
//                .authorizeHttpRequests((authorize) -> authorize
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
//                        .requestMatchers("/endpoint").permitAll()
//                        .anyRequest().denyAll()
//                )


                // Match by MvcRequestMatcher
//        @Bean
//        MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
//            return new MvcRequestMatcher.Builder(introspector).servletPath("/spring-mvc");
//        }
//
//        @Bean
//        SecurityFilterChain appEndpoints(HttpSecurity http, MvcRequestMatcher.Builder mvc) {
//            http
//                    .authorizeHttpRequests((authorize) -> authorize
//                            .requestMatchers(mvc.pattern("/my/controller/**")).hasAuthority("controller")
//                            .anyRequest().authenticated()
//                    );
//
//            return http.build();
//        }

//                Using a Custom Matcher
        RequestMatcher printview = (request) -> request.getParameter("print") != null;
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(printview).hasAuthority("print")
                        .anyRequest().authenticated()
                )


                .httpBasic(withDefaults())
                .formLogin(withDefaults())
        ;

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
    // @formatter:on

}
