package com.rookie.bigdata.basic.asymmetric.dsa;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Map;

/**
 * @Class JdkDsa
 * @Description
 * @Author rookie
 * @Date 2024/7/25 15:47
 * @Version 1.0
 */
public class JdkDsa {
    public static final String DSA = "DSA";
    public static final String SHA1withDSA = "SHA1withDSA";
    public static final String SHA224withDSA = "SHA224withDSA";
    public static final String SHA256withDSA = "SHA256withDSA";
    public static final String SHA384withDSA = "SHA384withDSA";
    public static final String SHA512withDSA = "SHA512withDSA";

    /**
     * 默认密钥字节数
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 默认种子
     */
    private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param signAlgorithm
     * @param priKeyBytes   私钥
     * @param data          加密数据
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] sign(String signAlgorithm, byte[] priKeyBytes, byte[] data) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
        //KEY_ALGORITHM指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(DSA);
        //获取私钥对象
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();


    }


    /**
     * 校验数字签名
     *
     * @param signAlgorithm
     * @param publKeyBytes  公钥
     * @param data          加密数据
     * @param sign          数字签名
     * @return 校验成功返回true, 失败返回false
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(String signAlgorithm, byte[] publKeyBytes, byte[] data, byte[] sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publKeyBytes);
        // ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(DSA);
        //取公钥对象
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(publicKey);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(sign);
    }

    /**
     * 生成密钥
     *
     * @param seed 种子
     * @return 密钥对象
     */
    public static Map.Entry<byte[], byte[]> initKey(String seed) throws NoSuchAlgorithmException {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(DSA);
        //初始化随机数生产器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes(StandardCharsets.UTF_8));
        keygen.initialize(KEY_SIZE, secureRandom);
        KeyPair keyPair = keygen.genKeyPair();
        DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();
        return new AbstractMap.SimpleEntry<>(publicKey.getEncoded(), privateKey.getEncoded());
    }

    /**
     * 默认生成密钥
     *
     * @return 密钥对象
     * @throws Exception
     */
    public static Map.Entry<byte[], byte[]> initKey() throws Exception {
        return JdkDsa.initKey(JdkDsa.DEFAULT_SEED);
    }
}
