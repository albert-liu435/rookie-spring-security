package com.rookie.bigdata.basic.mac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class BouncyCastleMACCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/22 16:30
 * @Version 1.0
 */
class BouncyCastleMACCoderTest {

    @Test
    public final void testEncodeHmacMD2() throws Exception {
        String str = "HmacMD2消息摘要";

        // 初始化密钥
        byte[] key = BouncyCastleMACCoder.initHmacMD2Key();

        // 获得摘要信息
        byte[] data1 = BouncyCastleMACCoder.encodeHmacMD2(str.getBytes(), key);
        byte[] data2 = BouncyCastleMACCoder.encodeHmacMD2(str.getBytes(), key);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacMD2Hex
     *
     * @throws Exception
     */
    @Test
    public final void testEncodeHmacMD2Hex() throws Exception {
        String str = "HmacMD2Hex消息摘要";

        // 初始化密钥
        byte[] key = BouncyCastleMACCoder.initHmacMD2Key();

        // 获得摘要信息
        String data1 = BouncyCastleMACCoder.encodeHmacMD2Hex(str.getBytes(), key);
        String data2 = BouncyCastleMACCoder.encodeHmacMD2Hex(str.getBytes(), key);

        System.err.println("原文：\t" + str);
        System.err.println("HmacMD2Hex-1：\t" + data1);
        System.err.println("HmacMD2Hex-2：\t" + data2);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacMD4
     *
     * @throws Exception
     */
    @Test
    public final void testEncodeHmacMD4() throws Exception {
        String str = "HmacMD4消息摘要";

        // 初始化密钥
        byte[] key = BouncyCastleMACCoder.initHmacMD4Key();

        // 获得摘要信息
        byte[] data1 = BouncyCastleMACCoder.encodeHmacMD4(str.getBytes(), key);
        byte[] data2 = BouncyCastleMACCoder.encodeHmacMD4(str.getBytes(), key);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacMD4Hex
     *
     * @throws Exception
     */
    @Test
    public final void testEncodeHmacMD4Hex() throws Exception {
        String str = "HmacMD4Hex消息摘要";

        // 初始化密钥
        byte[] key = BouncyCastleMACCoder.initHmacMD4Key();

        // 获得摘要信息
        String data1 = BouncyCastleMACCoder.encodeHmacMD4Hex(str.getBytes(), key);
        String data2 = BouncyCastleMACCoder.encodeHmacMD4Hex(str.getBytes(), key);

        System.err.println("原文：\t" + str);
        System.err.println("HmacMD4Hex-1：\t" + data1);
        System.err.println("HmacMD4Hex-2：\t" + data2);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacSHA224
     *
     * @throws Exception
     */
    @Test
    public final void testEncodeHmacSHA224() throws Exception {
        String str = "HmacSHA224消息摘要";

        // 初始化密钥
        byte[] key = BouncyCastleMACCoder.initHmacSHA224Key();

        // 获得摘要信息
        byte[] data1 = BouncyCastleMACCoder.encodeHmacSHA224(str.getBytes(), key);
        byte[] data2 = BouncyCastleMACCoder.encodeHmacSHA224(str.getBytes(), key);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacSHA224Hex
     *
     * @throws Exception
     */
    @Test
    public final void testEncodeHmacSHA224Hex() throws Exception {
        String str = "HmacSHA224Hex消息摘要";

        // 初始化密钥
        byte[] key = BouncyCastleMACCoder.initHmacSHA224Key();

        // 获得摘要信息
        String data1 = BouncyCastleMACCoder.encodeHmacSHA224Hex(str.getBytes(), key);
        String data2 = BouncyCastleMACCoder.encodeHmacSHA224Hex(str.getBytes(), key);

        System.err.println("原文：\t" + str);
        System.err.println("HmacSHA224Hex-1：\t" + data1);
        System.err.println("HmacSHA224Hex-2：\t" + data2);

        // 校验
        assertEquals(data1, data2);
    }
}
