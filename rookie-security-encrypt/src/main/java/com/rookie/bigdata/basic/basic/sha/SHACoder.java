package com.rookie.bigdata.basic.basic.sha;

import java.security.MessageDigest;

/**
 * @Class SHACoder
 * @Description 应用场景
 * 需要报文摘要功能，且安全要求比较高的应用场景，目前很多数字签名都是使用SHA的算法
 * @Author rookie
 * @Date 2024/7/19 16:27
 * @Version 1.0
 */

public class SHACoder {
    /**
     * SHA-1加密
     */
    public static byte[] encodeSHA(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * SHA-224加密
     */
    public static byte[] encodeSHA224(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-224");

        // 执行消息摘要
        return md.digest(data);
    }


    /**
     * SHA-256加密
     */
    public static byte[] encodeSHA256(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * SHA-384加密
     */
    public static byte[] encodeSHA384(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-384");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * SHA-512加密
     */
    public static byte[] encodeSHA512(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        // 执行消息摘要
        return md.digest(data);
    }
}
