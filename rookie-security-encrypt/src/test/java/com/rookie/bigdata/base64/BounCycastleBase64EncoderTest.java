package com.rookie.bigdata.base64;


import com.rookie.bigdata.basic.base64.BounCycastleBase64Encoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class BounCycastleBase64EncoderTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131302314
 * @Author rookie
 * @Date 2024/7/18 17:59
 * @Version 1.0
 */
class BounCycastleBase64EncoderTest {

    @Test
    void test() throws Exception {
        String inputStr = "Java加密与解密";
        // 进行Base64编码
        String code = BounCycastleBase64Encoder.encode(inputStr);
        System.err.println("编码后:\t" + code);
        // 进行Base64解码
        String outputStr = BounCycastleBase64Encoder.decode(code);
        System.err.println("解码后:\t" + outputStr);
        // 验证Base64编码解码一致性
        assertEquals(inputStr, outputStr);

    }

    @Test
    void demo() throws Exception {
        String str = "Base64 编码";
        // Base64编码
        String data = BounCycastleBase64Encoder.encode(str);
        System.err.println("编码后:\t" + new String(data));

        // Base64解码
        String output = BounCycastleBase64Encoder.decode(data);
        System.err.println("解码后:\t" + new String(output));

    }

    @Test
    final void demo2() throws Exception {
        String str = "Base64 编码";
        // Url Base64编码
        String data = BounCycastleBase64Encoder.urlEncode(str);
        System.err.println("编码后:\t" + new String(data));

        // Url Base64解码
        String output = BounCycastleBase64Encoder.urlDecode(data);
        System.err.println("解码后:\t" + new String(output));
    }
}
