package com.rookie.bigdata.basic.basic.ripemd;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

/**
 * @Class HmacRipeMDCoder
 * @Description HmacRipeMD128, HmacRipeMD160
 * @Author rookie
 * @Date 2024/7/25 11:34
 * @Version 1.0
 */
public class HmacRipeMDCoder {
    public static final String HmacRipeMD128 = "HmacRipeMD128";
    public static final String HmacRipeMD160 = "HmacRipeMD160";

    /**
     * 初始化HmacRipeMD128
     *
     * @param algorithm
     * @return 密钥
     * @throws NoSuchAlgorithmException
     */
    public static String initHmacRipeMDKey(String algorithm) throws NoSuchAlgorithmException {
        // 加入BouncyCastleProvider的支持
        Security.addProvider(new BouncyCastleProvider());
        //初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        //产生密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获取密钥
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * HamcRipeMD128消息摘要
     *
     * @param algorithm
     * @param key
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] encodeHmacRipeMD(String algorithm, String key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        // 加入BouncyCastleProvider的支持
        Security.addProvider(new BouncyCastleProvider());
        //还原密钥,因为密钥是以byte形式为消息传递算法所拥有
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), algorithm);
        //实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        //初始化Mac
        mac.init(secretKey);
        //执行消息摘要处理
        return mac.doFinal(data);
    }

    /**
     * HmacRipeMDHex消息摘要
     *
     * @param algorithm
     * @param key       密钥
     * @param data      待做消息摘要处理的数据
     * @return 消息摘要
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String encodeHmacRipeMDHex(String algorithm, String key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        //执行消息摘要
        byte[] bytes = HmacRipeMDCoder.encodeHmacRipeMD(algorithm, key, data);
        //做16进制转换
        return new String(Hex.encodeHex(bytes));
    }

}
