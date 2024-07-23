package com.rookie.bigdata.basic.symmetric.des;

import com.rookie.bigdata.basic.symmetric.des.DESedeCoder;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class DESedeCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/22 16:40
 * @Version 1.0
 */
class DESedeCoderTest {
    @Test
    public final void test() throws Exception {
        String inputStr = "DESede";
        byte[] inputData = inputStr.getBytes();
        System.err.println("原文:\t" + inputStr);

        // 初始化密钥
        byte[] key = DESedeCoder.initKey();
        System.err.println("密钥:\t" + Base64.encodeBase64String(key));

        // 加密
        inputData = DESedeCoder.encrypt(inputData, key);
        System.err.println("加密后:\t" + Base64.encodeBase64String(inputData));

        // 解密
        byte[] outputData = DESedeCoder.decrypt(inputData, key);

        String outputStr = new String(outputData);
        System.err.println("解密后:\t" + outputStr);

        // 校验
        assertEquals(inputStr, outputStr);
    }
}
