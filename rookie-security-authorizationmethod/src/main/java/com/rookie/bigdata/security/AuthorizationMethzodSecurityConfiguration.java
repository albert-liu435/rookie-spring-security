package com.rookie.bigdata.security;

import com.rookie.bigdata.mapper.UserMapper;
import com.rookie.bigdata.security.core.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
public class AuthorizationMethzodSecurityConfiguration {


    @Autowired
    private UserMapper userMapper;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults())


//                .formLogin(new FormLoginConfigurerCustomizer())
//                .formLogin(httpFormLogin -> {
//                    httpFormLogin
//                            .loginPage("/login")
//                            //默认为username password,可以在这里进行修改
//                            .usernameParameter("username")
//                            .passwordParameter("password");
//                })
//                .rememberMe(withDefaults())
        ;


//                        .authorizeHttpRequests((authorize) -> authorize
//                .anyRequest().authenticated()
//        )
//                .csrf((csrf) -> csrf.ignoringRequestMatchers("/token"))
//                .httpBasic(Customizer.withDefaults())
////                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
////                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt(Customizer.withDefaults()))
//                .oauth2ResourceServer((oAuth2ResourceServerConfigurer) -> oAuth2ResourceServerConfigurer
//                        .jwt(Customizer.withDefaults())
//                )
//                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling((exceptions) -> exceptions
//                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
//                );

        return http.build();
    }

    /**
     * 实例化bean,在这里实例化可以方便的看到
     *
     * @return
     */
    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userMapper);
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
