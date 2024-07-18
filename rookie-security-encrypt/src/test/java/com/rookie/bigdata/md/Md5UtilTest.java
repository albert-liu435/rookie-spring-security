package com.rookie.bigdata.md;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class Md5UtilTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131280324
 * @Author rookie
 * @Date 2024/7/18 17:34
 * @Version 1.0
 */
class Md5UtilTest {

    @Test
    void encodeMD2() throws Exception{
        byte[] bytes = Md5Util.encodeMD2("hello".getBytes(StandardCharsets.UTF_8));

        String s = Hex.encodeHexString(bytes);
        System.out.println(s);
//        System.out.println(new String(bytes,StandardCharsets.UTF_8));

    }



    @Test
    void encodeMD5() throws Exception{
        byte[] bytes = Md5Util.encodeMD5("hello".getBytes(StandardCharsets.UTF_8));
//        System.out.println(new String(bytes,StandardCharsets.UTF_8));
        String s = Hex.encodeHexString(bytes);
        System.out.println(s);
    }
}
