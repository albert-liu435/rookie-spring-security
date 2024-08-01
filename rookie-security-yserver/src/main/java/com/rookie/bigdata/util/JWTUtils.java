package com.rookie.bigdata.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.rookie.bigdata.domain.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * @Class JWTUtils
 * @Description
 * @Author rookie
 * @Date 2024/8/1 9:31
 * @Version 1.0
 */
public class JWTUtils {
    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "5ae95dd3c5f811b9b819434910c52820ae7cfb3d9f7961e7117b24a7012873767d79f61f81fc2e06ebb6fd4f09ab47764d6e20607f843c22a0a2a6a6ed829680";

    /**
     * 签发人
     */
    private static final String ISSUER = "rookie";

    /**
     * 默认过期时间 3600s
     */
    private static final long EXPIRATION = 3600L;

    /**
     * 选择了记住我之后的过期时间 3600s * 7
     */
    private static final long EXPIRATION_REMEMBER = 3600L * 7;

    private JWTUtils() {
    }



    /**
     * 创建 Json Web Token
     */
    public static String create(String username, boolean rememberMe, CustomUserDetails userDetails) {
        return Jwts.builder()
                // [Attention] 要先 setClaims(初始化底层 map) 再设置 subject, 如果 subject 先设置, 会被覆盖.
                .setClaims(JSON.parseObject(JSON.toJSONString(userDetails)))
                // 主题
                .setSubject(username)
                // TODO 过期时间交由 Redis 处理
                // .setExpiration(
                //         Date.from(
                //                 LocalDateTime.now()
                //                         .plus((rememberMe ? EXPIRATION_REMEMBER : EXPIRATION) * 1000, ChronoUnit.MILLIS)
                //                         .atZone(ZoneId.systemDefault())
                //                         .toInstant()
                //         )
                // )
                // 颁发时间
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                // 颁发人
                .setIssuer(ISSUER)
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS512)
                .serializeToJsonWith(map -> JSON.toJSONBytes(map))
                .compact();
    }


    /**
     * 获得 subject
     */
    public static String subject(String jwt) {
        return claims(jwt).getSubject();
    }

    public static CustomUserDetails userDetails(String jwt) {
        return JSON.parseObject(JSON.toJSONString(claims(jwt)), CustomUserDetails.class);
    }

    private static Claims claims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .deserializeJsonWith(bytes -> JSONObject.parseObject(new String(bytes), new TypeReference<Map<String, Object>>() {
                }))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
