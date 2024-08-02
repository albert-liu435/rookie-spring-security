package com.rookie.bigdata.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Class EnpointControllerTest
 * @Description
 * @Author rookie
 * @Date 2024/8/2 11:49
 * @Version 1.0
 */

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class EnpointControllerTest {

    @Autowired
    private MockMvc mvc;


    @WithMockUser(authorities="USER")
    @Test
    void endpointWhenUserAuthorityThenAuthorized() throws Exception {
        this.mvc.perform(get("/endpoint"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void endpointWhenNotUserAuthorityThenForbidden() throws Exception {
        this.mvc.perform(get("/endpoint"))
                .andExpect(status().isForbidden());
    }

    @Test
    void anyWhenUnauthenticatedThenUnauthorized() throws Exception {
        this.mvc.perform(get("/any"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void endpointSource() {
    }
}
