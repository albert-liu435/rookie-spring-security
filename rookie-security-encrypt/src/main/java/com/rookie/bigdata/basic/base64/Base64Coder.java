package com.rookie.bigdata.basic.base64;

import java.util.Base64;

/**
 * @Class Base64Coder
 * @Description
 * 　　规则：①.把3个字节变成4个字节。②每76个字符加一个换行符。③.最后的结束符也要处理。
 * 中文有多种编码（比如：utf-8、gb2312、gbk等），不同编码对应Base64编码结果都不一样。
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

    public static String encode(String data, String charsetName) throws Exception {
        //默认 ISO-8859-1
        return Base64.getEncoder().encodeToString(data.getBytes(charsetName));
    }

    public static String decode(String data, String charsetName) throws Exception {
        return new String(Base64.getDecoder().decode(data), charsetName);
    }

}
