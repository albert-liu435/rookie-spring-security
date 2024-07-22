package com.rookie.bigdata.aes;

import com.rookie.bigdata.basic.aes.AesCBCBytesEncryptor;
import org.junit.jupiter.api.Test;

import java.util.Base64;

/**
 * @Class AesCBCBytesEncryptorTest
 * @Description https://blog.csdn.net/m0_63216218/article/details/131774375
 * @Author rookie
 * @Date 2024/7/18 16:10
 * @Version 1.0
 */
class AesCBCBytesEncryptorTest {

    @Test
    void encrypt() throws Exception {
        // 原文:
        String message = "我从山中来带着兰花草";
        System.out.println("Message(原始信息): " + message);

        // 256位密钥 = 32 bytes Key:
        byte[] key = "asdfgh012394lj7g89jigdrsv8354kbg".getBytes();

        // 加密:
        byte[] data = message.getBytes();
        byte[] encrypted = AesCBCBytesEncryptor.encrypt(key, data);
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(encrypted));
    }

    @Test
    void decrypt() throws Exception{
        // 原文:
        String message = "我从山中来带着兰花草";
        System.out.println("Message(原始信息): " + message);

        // 256位密钥 = 32 bytes Key:
        byte[] key = "asdfgh012394lj7g89jigdrsv8354kbg".getBytes();

        // 加密:
        byte[] data = message.getBytes();
        byte[] encrypted = AesCBCBytesEncryptor.encrypt(key, data);
        System.out.println("Encrypted(加密内容): " +
                Base64.getEncoder().encodeToString(encrypted));

        // 解密:
        byte[] decrypted = AesCBCBytesEncryptor.decrypt(key, encrypted);
        System.out.println("Decrypted(解密内容): " + new String(decrypted));
    }
}
