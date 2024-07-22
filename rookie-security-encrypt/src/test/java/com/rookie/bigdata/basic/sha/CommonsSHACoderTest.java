package com.rookie.bigdata.basic.sha;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class CommonsSHACoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/22 15:31
 * @Version 1.0
 */
class CommonsSHACoderTest {
    /**
     * 测试SHA-1
     */
    @Test
    public final void testEncodeSHA() throws Exception {
        String str = "SHA1消息摘要";

        // 获得摘要信息
        byte[] data1 = CommonsSHACoder.encodeSHA(str);
        byte[] data2 = CommonsSHACoder.encodeSHA(str);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试SHA-1Hex
     */
    @Test
    public final void testEncodeSHAHex() throws Exception {
        String str = "SHA-1Hex消息摘要";

        // 获得摘要信息
        String data1 = CommonsSHACoder.encodeSHAHex(str);
        String data2 = CommonsSHACoder.encodeSHAHex(str);

        System.err.println("原文：\t" + str);
        System.err.println("SHA1Hex-1：\t" + data1);
        System.err.println("SHA1Hex-2：\t" + data2);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试SHA-256
     */
    @Test
    public final void testEncodeSHA256() throws Exception {
        String str = "SHA256消息摘要";

        // 获得摘要信息
        byte[] data1 = CommonsSHACoder.encodeSHA256(str);
        byte[] data2 = CommonsSHACoder.encodeSHA256(str);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试SHA-256Hex
     */
    @Test
    public final void testEncodeSHA256Hex() throws Exception {
        String str = "SHA256Hex消息摘要";

        // 获得摘要信息
        String data1 = CommonsSHACoder.encodeSHA256Hex(str);
        String data2 = CommonsSHACoder.encodeSHA256Hex(str);

        System.err.println("原文：\t" + str);
        System.err.println("SHA256Hex-1：\t" + data1);
        System.err.println("SHA256Hex-2：\t" + data2);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试SHA-384
     */
    @Test
    public final void testEncodeSHA384() throws Exception {
        String str = "SHA384消息摘要";

        // 获得摘要信息
        byte[] data1 = CommonsSHACoder.encodeSHA384(str);
        byte[] data2 = CommonsSHACoder.encodeSHA384(str);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试SHA-384Hex
     */
    @Test
    public final void testEncodeSHA384Hex() throws Exception {
        String str = "SHA384Hex消息摘要";

        // 获得摘要信息
        String data1 = CommonsSHACoder.encodeSHA384Hex(str);
        String data2 = CommonsSHACoder.encodeSHA384Hex(str);

        System.err.println("原文：\t" + str);
        System.err.println("SHA384Hex-1：\t" + data1);
        System.err.println("SHA384Hex-2：\t" + data2);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试SHA-512
     */
    @Test
    public final void testEncodeSHA512() throws Exception {
        String str = "SHA512消息摘要";

        // 获得摘要信息
        byte[] data1 = CommonsSHACoder.encodeSHA512(str);
        byte[] data2 = CommonsSHACoder.encodeSHA512(str);

        // 校验
        assertEquals(data1, data2);
    }

    /**
     * 测试SHA-512Hex
     */
    @Test
    public final void testEncodeSHA512Hex() throws Exception {
        String str = "SHA512Hex消息摘要";

        // 获得摘要信息
        String data1 = CommonsSHACoder.encodeSHA512Hex(str);
        String data2 = CommonsSHACoder.encodeSHA512Hex(str);

        System.err.println("原文：\t" + str);
        System.err.println("SHA512Hex-1：\t" + data1);
        System.err.println("SHA512Hex-2：\t" + data2);

        // 校验
        assertEquals(data1, data2);
    }
}

