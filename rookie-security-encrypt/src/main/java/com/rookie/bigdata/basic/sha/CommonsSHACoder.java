package com.rookie.bigdata.basic.sha;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Class CommonsSHACoder
 * @Description
 * @Author rookie
 * @Date 2024/7/22 15:31
 * @Version 1.0
 */
public class CommonsSHACoder {
    /**
     * SHA1加密
     */
    public static byte[] encodeSHA(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.sha(data);
    }

    /**
     * SHA1Hex加密
     */
    public static String encodeSHAHex(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.shaHex(data);
    }

    /**
     * SHA256加密
     */
    public static byte[] encodeSHA256(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.sha256(data);
    }

    /**
     * SHA256Hex加密
     */
    public static String encodeSHA256Hex(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.sha256Hex(data);
    }

    /**
     * SHA384加密
     */
    public static byte[] encodeSHA384(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.sha384(data);
    }

    /**
     * SHA384Hex加密
     */
    public static String encodeSHA384Hex(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.sha384Hex(data);
    }

    /**
     * SHA512Hex加密
     */
    public static byte[] encodeSHA512(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.sha512(data);
    }

    /**
     * SHA512Hex加密
     */
    public static String encodeSHA512Hex(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.sha512Hex(data);
    }
}

