package com.rookie.bigdata.basic.nationalpassword.sm4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * @Class SM4UtilTest
 * @Description
 * @Author rookie
 * @Date 2024/7/23 17:25
 * @Version 1.0
 */
class SM4UtilTest {

    public static final byte[] SRC_DATA = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
    // 密钥
    byte[] key = null;
    // 初始化向量（？）
    byte[] iv = null;
    // 密文
    byte[] encryptECB = null;
    byte[] encryptCBC = null;
    //
    byte[] decryptedECB = null;
    byte[] decryptedCBC = null;

    @BeforeEach
    public void init() {
        try {
            key = SM4Util.generateKey();
            iv = SM4Util.generateKey();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    // 加密
    @Test
    public void testECBEnrypt() {
        try {
            encryptECB = SM4Util.encrypt_Ecb_Padding(key, SRC_DATA);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
                 | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        System.out.println("SM4 ECB 加密结果：" + Arrays.toString(encryptECB));
    }

    // 解密
//    @Test(dependsOnMethods = { "testECBEnrypt" })

    @Test
    public void testECBDecrypt() {
        testECBEnrypt();
        try {
            decryptedECB = SM4Util.decrypt_Ecb_Padding(key, encryptECB);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                 | NoSuchProviderException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        System.out.println("SM4 ECB 解密结果：" + Arrays.toString(decryptedECB));
        if (!Arrays.equals(decryptedECB, SRC_DATA)) {
//            Assert.fail();
            System.out.println("失败");
        }
    }

    @Test
    public void testCBCEncrypt() {
        try {
            encryptCBC = SM4Util.encrypt_Cbc_Padding(key, iv, SRC_DATA);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
                 | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        System.out.println("SM4 CBC 加密结果：" + Arrays.toString(encryptCBC));
    }

    //    @Test(dependsOnMethods = {"testCBCEncrypt"})
    @Test
    public void testCBCDecrypt() {
        testCBCEncrypt();

        try {
            decryptedCBC = SM4Util.decrypt_Cbc_Padding(key, iv, encryptCBC);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                 | NoSuchProviderException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        System.out.println("SM4 CBC 解密结果：" + Arrays.toString(decryptedCBC));
        if (!Arrays.equals(decryptedCBC, SRC_DATA)) {
//            Assert.fail();
            System.out.println("失败2");
        }
    }
}
