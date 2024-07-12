package com.rookie.bigdata.security;

/**
 * @Class CustomUserRepository
 * @Description
 * @Author rookie
 * @Date 2024/7/12 17:46
 * @Version 1.0
 */
public interface CustomUserRepository {

    CustomUser findCustomUserByEmail(String email);

}
