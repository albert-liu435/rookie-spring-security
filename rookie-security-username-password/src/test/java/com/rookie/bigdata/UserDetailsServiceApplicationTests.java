package com.rookie.bigdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Class UserDetailsServiceApplicationTest
 * @Description
 * @Author rookie
 * @Date 2024/6/26 18:19
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserDetailsServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userWhenNotAuthenticated() throws Exception {
        // @formatter:off
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isUnauthorized());
        // @formatter:on
    }

    /**
     * WithUserDetails looks up the user from the UserDetailsService. The advantage is
     * this is easy to use. The disadvantage, is that the user must exist so it relies our
     * our data being set up properly. Alternatively, consider using a custom annotation
     * like {@link #userWhenWithMockCustomUserThenOk()}.
     */
    @Test
    @WithUserDetails("user@example.com")
    void userWhenWithUserDetailsThenOk() throws Exception {
        // @formatter:off
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
        // @formatter:on
    }

    /**
     * WithUser is annotated with WithUserDetails to create a concrete persona for our
     * testing. It is a little extra code, but makes it less error prone.
     */
    @Test
    @WithUser
    void userWhenWithUserThenOk() throws Exception {
        // @formatter:off
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
        // @formatter:on
    }

    /**
     * WithMockCustomUser is a little more code then using {@link WithUserDetails}, but we
     * don't need to ensure that the
     * {@link org.springframework.security.core.userdetails.UserDetails} is defined. The
     * {@link CustomUser} with email "admin@example.com" is not setup, but we can still
     * use it for testing here.
     */
    @Test
    @WithMockCustomUser(email = "admin@example.com")
    void userWhenWithMockCustomUserThenOk() throws Exception {
        // @formatter:off
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("admin@example.com")));
        // @formatter:on
    }

    /**
     * {@link WithMockCustomAdmin} is annotated with {@link WithMockCustomUser} to create
     * a concrete persona for our testing. This is a little extra code, but it is less
     * error prone.
     */
    @Test
    @WithMockCustomUser(email = "admin@example.com")
    void userWhenWithMockCustomAdminThenOk() throws Exception {
        // @formatter:off
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("admin@example.com")));
        // @formatter:on
    }

}
