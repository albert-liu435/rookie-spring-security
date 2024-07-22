package com.rookie.bigdata;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


/**
 * @Class MFASecurityApplicationTest
 * @Description
 * @Author rookie
 * @Date 2024/7/12 17:57
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class MfaApplicationTests {

    private static final String hexKey = "80ed266dd80bcd32564f0f4aaa8d9b149a2b1eaa";

    @Autowired
    private MockMvc mockMvc;


    @Test
    void test01(){
//        String hexSecret = "80ed266dd80bcd32564f0f4aaa8d9b149a2b1eaa";
//        String encrypted = new String(Hex.encode(encryptor.encrypt(hexSecret.getBytes())));

    }

    @Test
    void mfaWhenAllFactorsSucceedMatchesThenWorks() throws Exception {
        // @formatter:off
        MvcResult result = this.mockMvc.perform(formLogin()
                        .user("user@example.com")
                        .password("password"))
                .andExpect(redirectedUrl("/second-factor"))
                .andReturn();

        HttpSession session = result.getRequest().getSession();

        Integer code = TimeBasedOneTimePasswordUtil.generateCurrentNumberHex(hexKey);
        this.mockMvc.perform(post("/second-factor")
                        .session((MockHttpSession) session)
                        .param("code", String.valueOf(code))
                        .with(csrf()))
                .andExpect(redirectedUrl("/third-factor"));

        this.mockMvc.perform(post("/third-factor")
                        .session((MockHttpSession) session)
                        .param("answer", "smith")
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));
        // @formatter:on
    }

    @Test
    void mfaWhenBadCredsThenStillRequestsRemainingFactorsAndRedirects() throws Exception {
        // @formatter:off
        MvcResult result = this.mockMvc.perform(formLogin()
                        .user("user@example.com")
                        .password("wrongpassword"))
                .andExpect(redirectedUrl("/second-factor"))
                .andReturn();

        HttpSession session = result.getRequest().getSession();

        Integer code = TimeBasedOneTimePasswordUtil.generateCurrentNumberHex(hexKey);
        this.mockMvc.perform(post("/second-factor")
                        .session((MockHttpSession) session)
                        .param("code", String.valueOf(code))
                        .with(csrf()))
                .andExpect(redirectedUrl("/third-factor"));

        this.mockMvc.perform(post("/third-factor")
                        .session((MockHttpSession) session)
                        .param("answer", "smith")
                        .with(csrf()))
                .andExpect(redirectedUrl("/login?error"));
        // @formatter:on
    }

    @Test
    void mfaWhenWrongCodeThenRedirects() throws Exception {
        // @formatter:off
        MvcResult result = this.mockMvc.perform(formLogin()
                        .user("user@example.com")
                        .password("password"))
                .andExpect(redirectedUrl("/second-factor"))
                .andReturn();

        HttpSession session = result.getRequest().getSession();

        Integer code = TimeBasedOneTimePasswordUtil.generateCurrentNumberHex(hexKey) - 1;
        this.mockMvc.perform(post("/second-factor")
                        .session((MockHttpSession) session)
                        .param("code", String.valueOf(code))
                        .with(csrf()))
                .andExpect(redirectedUrl("/third-factor"));

        this.mockMvc.perform(post("/third-factor")
                        .session((MockHttpSession) session)
                        .param("answer", "smith")
                        .with(csrf()))
                .andExpect(redirectedUrl("/login?error"));
        // @formatter:on
    }

    @Test
    void mfaWhenWrongSecurityAnswerThenRedirects() throws Exception {
        // @formatter:off
        MvcResult result = this.mockMvc.perform(formLogin()
                        .user("user@example.com")
                        .password("password"))
                .andExpect(redirectedUrl("/second-factor"))
                .andReturn();

        HttpSession session = result.getRequest().getSession();

        Integer code = TimeBasedOneTimePasswordUtil.generateCurrentNumberHex(hexKey);
        this.mockMvc.perform(post("/second-factor")
                        .session((MockHttpSession) session)
                        .param("code", String.valueOf(code))
                        .with(csrf()))
                .andExpect(redirectedUrl("/third-factor"));

        this.mockMvc.perform(post("/third-factor")
                        .session((MockHttpSession) session)
                        .param("answer", "wilson")
                        .with(csrf()))
                .andExpect(redirectedUrl("/login?error"));
        // @formatter:on
    }

    @Test
    void mfaWhenInProcessThenCantViewOtherPages() throws Exception {
        // @formatter:off
        MvcResult result = this.mockMvc.perform(formLogin()
                        .user("user@example.com")
                        .password("password"))
                .andExpect(redirectedUrl("/second-factor"))
                .andReturn();

        HttpSession session = result.getRequest().getSession();

        this.mockMvc.perform(get("/")
                        .session((MockHttpSession) session))
                .andExpect(redirectedUrl("http://localhost/login"));

        result = this.mockMvc.perform(formLogin()
                        .user("user@example.com")
                        .password("password"))
                .andExpect(redirectedUrl("/second-factor"))
                .andReturn();

        session = result.getRequest().getSession();

        Integer code = TimeBasedOneTimePasswordUtil.generateCurrentNumberHex(hexKey);
        this.mockMvc.perform(post("/second-factor")
                        .session((MockHttpSession) session)
                        .param("code", String.valueOf(code))
                        .with(csrf()))
                .andExpect(redirectedUrl("/third-factor"));

        this.mockMvc.perform(get("/")
                        .session((MockHttpSession) session))
                .andExpect(redirectedUrl("http://localhost/login"));
        // @formatter:on
    }

}
