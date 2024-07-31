package com.rookie.bigdata.util;

import com.rookie.bigdata.security.core.userdetails.CustomUserDetailsService;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class AccessTokenUtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/7/31 17:36
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Slf4j
class AccessTokenUtilsTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;



    @Test
    void testJSON(){

        UserDetails customUserDetailsDto = customUserDetailsService.loadUserByUsername("admin");

//        JSON.parseObject(JSON.toJSONString(customUserDetailsDto));




    }

    @Test
    void testKeyHash(){
//        Keys.hmacShaKeyFor()
    }

}