package com.rookie.bigdata.config.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Class TokenProperties
 * @Description
 * @Author rookie
 * @Date 2024/7/4 17:50
 * @Version 1.0
 */
//@ConfigurationProperties(prefix = "token")
@Data
public class TokenProperties {
    private String secretKey;
    private Long tokenExpireSecond;
    private String tokenHeaderPrefix;
    private String authorizationHeaderName;
    private Long refreshTokenExpiredSecond;
    private String refreshHeaderName;
    private String userId;
}
