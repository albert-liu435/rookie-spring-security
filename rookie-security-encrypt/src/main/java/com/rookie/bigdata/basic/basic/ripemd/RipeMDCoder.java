package com.rookie.bigdata.basic.basic.ripemd;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * @Class RipeMDCoder
 * @Description RipeMD128, RipeMD160, RipeMD256, RipeMD320
 * @Author rookie
 * @Date 2024/7/25 11:25
 * @Version 1.0
 */
public class RipeMDCoder {

    public static final String RipeMD128 = "RipeMD128";
    public static final String RipeMD160 = "RipeMD160";
    public static final String RipeMD256 = "RipeMD256";
    public static final String RipeMD320 = "RipeMD320";


    /**
     * RipeMD消息摘掉
     *
     * @param algorithm
     * @param data      待处理的消息摘要数据
     * @return 消息摘要
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encodeRipeMD(String algorithm, byte[] data) throws NoSuchAlgorithmException {
        //加入BouncyCastleProvider的支持
        Security.addProvider(new BouncyCastleProvider());
        //初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance(algorithm);
        //执行消息摘要
        return md.digest(data);
    }

    /**
     * RipeMDHex消息摘要
     *
     * @param algorithm
     * @param data      待处理的消息摘要数据
     * @return 消息摘要
     * @throws NoSuchAlgorithmException
     */
    public static String encodeRipeMDHex(String algorithm, byte[] data) throws NoSuchAlgorithmException {
        //执行消息摘要
        byte[] bytes = RipeMDCoder.encodeRipeMD(algorithm, data);
        //做16进制的编码处理
        return new String(Hex.encodeHex(bytes));
    }
}































