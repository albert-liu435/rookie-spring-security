package com.rookie.bigdata.security.customizer;

import com.rookie.bigdata.security.util.matcher.MyAnyRequestMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * @Class AuthorizeHttpRequestsCustomizer
 * @Description
 * @Author rookie
 * @Date 2024/7/2 17:36
 * @Version 1.0
 */
@Slf4j
public class AuthorizeHttpRequestsCustomizer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    /**
     * 这个http.authorizeHttpRequests(new AuthorizeHttpRequestsCustomizer());同
     * http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());是一致的
     *
     * @param authorizationManagerRequestMatcherRegistry the input argument
     */
    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {

        authorizationManagerRequestMatcherRegistry
                .requestMatchers(MyAnyRequestMatcher.INSTANCE)
//                .anyRequest()
                .authenticated();
    }
}
