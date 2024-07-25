package com.rookie.bigdata.basic.asymmetric.ecc;

import java.security.*;

/**
 * @Class JdkEccCoder
 * @Description
 * @Author rookie
 * @Date 2024/7/25 10:55
 * @Version 1.0
 */
public class JdkEccCoder {
    public static KeyPair initKeyPair(String algorithm, Integer keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(keySize, new SecureRandom());

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        throw new RuntimeException("暂不支持");
    }

    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        throw new RuntimeException("暂不支持");
    }
}
