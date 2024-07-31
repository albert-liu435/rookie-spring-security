package com.rookie.bigdata.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class ApplicationConfigurationTest
 * @Description
 * @Author rookie
 * @Date 2024/7/31 17:04
 * @Version 1.0
 */

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Slf4j
class ApplicationConfigurationTest {

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Test
    void setContextPath() {

        log.info("uri地址:{}",ApplicationConfiguration.CONTEXT_PATH);
    }
}
