package com.rookie.bigdata.config;

import com.rookie.bigdata.config.common.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Class TokenConfig
 * @Description
 * @Author rookie
 * @Date 2024/7/4 17:50
 * @Version 1.0
 */
@Configuration
//@EnableConfigurationProperties({TokenProperties.class})
public class TokenConfig {


//    private final TokenProperties tokenProperties;
//
//
//    @Autowired
//    public TokenConfig(TokenProperties tokenProperties) {
//        this.tokenProperties = tokenProperties;
//    }

    @Bean
    @ConfigurationProperties(prefix = "token")
    public TokenProperties tokenProperties() {
        return new TokenProperties();
    }

}
