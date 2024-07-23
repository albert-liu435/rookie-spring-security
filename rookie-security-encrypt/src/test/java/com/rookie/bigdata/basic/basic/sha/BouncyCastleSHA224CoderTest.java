package com.rookie.bigdata.basic.basic.sha;

import com.rookie.bigdata.basic.basic.sha.BouncyCastleSHA224Coder;
import org.junit.jupiter.api.Test;

import java.util.Base64;

/**
 * @Class BouncyCastleSHA224CoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/22 15:25
 * @Version 1.0
 */
class BouncyCastleSHA224CoderTest {

    @Test
    void encodeSHA224() throws Exception{
        String str = "SHA224消息摘要";

        // 获得摘要信息
        byte[] data1 = BouncyCastleSHA224Coder.encodeSHA224(str.getBytes());
        byte[] data2 = BouncyCastleSHA224Coder.encodeSHA224(str.getBytes());

        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data1));
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(data2));
    }

    @Test
    void encodeSHA224Hex() throws Exception{
        String str = "SHA224Hex消息摘要";

        // 获得摘要信息
        String data1 = BouncyCastleSHA224Coder.encodeSHA224Hex(str.getBytes());
        String data2 = BouncyCastleSHA224Coder.encodeSHA224Hex(str.getBytes());

        System.err.println("原文：\t" + str);
        System.err.println("SHA224Hex-1：\t" + data1);
        System.err.println("SHA224Hex-2：\t" + data2);
    }
}
