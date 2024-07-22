package com.rookie.bigdata.aes;

import com.rookie.bigdata.basic.aes.AesEcbBytesEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @Class AesEcbBytesEncryptorTest
 * @Description https://blog.csdn.net/m0_63216218/article/details/131774375
 * https://www.cnblogs.com/chen-lhx/p/5817161.html
 * @Author rookie
 * @Date 2024/7/18 15:53
 * @Version 1.0
 */
@Slf4j
class AesEcbBytesEncryptorTest {


    // 原文:
//    String message = "天生我材必有用飞流直下三千尺";

    String message = "aaaaaaaaaaaa";

    @Test
    void encrypt() throws Exception {

        // 128位密钥 = 16 bytes Key:
        byte[] key = "1234567890abcdef".getBytes();

        // 加密:
        byte[] data = message.getBytes();
        byte[] encrypted = AesEcbBytesEncryptor.encrypt(key, data);

//        log.info("encrypted:{}",new String(encrypted, StandardCharsets.UTF_8));
//        System.out.println("Encrypted(加密内容): " + Base64.getEncoder().encodeToString(encrypted));
        log.info("Encrypted(加密内容): " + Base64.getEncoder().encodeToString(encrypted));

    }

    @Test
    void decrypt() throws Exception {
        // 128位密钥 = 16 bytes Key:
        byte[] key = "1234567890abcdef".getBytes();

        // 加密:
        byte[] data = message.getBytes();
        byte[] encrypted = AesEcbBytesEncryptor.encrypt(key, data);
        System.out.println("Encrypted(加密内容): " + Base64.getEncoder().encodeToString(encrypted));

        // 解密:
        byte[] decrypted = AesEcbBytesEncryptor.decrypt(key, encrypted);
        System.out.println("Decrypted(解密内容): " + new String(decrypted));
    }

    @Test
    void testString() throws Exception {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        //此处使用AES-128-ECB加密模式，key需要为16位。
        String key = "8749284927849201";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        // 原字符串
        String content = "hello world！";
        System.out.println(simpleDateFormat.format(new Date()) + "原字符串是：" + content);
        // 加密
        String encryptContent = AesEcbBytesEncryptor.EncryptString(content, key, secretKey);
        System.out.println(simpleDateFormat.format(new Date()) + "加密后的字符串是：" + encryptContent);
        // 解密
        String decryptContent = AesEcbBytesEncryptor.DecryptString(encryptContent, key, secretKey);
        System.out.println(simpleDateFormat.format(new Date()) + "解密后的字符串是：" + decryptContent);
    }
}
