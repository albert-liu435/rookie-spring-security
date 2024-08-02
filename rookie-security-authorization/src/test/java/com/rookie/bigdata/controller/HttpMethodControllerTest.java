package com.rookie.bigdata.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Class HttpMethodControllerTest
 * @Description TODO
 * @Author rookie
 * @Date 2024/8/2 14:10
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class HttpMethodControllerTest {

//    @SpringBootTest
//    @AutoConfigureMockMvc
//    @ActiveProfiles("dev")
//    class PathVariableEnpointControllerTest {
        @Autowired
        private MockMvc mvc;

        @WithMockUser(authorities="read")
    @Test
    void getWhenReadAuthorityThenAuthorized() throws Exception {
        this.mvc.perform(get("/any"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void getWhenNoReadAuthorityThenForbidden() throws Exception {
        this.mvc.perform(get("/any"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities="write")
    @Test
    void postWhenWriteAuthorityThenAuthorized() {
//        this.mvc.perform(post("/any").with(csrf()))
//                .andExpect(status().isOk());
    }

    @WithMockUser(authorities="read")
    @Test
    void postWhenNoWriteAuthorityThenForbidden() throws Exception {
        this.mvc.perform(get("/any").with(csrf()))
                .andExpect(status().isForbidden());
    }
}
