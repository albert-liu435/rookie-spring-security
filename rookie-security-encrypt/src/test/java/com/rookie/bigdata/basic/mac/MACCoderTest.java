package com.rookie.bigdata.basic.mac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class MACCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/22 15:35
 * @Version 1.0
 */
class MACCoderTest {

    /**
     * 测试HmacMD5
     */
    @Test
    public final void testEncodeHmacMD5() throws Exception {
        String str = "HmacMD5消息摘要";

        // 初始化密钥
        byte[] key = MACCoder.initHmacMD5Key();

        // 获得摘要信息
        byte[] data1 = MACCoder.encodeHmacMD5(str.getBytes(), key);
        byte[] data2 = MACCoder.encodeHmacMD5(str.getBytes(), key);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacSHA1
     */
    @Test
    public final void testEncodeHmacSHA() throws Exception {
        String str = "HmacSHA1消息摘要";

        // 初始化密钥
        byte[] key = MACCoder.initHmacSHAKey();

        // 获得摘要信息
        byte[] data1 = MACCoder.encodeHmacSHA(str.getBytes(), key);
        byte[] data2 = MACCoder.encodeHmacSHA(str.getBytes(), key);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacSHA256
     */
    @Test
    public final void testEncodeHmacSHA256() throws Exception {
        String str = "HmacSHA256消息摘要";

        // 初始化密钥
        byte[] key = MACCoder.initHmacSHA256Key();

        // 获得摘要信息
        byte[] data1 = MACCoder.encodeHmacSHA256(str.getBytes(), key);
        byte[] data2 = MACCoder.encodeHmacSHA256(str.getBytes(), key);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacSHA384
     */
    @Test
    public final void testEncodeHmacSHA384() throws Exception {
        String str = "HmacSHA384消息摘要";

        // 初始化密钥
        byte[] key = MACCoder.initHmacSHA384Key();

        // 获得摘要信息
        byte[] data1 = MACCoder.encodeHmacSHA384(str.getBytes(), key);
        byte[] data2 = MACCoder.encodeHmacSHA384(str.getBytes(), key);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试HmacSHA512
     */
    @Test
    public final void testEncodeHmacSHA512() throws Exception {
        String str = "HmacSHA512消息摘要";

        // 初始化密钥
        byte[] key = MACCoder.initHmacSHA512Key();

        // 获得摘要信息
        byte[] data1 = MACCoder.encodeHmacSHA512(str.getBytes(), key);
        byte[] data2 = MACCoder.encodeHmacSHA512(str.getBytes(), key);

        // 校验
        assertEquals(data1, data2);
    }

}
