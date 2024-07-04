/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rookie.bigdata.controller;

import com.rookie.bigdata.config.common.TokenProperties;
import com.rookie.bigdata.token.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * A controller for the token resource.
 *
 * @author Josh Cummings
 */
@RestController
@Slf4j
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenController {

    private final TokenProperties tokenProperties;

    @Autowired
    public TokenController(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @GetMapping("/getToken")
    public void getToken(HttpServletResponse response,
                         @RequestParam("userId") String userId) {
        String authenticateToken = JwtUtils.generatorJwtToken(userId,
                tokenProperties.getUserId(),
                tokenProperties.getTokenExpireSecond(),
                tokenProperties.getSecretKey());

        String refreshToken = JwtUtils.generatorJwtToken(userId,
                tokenProperties.getUserId(),
                tokenProperties.getRefreshTokenExpiredSecond(),
                tokenProperties.getSecretKey());

        response.addHeader(tokenProperties.getAuthorizationHeaderName(), authenticateToken);
        response.addHeader(tokenProperties.getRefreshHeaderName(), refreshToken);

    }

    @GetMapping("/protectedMethod")
    public String protectedMethod() {
        System.out.println("访问受保护的方法");
        return "当前登录用户" + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
