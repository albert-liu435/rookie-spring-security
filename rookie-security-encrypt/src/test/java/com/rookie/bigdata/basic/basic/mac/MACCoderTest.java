package com.rookie.bigdata.basic.basic.mac;

import com.rookie.bigdata.basic.basic.mac.MACCoder;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.math.BigInteger;
import java.util.Arrays;

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


    @Test
    void test001()throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");

        SecretKey key = keyGen.generateKey();
        // 打印随机生成的key:
        byte[] skey = key.getEncoded();
        System.out.println(new BigInteger(1, skey).toString(16));
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(key);
        mac.update("HelloWorld".getBytes("UTF-8"));
        byte[] result = mac.doFinal();
        System.out.println(new BigInteger(1, result).toString(16));


    }

    @Test
    void test002()throws Exception{
        byte[] hkey = new byte[] { 106, 70, -110, 125, 39, -20, 52, 56, 85, 9, -19, -72, 52, -53, 52, -45, -6, 119, -63,
                30, 20, -83, -28, 77, 98, 109, -32, -76, 121, -106, 0, -74, -107, -114, -45, 104, -104, -8, 2, 121, 6,
                97, -18, -13, -63, -30, -125, -103, -80, -46, 113, -14, 68, 32, -46, 101, -116, -104, -81, -108, 122,
                89, -106, -109 };

        SecretKey key = new SecretKeySpec(hkey, "HmacMD5");
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(key);
        mac.update("HelloWorld".getBytes("UTF-8"));
        byte[] result = mac.doFinal();
        System.out.println(Arrays.toString(result));
        System.out.println(new BigInteger(1, result).toString(16));

        // [126, 59, 37, 63, 73, 90, 111, -96, -77, 15, 82, -74, 122, -55, -67, 54]
    }

}
