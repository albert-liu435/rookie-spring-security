package com.rookie.bigdata.basic.basic.base64;

import com.rookie.bigdata.basic.basic.base64.Base64Utils;
import org.junit.jupiter.api.Test;

/**
 * @Class Base64UtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/7/23 10:53
 * @Version 1.0
 */
class Base64UtilsTest {

    @Test
    void encode() {

        String hello = Base64Utils.encode("hello", "UTF-8");
        System.out.println(hello);
    }

    @Test
    void decode() {
    }
}