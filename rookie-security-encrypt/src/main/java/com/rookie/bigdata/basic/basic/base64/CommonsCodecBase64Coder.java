package com.rookie.bigdata.basic.basic.base64;


import org.apache.commons.codec.binary.Base64;

/**
 * @Class CommonsCodecBase64Coder
 * @Description
 * @Author rookie
 * @Date 2024/7/18 18:01
 * @Version 1.0
 */
public class CommonsCodecBase64Coder {
    public final static String ENCODING = "UTF-8";

    /**
     * Base64编码
     *
     * @param data 待编码数据
     * @return String 编码数据
     * @throws Exception
     */
    public static String encode(String data) throws Exception {
        // 执行编码
        byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
        return new String(b, ENCODING);
    }

    /**
     * Base64安全编码<br>
     * 遵循RFC 2045实现
     *
     * @param data 待编码数据
     * @return String 编码数据
     *
     * @throws Exception
     */
    public static String encodeSafe(String data) throws Exception {
        // 执行编码
        byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);
        return new String(b, ENCODING);
    }

    /**
     * Base64解码
     *
     * @param data 待解码数据
     * @return String 解码数据
     * @throws Exception
     */
    public static String decode(String data) throws Exception {
        // 执行解码
        byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
        return new String(b, ENCODING);
    }
}

