package com.rookie.bigdata.security;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

/**
 * @Class MfaTrustResolver
 * @Description
 * @Author rookie
 * @Date 2024/7/12 17:43
 * @Version 1.0
 */
public class MfaTrustResolver implements AuthenticationTrustResolver {

    private final AuthenticationTrustResolver delegate = new AuthenticationTrustResolverImpl();

    @Override
    public boolean isAnonymous(Authentication authentication) {
        return this.delegate.isAnonymous(authentication) || authentication instanceof MfaAuthentication;
    }

    @Override
    public boolean isRememberMe(Authentication authentication) {
        return this.delegate.isRememberMe(authentication);
    }

}
