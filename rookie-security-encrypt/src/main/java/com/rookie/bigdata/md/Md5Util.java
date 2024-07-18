package com.rookie.bigdata.md;

import java.security.MessageDigest;

/**
 * @Class Md5Util
 * @Description
 * @Author rookie
 * @Date 2024/7/18 17:31
 * @Version 1.0
 */
public class Md5Util {


    /**
     * MD2加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encodeMD2(byte[] data) throws Exception {
        //初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD2");
        //执行消息摘要
        return md.digest(data);
    }


    public static byte[] encodeMD5(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 执行消息摘要
        return md.digest(data);
    }
}
