package com.rookie.bigdata.basic.basic.md;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Class CommonsMD5Coder
 * @Description
 * @Author rookie
 * @Date 2024/7/19 13:41
 * @Version 1.0
 */
public class CommonsMD5Coder {


    public static byte[] encodeMD5(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.md5(data);
    }

    public static String encodeMD5Hex(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.md5Hex(data);
    }
}
