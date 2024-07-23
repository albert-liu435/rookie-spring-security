package com.rookie.bigdata.basic.basic.base64;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.UrlBase64;
/**
 * @Class BounCycastleBase64Encoder
 * @Description
 * @Author rookie
 * @Date 2024/7/18 17:58
 * @Version 1.0
 */
public class BounCycastleBase64Encoder {
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
        byte[] b = Base64.encode(data.getBytes(ENCODING));
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
        byte[] b = Base64.decode(data.getBytes(ENCODING));
        return new String(b, ENCODING);
    }

    /**
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String urlEncode(String data) throws Exception {
        // 执行编码
        byte[] b = UrlBase64.encode(data.getBytes(ENCODING));
        return new String(b, ENCODING);
    }

    /**
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String urlDecode(String data) throws Exception {
        // 执行编码
        byte[] b = UrlBase64.encode(data.getBytes(ENCODING));
        return new String(b, ENCODING);
    }

}

