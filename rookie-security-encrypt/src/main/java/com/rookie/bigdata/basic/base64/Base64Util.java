package com.rookie.bigdata.basic.base64;


import java.util.Base64;

/**
 * @Class Base64Util
 * @Description 应用场景：电子邮件传输、网络数据传输、密钥存储、数字证书存储
 * @Author rookie
 * @Date 2024/7/18 16:39
 * @Version 1.0
 */
public class Base64Util {

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
