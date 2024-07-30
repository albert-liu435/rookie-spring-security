package com.rookie.bigdata.security.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * @Class AuthorizeHttpRequestsConfigurerCustomizer
 * @Description
 * @Author rookie
 * @Date 2024/7/26 15:04
 * @Version 1.0
 */
public class AuthorizeHttpRequestsConfigurerCustomizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    /**
     * http
     * .authorizeHttpRequests((authorize) -> authorize
     * .anyRequest().authenticated()
     * )
     * <p>
     * 同下面是一样的
     * http
     * .authorizeHttpRequests(new AuthorizeHttpRequestsConfigurerCustomizer())
     */

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
        authorizationManagerRequestMatcherRegistry
                .anyRequest()
                .authenticated();
    }


}
