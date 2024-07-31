package com.rookie.bigdata.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class RequestUtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/7/31 17:07
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Slf4j
class RequestUtilsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getURI() {

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI("/server/hello");

        String uri = RequestUtils.getURI(mockHttpServletRequest);
        log.info("uri:{}", uri);

    }
}
