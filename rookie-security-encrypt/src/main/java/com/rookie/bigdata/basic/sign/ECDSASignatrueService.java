package com.rookie.bigdata.basic.sign;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

/**
 * @Class ECDSASignatrueService
 * @Description
 * @Author rookie
 * @Date 2024/7/23 16:54
 * @Version 1.0
 */
public class ECDSASignatrueService {
    private Map<String, Object> key = null;
    private String PRIVATEKEY = "privatekey";
    private String PUBLICKEY = "publickey";
    private String ECAlGORIYTHMS = "EC";
    // ECDSA: 椭圆曲线数字签名算法
    private String SIGNATUREALGORIYTHMS = "SHA1withECDSA";

    public Map<String, Object> getKey() {
        return key;
    }

    /**
     * 通过构造函数生成密钥对
     */
    private ECDSASignatrueService() {
        key = new HashMap<String, Object>();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ECAlGORIYTHMS);
            SecureRandom secrand = new SecureRandom();
            secrand.setSeed("www.okcard.com".getBytes()); // 初始化随机产生器
            keyPairGenerator.initialize(256, secrand);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            key.put(PUBLICKEY, ecPublicKey);
            key.put(PRIVATEKEY, ecPrivateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 针对需要签名的字符进行签名
     *
     * @param message
     * @return
     */
    public byte[] doSignature(String message) {
        byte[] result = null;
        ECPrivateKey ecPrivateKey = (ECPrivateKey) key.get(PRIVATEKEY);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ECAlGORIYTHMS);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);


            Signature signature = Signature.getInstance(SIGNATUREALGORIYTHMS);
            signature.initSign(privateKey);
            signature.update(message.getBytes());
            result = signature.sign();


            System.out.println("jdk 加密结果 : " + Hex.encodeHexString(result));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 针对签名的字符使用公钥进行验证
     *
     * @param message
     * @param signatureMessage
     * @param eCPublicKey
     * @return
     */
    public boolean verify(String message, byte[] signatureMessage, ECPublicKey eCPublicKey) {
        boolean flag = true;
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(eCPublicKey.getEncoded());

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ECAlGORIYTHMS);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Signature signature = Signature.getInstance(SIGNATUREALGORIYTHMS);
            signature.initVerify(publicKey);
            signature.update(message.getBytes());
            flag = signature.verify(signatureMessage);
            System.out.println("jdk 签名验证结果 : " + flag);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return flag;
    }

    public static void main(String[] args) {
        ECDSASignatrueService eCDSASignatrueService = new ECDSASignatrueService();
        String message = "test a signatrue string";
        eCDSASignatrueService.verify(message, eCDSASignatrueService.doSignature(message),
                (ECPublicKey) eCDSASignatrueService.getKey().get(eCDSASignatrueService.PUBLICKEY));
    }
}
