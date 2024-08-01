package com.rookie.bigdata.security;

import com.rookie.bigdata.security.access.CustomAccessDeniedHandler;
import com.rookie.bigdata.security.web.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Class FormLoginSecurityConfiguration
 * @Description
 * @Author rookie
 * @Date 2024/7/30 9:09
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
public class YServerSecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//        .csrf((csrf) -> {
//                    csrf.ignoringRequestMatchers(LOGIN_URI, REGISTER_URI)
//                            .csrfTokenRepository(csrfTokenRedisRepository)
////                    .csrfTokenRequestHandler(requestHandler)
//                    ;
//                })
                .csrf(withDefaults())


//                .authorizeRequests((authorizeRequests) ->
//                        authorizeRequests
//                                .requestMatchers("/auth/**").permitAll()
//                                .anyRequest().authenticated()
//                                .withObjectPostProcessor(new FilterSecurityInterceptorPostProcessor(accessDecisionManager, securityMetadataSource))
//                )

                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

//                .addFilterAt(new JWTAuthenticationFilter(authenticationManager(), redisService), UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(new JWTAuthorizationFilter(redisService), JWTAuthenticationFilter.class)

                //异常处理: 处理 AccessDeniedException 和 AuthenticationException
                .exceptionHandling(exceptionHandler ->
                        exceptionHandler
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                );


        return http.build();
    }


    /**
     * 这里能起作用并注入到spring security中，可以参考InitializeUserDetailsBeanManagerConfigurer.InitializeUserDetailsManagerConfigurer#configure()方法中的
     * PasswordEncoder passwordEncoder = getBeanOrNull(PasswordEncoder.class);从spring容器中获取PasswordEncoder
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
