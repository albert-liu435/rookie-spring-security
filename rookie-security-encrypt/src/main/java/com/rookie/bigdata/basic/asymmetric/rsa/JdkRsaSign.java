package com.rookie.bigdata.basic.asymmetric.rsa;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Map;

/**
 * @Class JdkRsaSign
 * @Description
 * @Author rookie
 * @Date 2024/7/25 15:29
 * @Version 1.0
 */
public class JdkRsaSign {

    /**
     * RSA签名流程：
     * <p>
     * 发送方—>构建密钥对-》公布密钥给接收方—>使用私钥对数据签名-》发送签名、数据给接收方。<br>
     * 接收方—》使用公钥、签名验证数据
     *
     *
     */
/**
 *
 * 使用rsa签名 发送方—>构建密钥对-》公布密钥给接收方。 发送方—>使用私钥对数据签名-》发送签名、数据给接收方—》接收方使用公钥、签名验证数据
 */
    /**
     * 算法 密钥长度 默认长度 签名长度 实现的方<br>
     * MD2withRSA 512-65536（64的整数倍） 1024 同密钥 JDK8<br>
     * MD5withRSA 同上 1024 同密钥 JDK8<br>
     * SHA1withRSA ... 1024 同密钥 JDK8<br>
     * SHA224withRSA ... 2048 同密钥 JDK8<br>
     * SHA256withRSA ... 2048 同密钥 JDK8<br>
     * SHA384withRSA ... 2048 同密钥 JDK8<br>
     * SHA512withRSA ... 2048 同密钥 JDK8<br>
     * RIPEMD128withRSA 2048 同密钥 BC<br>
     * RIPEMD160withRSA 同上 2048 同密钥 BC<br>
     */

    public final static String RSA = "RSA";
    public final static String MD2withRSA = "MD2withRSA";
    public final static String MD5withRSA = "MD5withRSA";
    public final static String SHA1withRSA = "SHA1withRSA";
    public final static String SHA224withRSA = "SHA224withRSA";
    public final static String SHA256withRSA = "SHA256withRSA";
    public final static String SHA384withRSA = "SHA384withRSA";
    public final static String SHA512withRSA = "SHA512withRSA";

    /**
     * 初始化密钥
     *
     * @param algorithm
     * @param keySize
     * @return
     */
    public static Map.Entry<byte[], byte[]> initKeyPair(String algorithm, Integer keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new AbstractMap.SimpleEntry<>(rsaPublicKey.getEncoded(), rsaPrivateKey.getEncoded());
    }

    /**
     * 执行签名
     *
     * @param algorithm
     * @param signAlgorithm
     * @param priKey
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] priKeySign(String algorithm, String signAlgorithm, byte[] priKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }


    /**
     * 执行签名
     *
     * @param algorithm
     * @param signAlgorithm
     * @param pubKey
     * @param data
     * @param sign
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean pubKeyCheckSign(String algorithm, String signAlgorithm, byte[] pubKey, byte[] data, byte[] sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);// 为了数据的完整性
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

}
