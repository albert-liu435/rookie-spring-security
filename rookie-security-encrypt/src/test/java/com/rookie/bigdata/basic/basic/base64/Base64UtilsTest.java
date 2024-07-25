package com.rookie.bigdata.basic.basic.base64;

import com.rookie.bigdata.basic.basic.base64.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Class Base64UtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/7/23 10:53
 * @Version 1.0
 */
@Slf4j
class Base64UtilsTest {

    @Test
    void encode() {

        String hello = Base64Utils.encode("hello", "UTF-8");
//        System.out.println(hello);
        log.info("base64:{}", hello);

        String man = Base64Utils.encode("Man", "UTF-8");
//        System.out.println(man);
        log.info("base64:{}", man);
    }

    @Test
    void decode() {

        String hello = Base64Utils.encode("hello", "UTF-8");
//        System.out.println(hello);
        log.info("base64:{}", hello);
        String base = Base64Utils.decode(hello, "UTF-8");
//        System.out.println(base);
        log.info("base64:{}", base);
    }
}
