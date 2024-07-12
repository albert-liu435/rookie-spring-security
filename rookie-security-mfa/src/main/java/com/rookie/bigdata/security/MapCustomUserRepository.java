package com.rookie.bigdata.security;

import java.util.Map;

/**
 * @Class MapCustomUserRepository
 * @Description
 * @Author rookie
 * @Date 2024/7/12 17:45
 * @Version 1.0
 */
public class MapCustomUserRepository implements CustomUserRepository {

    private final Map<String, CustomUser> emailToCustomUser;

    public MapCustomUserRepository(Map<String, CustomUser> emailToCustomUser) {
        this.emailToCustomUser = emailToCustomUser;
    }

    @Override
    public CustomUser findCustomUserByEmail(String email) {
        return this.emailToCustomUser.get(email);
    }

}
