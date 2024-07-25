package com.rookie.bigdata.basic.asymmetric.ecc;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class JdkEccCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 10:56
 * @Version 1.0
 */
class JdkEccCoderTest {

    @Test
    void test01()throws Exception{
        String msg="我是测试数据";
        KeyPair keyPair = JdkEccCoder.initKeyPair("", 256);
        System.out.println("pub:"+ Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        System.out.println("pri:"+ Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
//        com.sun.crypto.provider.AESCipher
        byte[] encrypt = JdkEccCoder.encrypt(msg.getBytes(), keyPair.getPublic());
        System.out.println("encrypt DAta:"+ Base64.getEncoder().encodeToString(encrypt));
//
        byte[] decrypt = JdkEccCoder.decrypt(encrypt, keyPair.getPrivate());
        System.out.println("txt DAta:"+ new String(decrypt));
    }

}
