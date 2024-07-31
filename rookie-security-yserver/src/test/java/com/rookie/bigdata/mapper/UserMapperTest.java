package com.rookie.bigdata.mapper;

import com.rookie.bigdata.domain.User;
import com.rookie.bigdata.domain.dto.UserRoleDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class UserMapperTest
 * @Description
 * @Author rookie
 * @Date 2024/7/30 14:54
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Slf4j
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void loadUserByUsername() {
        User user = userMapper.loadUserByUsername("admin");

        log.info("查询的user:{}", user);
    }

    @Test
    void getUserRoleDto() {
        UserRoleDto admin = userMapper.getUserRoleDto("admin");
        log.info("查询的UserRoleDto:{}", admin);
    }

}
