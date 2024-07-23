package com.rookie.bigdata.basic.pbe;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class PBECoderTest
 * @Description TODO
 * @Author rookie
 * @Date 2024/7/23 8:52
 * @Version 1.0
 */
class PBECoderTest {

    @Test
    public void test() throws Exception {
        String inputStr = "PBE";
        System.err.println("原文：" + inputStr);
        byte[] input = inputStr.getBytes();

        String pwd = "alanchan";
        System.err.println("密码：\t" + pwd);

        // 初始化盐
        byte[] salt = PBECoder.initSalt();
        System.err.println("盐：\t" + Base64.encodeBase64String(salt));

        // 加密
        byte[] data = PBECoder.encrypt(input, pwd, salt);
        System.err.println("加密后：\t" + Base64.encodeBase64String(data));

        // 解密
        byte[] output = PBECoder.decrypt(data, pwd, salt);
        String outputStr = new String(output);
        System.err.println("解密后：\t" + outputStr);

        // 校验
        assertEquals(inputStr, outputStr);
    }
}

