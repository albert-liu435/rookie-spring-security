package com.rookie.bigdata.basic.nationalpassword.sm2;

import com.rookie.bigdata.basic.nationalpassword.KeyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class BcSm2UtilTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 16:12
 * @Version 1.0
 */
class BcSm2UtilTest {

    private String test = "woshi测试数据。。..";

    java.security.PublicKey publicKey = null;
    java.security.PrivateKey privateKey = null;

    @BeforeEach
    public void setup() throws Exception {//生成公私钥对
        String[] keys = KeyUtils.generateSmKey();

        System.out.println("原始数据：" + test);
        System.out.println("公钥：" + new String(keys[0]));
        System.out.println();
        publicKey = KeyUtils.createPublicKey(keys[0]);

        System.out.println("私钥：" + new String(keys[1]));
        System.out.println();
        privateKey = KeyUtils.createPrivateKey(keys[1]);
    }

    @Test
    public void encrypt() throws Exception {
        byte[] encrypt = BcSm2Util.encrypt(test.getBytes(), publicKey);
        String encryptBase64Str = Base64.getEncoder().encodeToString(encrypt);
        System.out.println("加密数据：" + encryptBase64Str);

        byte[] decrypt = BcSm2Util.decrypt(encrypt, privateKey);

        System.out.println("解密数据："+new String(decrypt));

        byte[] sign = BcSm2Util.signByPrivateKey(test.getBytes(), privateKey);
        System.out.println("数据签名："+ Base64.getEncoder().encodeToString(sign));

        boolean b = BcSm2Util.verifyByPublicKey(test.getBytes(), publicKey,sign);
        System.out.println("数据验签："+ b);
    }
}
