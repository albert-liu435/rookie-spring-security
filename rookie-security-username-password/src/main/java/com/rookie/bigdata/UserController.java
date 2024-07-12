package com.rookie.bigdata;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class UserController
 * @Description
 * @Author rookie
 * @Date 2024/6/26 17:22
 * @Version 1.0
 */
@RestController
public class UserController {

    /**
     * 请求 http://localhost:8888/user
     * basic auth
     * user@example.com:password
     * @param currentUser
     * @return
     */
    @GetMapping("/user")
    public CustomUser user(@CurrentUser CustomUser currentUser) {
        return currentUser;
    }

}
