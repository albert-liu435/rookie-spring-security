package com.rookie.bigdata.basic.basic.md5andsha;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.Security;

/**
 * @Class BcMD5AndShaCoder
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:14
 * @Version 1.0
 */
public class BcMD5AndShaCoder {
    public static final String MD2 = "MD2";
    public static final String MD3 = "MD3";
    public static final String MD4 = "MD4";
    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA-1";
    public static final String SHA224 = "SHA-224";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";

    public static String msgSafeBase(String msg, String algorithmName) throws Exception {

        // 注册BouncyCastle:
        Security.addProvider(new BouncyCastleProvider());

        MessageDigest m = MessageDigest.getInstance(algorithmName);
        m.update(msg.getBytes("UTF8"));
        byte s[] = m.digest();
        return Hex.encodeHexString(s);
    }

}
