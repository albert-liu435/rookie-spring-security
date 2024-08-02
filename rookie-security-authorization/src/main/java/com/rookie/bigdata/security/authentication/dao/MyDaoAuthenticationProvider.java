//package com.rookie.bigdata.security.authentication.dao;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.util.Assert;
//
///**
// * @Class MyDaoAuthenticationProvider
// * @Description
// * @Author rookie
// * @Date 2024/7/31 10:00
// * @Version 1.0
// */
//
//@Slf4j
//public class MyDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//
//    private PasswordEncoder passwordEncoder;
//
//
//    private volatile String userNotFoundEncodedPassword;
//
//
//    public MyDaoAuthenticationProvider() {
//    }
//
//    public MyDaoAuthenticationProvider(PasswordEncoder passwordEncoder) {
//        setPasswordEncoder(passwordEncoder);
//    }
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        log.info("执行了MyDaoAuthenticationProvider");
////        return super.authenticate(authentication);
//
//        return null;
//    }
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        return null;
//    }
//
//    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
//        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
//        this.passwordEncoder = passwordEncoder;
//        this.userNotFoundEncodedPassword = null;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return super.supports(authentication);
//    }
//}
