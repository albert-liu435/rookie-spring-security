package com.rookie.bigdata;

/**
 * @Class CustomUserRepository
 * @Description
 * @Author rookie
 * @Date 2024/6/26 17:22
 * @Version 1.0
 */
public interface CustomUserRepository {

    CustomUser findCustomUserByEmail(String email);

}
