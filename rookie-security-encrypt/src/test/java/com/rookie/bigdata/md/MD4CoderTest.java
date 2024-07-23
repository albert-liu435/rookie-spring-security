package com.rookie.bigdata.md;

import com.rookie.bigdata.basic.basic.md.MD4Coder;
import org.junit.jupiter.api.Test;

/**
 * @Class MD4CoderTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131302314
 * @Author rookie
 * @Date 2024/7/19 13:37
 * @Version 1.0
 */
class MD4CoderTest {

    @Test
    void encodeMD4() throws Exception {
        String str = "MD4消息摘要";

        // 获得摘要信息
        byte[] data1 = MD4Coder.encodeMD4(str.getBytes());
        byte[] data2 = MD4Coder.encodeMD4(str.getBytes());

        // 校验
//        assertEquals(data1, data2);
    }

    @Test
    void encodeMD4Hex() throws Exception {
        String str = "MD4Hex消息摘要";

        // 获得摘要信息
        String data1 = MD4Coder.encodeMD4Hex(str.getBytes());
        String data2 = MD4Coder.encodeMD4Hex(str.getBytes());

        System.err.println("原文：\t" + str);
        System.err.println("MD4Hex-1：\t" + data1);
        System.err.println("MD4Hex-2：\t" + data2);

        // 校验
//        assertEquals(data1, data2);
    }
}
