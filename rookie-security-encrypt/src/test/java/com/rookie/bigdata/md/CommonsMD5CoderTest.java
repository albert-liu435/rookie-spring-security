package com.rookie.bigdata.md;

import com.rookie.bigdata.basic.md.CommonsMD5Coder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class CommonsMD5CoderTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131302314
 * @Author rookie
 * @Date 2024/7/19 13:41
 * @Version 1.0
 */
class CommonsMD5CoderTest {

    @Test
    void encodeMD5() throws Exception{
        String str = "MD5消息摘要";

        // 获得摘要信息
        byte[] data1 = CommonsMD5Coder.encodeMD5(str);
        byte[] data2 = CommonsMD5Coder.encodeMD5(str);

        // 校验
//        assertEquals(data1, data2);
    }

    @Test
    void encodeMD5Hex()throws Exception {
        String str = "MD5Hex消息摘要";

        // 获得摘要信息
        String data1 = CommonsMD5Coder.encodeMD5Hex(str);
        String data2 = CommonsMD5Coder.encodeMD5Hex(str);

        System.err.println("原文：\t" + str);
        System.err.println("MD5Hex-1：\t" + data1);
        System.err.println("MD5Hex-2：\t" + data2);

        // 校验
        assertEquals(data1, data2);
    }
}
