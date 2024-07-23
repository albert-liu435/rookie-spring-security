package com.rookie.bigdata.basic.basic.md;

import java.security.MessageDigest;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
/**
 * @Class MD4Coder
 * @Description
 * @Author rookie
 * @Date 2024/7/19 13:36
 * @Version 1.0
 */
public class MD4Coder {
    public static byte[] encodeMD4(byte[] data) throws Exception {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD4");

        // 执行消息摘要
        return md.digest(data);
    }

    public static String encodeMD4Hex(byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = encodeMD4(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }
}

