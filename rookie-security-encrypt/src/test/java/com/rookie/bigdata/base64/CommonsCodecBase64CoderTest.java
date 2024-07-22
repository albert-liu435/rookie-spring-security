package com.rookie.bigdata.base64;

import com.rookie.bigdata.basic.base64.CommonsCodecBase64Coder;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class CommonsCodecBase64CoderTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131302314
 * @Author rookie
 * @Date 2024/7/18 18:02
 * @Version 1.0
 */
class CommonsCodecBase64CoderTest {

    @Test
    public final void test() throws Exception {
        String inputStr = "Java加密与解密";

        // 进行Base64编码
        String code = CommonsCodecBase64Coder.encode(inputStr);
        System.err.println("编码后:\t" + code);

        // 进行Base64解码
        String outputStr = CommonsCodecBase64Coder.decode(code);
        System.err.println("解码后:\t" + outputStr);

        // 验证Base64编码解码一致性
        assertEquals(inputStr, outputStr);
    }

    @Test
    public final void testSafe() throws Exception {
        String inputStr = "Java加密与解密";

        // 进行Base64编码
        String code = CommonsCodecBase64Coder.encodeSafe(inputStr);
        System.err.println("编码后:\t" + code);

        // 进行Base64解码
        String outputStr = CommonsCodecBase64Coder.decode(code);
        System.err.println("解码后:\t" + outputStr);

        // 验证Base64编码解码一致性
        assertEquals(inputStr, outputStr);
    }

    @Test
    public final void demo() throws Exception {
        String str = "Base64 编码1";

        // Base64编码
        String data = CommonsCodecBase64Coder.encode(str);
        System.err.println("编码后:\t" + new String(data));

        // Base64解码
        String output = CommonsCodecBase64Coder.decode(data);
        System.err.println("解码后:\t" + new String(output));
    }

    @Test
    public final void demo2() throws Exception {
        String str = "Base64 编码2";
        byte[] input = str.getBytes();

        // Base64编码
        byte[] data = Base64.encodeBase64(input);
        System.err.println("编码后:\t" + new String(data));

        // Base64解码
        byte[] output = Base64.decodeBase64(data);
        System.err.println("解码后:\t" + new String(output));

    }

    @Test
    public final void demo3() throws Exception {
        String str = "Base64 编码3";

        // Base64编码
        String data = CommonsCodecBase64Coder.encodeSafe(str);
        System.err.println("编码后:\t" + new String(data));

        // Base64解码
        byte[] output = Base64.decodeBase64(data);
        System.err.println("解码后:\t" + new String(output));

    }
}

