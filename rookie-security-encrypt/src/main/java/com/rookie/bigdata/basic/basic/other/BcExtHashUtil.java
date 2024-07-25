package com.rookie.bigdata.basic.basic.other;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.Security;

/**
 * @Class BcExtHashUtil
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:44
 * @Version 1.0
 */
public class BcExtHashUtil {
    public static final String RipeMD128 = "RipeMD128";
    public static final String RipeMD160 = "RipeMD160";
    public static final String RipeMD256 = "RipeMD256";
    public static final String RipeMD320 = "RipeMD320";

    public static final String Tiger = "Tiger";
    public static final String Whirlpool = "Whirlpool";
    public static final String Gost3411 = "Gost3411";


    /**
     * RipeMD消息摘要
     *
     * @param data 待处理的消息摘要数据
     * @return byte[] 消息摘要
     */
    public static byte[] encodeExtHash(String algorithm, byte[] data) throws Exception {
        // 加入BouncyCastleProvider的支持
        Security.addProvider(new BouncyCastleProvider());
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance(algorithm);
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * RipeMDHex消息摘要
     *
     * @param data 待处理的消息摘要数据
     * @return String 消息摘要
     **/
    public static String encodeExtHashHex(String algorithm, byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = encodeRipeMD(algorithm, data);
        // 做十六进制的编码处理
        return new String(Hex.encodeHex(b));
    }

    /**
     * RipeMD消息摘要
     *
     * @param data 待处理的消息摘要数据
     * @return byte[] 消息摘要
     */
    public static byte[] encodeRipeMD(String algorithm, byte[] data) throws Exception {
        // 加入BouncyCastleProvider的支持
        Security.addProvider(new BouncyCastleProvider());
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance(algorithm);
        // 执行消息摘要
        return md.digest(data);
    }

}
