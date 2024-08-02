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
 * @Class PathVariableEnpointControllerTest
 * @Description Matching Using Ant
 * @Author rookie
 * @Date 2024/8/2 13:37
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class PathVariableEnpointControllerTest {
    @Autowired
    private MockMvc mvc;

    @WithMockUser(authorities="USER")
    @Test
    void endpointWhenUserAuthorityThenAuthorized() throws Exception {
        this.mvc.perform(get("/resource/jon"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void endpointWhenNotUserAuthorityThenForbidden() throws Exception {
        this.mvc.perform(get("/resource/jon"))
                .andExpect(status().isForbidden());
    }

    @Test
    void anyWhenUnauthenticatedThenUnauthorized() throws Exception {
        this.mvc.perform(get("/any"))
                .andExpect(status().isUnauthorized());
    }

}
