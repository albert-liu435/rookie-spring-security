package com.rookie.bigdata.basic.basic.md5andsha;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Class JDKMd5AndShaCoder
 * @Description
 * @Author rookie
 * @Date 2024/7/25 10:59
 * @Version 1.0
 */
public class JDKMd5AndShaCoder {

    public static final String MD2 = "MD2";
    public static final String MD3 = "MD3";
    public static final String MD4 = "MD4";
    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA-1";
    public static final String SHA224 = "SHA-224";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";

    public static String msgSafeBase(String msg,String algorithmName) throws NoSuchAlgorithmException {
        MessageDigest m=MessageDigest.getInstance(algorithmName);
        m.update(msg.getBytes(StandardCharsets.UTF_8));
        byte[] bytes=m.digest();
        return Hex.encodeHexString(bytes);
    }


}
