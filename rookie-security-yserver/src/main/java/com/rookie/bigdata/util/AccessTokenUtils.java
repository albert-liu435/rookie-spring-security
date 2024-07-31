package com.rookie.bigdata.util;

import com.rookie.bigdata.domain.dto.CustomUserDetailsDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * @Class AccessTokenUtils
 * @Description 用于生成 AccessToken
 * @Author rookie
 * @Date 2024/7/31 17:36
 * @Version 1.0
 */
public class AccessTokenUtils {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_TOKEN_TYPE = "Bearer ";

    /**
     * access-token 的有效时长 (秒)
     */
    public static final long LIFE_TIME = 60 * 60;

    private static final String SECRET = "5ae95dd3c5f811b9b819434910c52820ae7cfb3d9f7961e7117b24a7012873767d79f61f81fc2e06ebb6fd4f09ab47764d6e20607f843c22a0a2a6a6ed829680";

    /**
     * 签发人
     */
    private static final String ISSUER = "rookie";


    private AccessTokenUtils() {
    }


    //TODO

//    public static String getSubject(String jwt) {
//        return claims(jwt).getSubject();
//    }
//
//
//    public static String create(CustomUserDetailsDto customUserDetailsDto) {
//        customUserDetailsDto.setPassword("[PROTECTED]");
//        return Jwts.builder()
//                .setClaims(JSON.parseObject(JSON.toJSONString(customUserDetailsDto)))
//                .setSubject(customUserDetailsDto.getName())
//                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
//                .setIssuer(ISSUER)
//                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS512)
//                .serializeToJsonWith(map -> JSON.toJSONBytes(map))
//                .compact();
//    }
//
//
//    public static CustomUserDetailsDto getCustomUserDetails(String jwt) {
//        return JSON.parseObject(JSON.toJSONString(claims(jwt)), CustomUserDetailsDto.class);
//    }
//
//    private static Claims claims(String jwt) {
//        return Jwts.parser()
//                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
//                .deserializeJsonWith(bytes -> JSONObject.parseObject(new String(bytes), new TypeReference<Map<String, Object>>() {
//                }))
//                .build()
//                .parseClaimsJws(jwt)
//                .getBody();
//    }


}
