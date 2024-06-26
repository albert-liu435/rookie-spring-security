package com.rookie.bigdata;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Class CurrentUser
 * @Description
 * @Author rookie
 * @Date 2024/6/26 17:21
 * @Version 1.0
 */
@AuthenticationPrincipal
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

}
