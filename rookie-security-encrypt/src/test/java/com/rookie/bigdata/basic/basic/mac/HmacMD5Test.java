package com.rookie.bigdata.basic.basic.mac;

import com.rookie.bigdata.basic.basic.mac.HmacMD5;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * @Class HmacMD5Test
 * @Description https://blog.csdn.net/qq_15764943/article/details/118099518
 * @Author rookie
 * @Date 2024/7/23 14:06
 * @Version 1.0
 */
class HmacMD5Test {

    @Test
    void getHmacMd5() throws Exception{

        String hmacMd5 = HmacMD5.getHmacMd5("hello".getBytes(StandardCharsets.UTF_8), "hello".getBytes(StandardCharsets.UTF_8));
        System.out.println(hmacMd5);
    }
}
