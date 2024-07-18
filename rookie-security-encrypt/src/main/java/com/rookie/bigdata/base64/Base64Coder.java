package com.rookie.bigdata.base64;

import java.util.Base64;

/**
 * @Class Base64Coder
 * @Description
 * @Author rookie
 * @Date 2024/7/18 17:50
 * @Version 1.0
 */
public class Base64Coder {
    /**
     * 字符编码
     */
    public final static String ENCODING = "UTF-8";

    /**
     * Base64编码
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encode(String data) throws Exception {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] b = data.getBytes(ENCODING);
        return encoder.encodeToString(b);
    }

    /**
     * Base64解码
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decode(String data) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] b = decoder.decode(data);
        return new String(b, ENCODING);
    }
}
