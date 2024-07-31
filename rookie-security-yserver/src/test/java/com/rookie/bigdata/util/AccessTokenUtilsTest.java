package com.rookie.bigdata.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rookie.bigdata.security.core.userdetails.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Type;
import java.util.Map;


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
    void testJSON() {

        UserDetails customUserDetailsDto = customUserDetailsService.loadUserByUsername("admin");

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(customUserDetailsDto));
        log.info("jsonObject:{}", jsonObject);

        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()//序列化为null对象
                .create();
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();

        Map<String, Object> map = gson.fromJson(gson.toJson(customUserDetailsDto), mapType);

        log.info("map:{}", map);
    }

    @Test
    void testKeyHash() {
//        Keys.hmacShaKeyFor()
    }

}
