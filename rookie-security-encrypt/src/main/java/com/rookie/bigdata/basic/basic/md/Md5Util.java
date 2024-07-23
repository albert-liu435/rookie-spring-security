package com.rookie.bigdata.basic.basic.md;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String md5(byte[] b) throws NoSuchAlgorithmException {
//        try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(b);
        byte[] hash = md.digest();
        StringBuffer outStrBuf = new StringBuffer(32);
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            if (v < 16) {
                outStrBuf.append('0');
            }
            outStrBuf.append(Integer.toString(v, 16).toLowerCase());
        }
        return outStrBuf.toString();
//        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////            logger.error("md5加密失败 ",e);
//            return new String(b);
//        }
    }
}
