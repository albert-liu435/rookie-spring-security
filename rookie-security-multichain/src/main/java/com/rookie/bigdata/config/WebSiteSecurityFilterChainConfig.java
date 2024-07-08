package com.rookie.bigdata.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Class WebSiteSecurityFilterChainConfig
 * @Description
 * @Author rookie
 * @Date 2024/7/8 18:10
 * @Version 1.0
 */
@Configuration
public class WebSiteSecurityFilterChainConfig {
    /**
     * 处理 给 webSite（非前后端分离） 端使用的过滤链
     * 以 页面 的格式返回给前端
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSiteSecurityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        // 创建用户
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"))
                .and()
                .withUser("dev")
                .password(new BCryptPasswordEncoder().encode("dev"))
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEV"))
                .and()
                .passwordEncoder(new BCryptPasswordEncoder());

        http.securityMatcher("/**")
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests.anyRequest()
                                .hasRole("ADMIN"))
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
//                // 用户认证
//                .addFilterBefore((request, response, chain) -> {
//                    // 此处可以模拟从 token 中解析出用户名、权限等
//                    String token = ((HttpServletRequest) request).getHeader("token");
//                    if (!StringUtils.hasText(token)) {
//                        chain.doFilter(request, response);
//                        return;
//                    }
//                    Authentication authentication = new TestingAuthenticationToken(token, null,
//                            AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    chain.doFilter(request, response);
//                }, UsernamePasswordAuthenticationFilter.class);
                // 禁用csrf
                .csrf((csrf)->csrf.disable())
                //// 启用表单登录
                .formLogin((formLogin)->formLogin.permitAll())
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.accessDeniedHandler((request, response, exception) -> response.sendRedirect("http://www.baidu.com")));

        return http.build();


//        return http.build();

//        // 只处理 所有 开头的请求
//        return http.antMatcher("/**")
//                .authorizeRequests()
//                // 所有请求都必须要认证才可以访问
//                .anyRequest()
//                .hasRole("ADMIN")
//                .and()
//                // 禁用csrf
//                .csrf()
//                .disable()
//                // 启用表单登录
//                .formLogin()
//                .permitAll()
//                .and()
//                // 捕获成功认证后无权限访问异常，直接跳转到 百度
//                .exceptionHandling()
//                .accessDeniedHandler((request, response, exception) -> response.sendRedirect("http://www.baidu.com"))
//                .and()
//                .build();
    }

    /**
     * 忽略静态资源
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer( ){
        return web -> web.ignoring()
                //.requestMatchers()
                .requestMatchers("/**/js/**")
                .requestMatchers("/**/css/**");

    }
}
