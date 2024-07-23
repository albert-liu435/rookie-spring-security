package com.rookie.bigdata.basic.sha;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class SHACoderTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131302314
 * @Author rookie
 * @Date 2024/7/19 16:29
 * @Version 1.0
 */
class SHACoderTest {

    @Test
    void encodeSHA() throws Exception {
        String str = "SHA1消息摘要";

        // 获得摘要信息
        byte[] data1 = SHACoder.encodeSHA(str.getBytes());
        byte[] data2 = SHACoder.encodeSHA(str.getBytes());
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data1));
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data2));
        // 校验
//        assertEquals(data1, data2);
    }

    @Test
    void encodeSHA224() throws Exception {
        String str = "SHA224消息摘要";

        // 获得摘要信息
        byte[] data1 = SHACoder.encodeSHA(str.getBytes());
        byte[] data2 = SHACoder.encodeSHA(str.getBytes());
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data1));
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data2));
        // 校验
//        assertEquals(data1, data2);
    }

    @Test
    void encodeSHA256() throws Exception {

        String str = "SHA256消息摘要";

        // 获得摘要信息
        byte[] data1 = SHACoder.encodeSHA256(str.getBytes());
        byte[] data2 = SHACoder.encodeSHA256(str.getBytes());
        //将字节数组转换为16进制的字符串
        BigInteger bigInteger = new BigInteger(1, data1);
        String hashString = bigInteger.toString(16);
        System.out.println(hashString);

//        System.out.println(new String(data1, StandardCharsets.UTF_8));
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data1));
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data2));
        // 校验
//        assertEquals(data1, data2);
    }

    @Test
    void encodeSHA384() throws Exception {
        String str = "SHA384消息摘要";

        // 获得摘要信息
        byte[] data1 = SHACoder.encodeSHA384(str.getBytes());
        byte[] data2 = SHACoder.encodeSHA384(str.getBytes());
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data1));
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data2));
        // 校验
//        assertEquals(data1, data2);
    }

    @Test
    void encodeSHA512() throws Exception {
        String str = "SHA512消息摘要";

        // 获得摘要信息
        byte[] data1 = SHACoder.encodeSHA512(str.getBytes());
        byte[] data2 = SHACoder.encodeSHA512(str.getBytes());
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data1));
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data2));
        // 校验
//        assertEquals(data1, data2);
    }
}
