package com.rookie.bigdata.basic.symmetric.aes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;


/**
 * @Class AesEcbBytesEncryptor
 * @Description AES+ECB
 * @Author rookie
 * @Date 2024/7/18 14:03
 * @Version 1.0
 */
public class AesEcbBytesEncryptor {


    /**
     * 加密
     *
     * @param key   加密key
     * @param input 要加密的数据
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        //创建密码对象，需要传入算法/工作模式/填充模式
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        //根据key的字节内容，"恢复"秘钥对象
        SecretKey keySpec = new SecretKeySpec(key, "AES");

        //初始化秘钥:设置加密模式ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        //根据原始内容(字节),进行加密
        return cipher.doFinal(input);

    }




        /**
         * 解密
         *
         * @param key   解密key
         * @param input 要解密的数据
         * @return
         * @throws GeneralSecurityException
         */
    public static byte[] decrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        // 创建密码对象，需要传入算法/工作模式/填充模式
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // 根据key的字节内容，"恢复"秘钥对象
        SecretKey keySpec = new SecretKeySpec(key, "AES");

        // 初始化秘钥:设置解密模式DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // 根据原始内容(字节),进行解密
        return cipher.doFinal(input);
    }




    public static String EncryptString(String content, String key,SecretKey secretKey) throws Exception {
        // 判断Key是否为16位
        if (key.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128, new SecureRandom(key.getBytes()));
//        SecretKey secretKey = keyGenerator.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat , "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
        //使用BASE64做转码功能，能起到2次加密的作用。
        return new Base64().encodeToString(encrypted);
    }

    public static String DecryptString(String content, String key, SecretKey secretKey) throws Exception {
        try {
            // 判断Key是否为16位
            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128, new SecureRandom(key.getBytes()));
//            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat , "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            //先base64解密
            byte[] encrypted1 = new Base64().decode(content);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }


}
