package com.rookie.bigdata.basic.dh;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class DHCoderTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131321538
 * https://blog.csdn.net/fengzun_yi/article/details/104497160
 * 需要设置参数： 将 -Djdk.crypto.KeyAgreement.legacyKDF=true 写入JVM系统变量中，
 * @Author rookie
 * @Date 2024/7/23 9:02
 * @Version 1.0
 */
 class DHCoderTest {
    /**
     * 甲方公钥
     */
    private byte[] publicKey_a;
    /**
     * 甲方私钥
     */
    private byte[] privateKey_a;

    /**
     * 甲方本地密钥
     */
    private byte[] key_a;

    /**
     * 乙方公钥
     */
    private byte[] publicKey_b;

    /**
     * 乙方私钥
     */
    private byte[] privateKey_b;

    /**
     * 乙方本地密钥
     */
    private byte[] key_b;

    /**
     * 初始化密钥
     *
     * @throws Exception
     */
//    @BeforeMethod
    public final void initKey() throws Exception {
        // 生成甲方密钥对
        Map<String, Object> keyMap_a = DHCoder.initKey();
        publicKey_a = DHCoder.getPublicKey(keyMap_a);
        privateKey_a = DHCoder.getPrivateKey(keyMap_a);
        System.err.println("甲方公钥:\n" + Base64.encodeBase64String(publicKey_a));
        System.err.println("甲方私钥:\n" + Base64.encodeBase64String(privateKey_a));

        // 由甲方公钥产生本地密钥对
        Map<String, Object> keyMap_native = DHCoder.initKey(publicKey_a);
        publicKey_b = DHCoder.getPublicKey(keyMap_native);
        privateKey_b = DHCoder.getPrivateKey(keyMap_native);
        System.err.println("乙方公钥:\n" + Base64.encodeBase64String(publicKey_b));
        System.err.println("乙方私钥:\n" + Base64.encodeBase64String(privateKey_b));

        key_a = DHCoder.getSecretKey(publicKey_b, privateKey_a);
        System.err.println("甲方本地密钥:\n" + Base64.encodeBase64String(key_a));

        key_b = DHCoder.getSecretKey(publicKey_a, privateKey_b);
        System.err.println("乙方本地密钥:\n" + Base64.encodeBase64String(key_b));

        assertEquals(key_a, key_b);
    }

    /**
     * 校验
     *
     * @throws Exception
     */
    @Test
    public final void test() throws Exception {
        initKey();

        System.err.println("\n=====甲方向乙方发送加密数据=====");
        String input1 = "密码交换算法 ";
        System.err.println("原文: " + input1);
        System.err.println("---使用甲方本地密钥对数据加密---");

        // 使用甲方本地密钥对数据加密
        byte[] code1 = DHCoder.encrypt(input1.getBytes(), key_a);

        System.err.println("加密: " + Base64.encodeBase64String(code1));
        System.err.println("---使用乙方本地密钥对数据解密---");

        // 使用乙方本地密钥对数据解密
        byte[] decode1 = DHCoder.decrypt(code1, key_b);
        String output1 = (new String(decode1));

        System.err.println("解密: " + output1);

        assertEquals(input1, output1);

        System.err.println("\n=====乙方向甲方发送加密数据=====");
        String input2 = "DH";
        System.err.println("原文: " + input2);
        System.err.println("---使用乙方本地密钥对数据加密---");

        // 使用乙方本地密钥对数据加密
        byte[] code2 = DHCoder.encrypt(input2.getBytes(), key_b);

        System.err.println("加密: " + Base64.encodeBase64String(code2));
        System.err.println("---使用甲方本地密钥对数据解密---");

        // 使用甲方本地密钥对数据解密
        byte[] decode2 = DHCoder.decrypt(code2, key_a);
        String output2 = (new String(decode2));

        System.err.println("解密: " + output2);

        // 校验
        assertEquals(input2, output2);
    }

}
