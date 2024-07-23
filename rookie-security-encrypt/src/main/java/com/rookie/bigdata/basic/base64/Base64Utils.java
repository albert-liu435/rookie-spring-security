package com.rookie.bigdata.basic.base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @Class Base64Utils
 * @Description
 * @Author rookie
 * @Date 2024/7/23 9:57
 * @Version 1.0
 */
public class Base64Utils {

    //    Base64的索引表，字符选用了"A-Z、a-z、0-9、+、/" 64个可打印字符
    private static String base64Table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static String add = "=";

    /**
     * base64编码
     *
     * @param str
     * @param charsetName
     * @return
     */
    public static String encode(String str, String charsetName) {
        StringBuilder base64Str = new StringBuilder();
        byte[] bytesStr;
        try {
            bytesStr = str.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

//        2、转换步骤
//
//第一步，将待转换的字符串每三个字节分为一组，每个字节占8bit，那么共有24个二进制位。
//
//第二步，将上面的24个二进制位每6个一组，共分为4组。
//
//第三步，在每组前面添加两个0，每组由6个变为8个二进制位，总共32个二进制位，即四个字节。
//
//第四步，根据Base64编码对照表（见下图）获得对应的值。

        //编码为二进制字符串
        String bytesBinary = binary(bytesStr, 2);
        // 24位为一组，不够后面补0,计算出需要补充的个数
        int addCount = 0;
        while (bytesBinary.length() % 24 != 0) {
            bytesBinary += "0";
            addCount++;
        }

        for (int i = 0; i <= bytesBinary.length() - 6; i += 6) {
            // 二进制转十进制
            int index = Integer.parseInt(bytesBinary.substring(i, i + 6), 2);
            // 如果是有补位的6个0组成，则转换为'='
            if (index == 0 && i >= bytesBinary.length() - addCount) {
                base64Str.append(add);
            } else {
                base64Str.append(base64Table.charAt(index));
            }
        }
        return base64Str.toString();
    }

    /**
     * base64解码
     */
    public static String decode(String base64str, String charsetName) {
        String base64Binarys = "";
        for (int i = 0; i < base64str.length(); i++) {
            char s = base64str.charAt(i);
            if (s != '=') {
                // 十进制转二进制
                String binary = Integer.toBinaryString(base64Table.indexOf(s));
                // 不够六位进行补位
                while (binary.length() != 6) {
                    binary = "0" + binary;
                }
                base64Binarys += binary;
            }
        }
        // 长度应该是8的倍数，去除后面多余的0
        base64Binarys = base64Binarys.substring(0, base64Binarys.length() - base64Binarys.length() % 8);
        byte[] bytesStr = new byte[base64Binarys.length() / 8];
        for (int bytesIndex = 0; bytesIndex < base64Binarys.length() / 8; bytesIndex++) {
            // 八位截取一次，转化为一个字节
            bytesStr[bytesIndex] = (byte) Integer.parseInt(base64Binarys.substring(bytesIndex * 8, bytesIndex * 8 + 8), 2);
        }
        try {
            return new String(bytesStr, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 字节数组转自定义进制字符串
     *
     * @param bytes
     * @param radix
     * @return
     */
    public static String binary(byte[] bytes, int radix) {

        // 转化为二进制字符串   1代表正数,如果第一个字节以0开头，转化后会省略，所以要重新补位
        String strBytes = new BigInteger(1, bytes).toString(radix);
        while (strBytes.length() % 8 != 0) {
            strBytes = "0" + strBytes;
        }
        return strBytes;
    }
}
