package com.rookie.bigdata.security.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

/**
 * @Class FormLoginConfigurerCustomizer
 * @Description
 * @Author rookie
 * @Date 2024/7/30 9:11
 * @Version 1.0
 */
public class FormLoginConfigurerCustomizer implements Customizer<FormLoginConfigurer<HttpSecurity>> {
    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
        httpSecurityFormLoginConfigurer
                //登录页面，可以根据自己得需要进行不同得设置，默认为/login,在AbstractAuthenticationFilterConfigurer得构造方法中设置
                .loginPage("/login")
                //默认为username password,可以在这里进行修改
                .usernameParameter("username")
                .passwordParameter("password")
                //失败和成功url,默认执行init()中得updateAuthenticationDefaults方法进行设置 默认失败的handler为SimpleUrlAuthenticationFailureHandler，默认失败为this.loginPage + "?error"
                .failureUrl("/login?error")
                //SavedRequestAwareAuthenticationSuccessHandler
//                .defaultSuccessUrl()
//                .failureForwardUrl()
//                .successForwardUrl()
//                .authenticationDetailsSource()
//                .loginProcessingUrl()
//                .securityContextRepository()

                .permitAll()
        ;
    }
}
