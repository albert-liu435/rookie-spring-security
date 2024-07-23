package com.rookie.bigdata.basic.mac;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Class HmacMD5
 * @Description https://blog.csdn.net/qq_15764943/article/details/118099518
 * @Author rookie
 * @Date 2024/7/23 14:02
 * @Version 1.0
 */
public class HmacMD5 {

//实现过程
//在密钥key后面添加0来创建一个长为B(64字节)的字符串（str）；
//将上一步生成的字符串(str) 与ipad（0x36）做异或运算，形成结果字符串（istr）
//将数据流data附加到第二步的结果字符串(istr)的末尾
//做md5运算于第三步生成的数据流(istr)
//将第一步生成的字符串(str) 与opad（0x5c）做异或运算，形成结果字符串（ostr）
//再将第四步的结果(istr) 附加到第五步的结果字符串（ostr）的末尾
//做md5运算于第6步生成的数据流（ostr），最终输出结果(out)
//————————————————
//
//                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。
//
//原文链接：https://blog.csdn.net/qq_15764943/article/details/118099518

    /**
     * 将待加密数据data，通过密钥key，使用hmac-md5算法进行加密，然后返回加密结果。 参照rfc2104 HMAC算法介绍实现。
     *
     * @param key  密钥
     * @param data 待加密数据
     * @return 加密结果
     * @throws NoSuchAlgorithmException
     */
    public static String getHmacMd5(byte[] key, byte[] data) throws NoSuchAlgorithmException {
        /*
         * HmacMd5 calculation formula: H(K XOR opad, H(K XOR ipad, text)) HmacMd5
         * 计算公式：H(K XOR opad, H(K XOR ipad, text))
         * H代表hash算法，本类中使用MD5算法，K代表密钥，text代表要加密的数据 ipad为0x36，opad为0x5C。
         */
        int length = 64;
        byte[] ipad = new byte[length];
        byte[] opad = new byte[length];

        for (int i = 0; i < 64; i++) {
            ipad[i] = 0x36;
            opad[i] = 0x5C;
        }
        byte[] actualKey = key; // Actual key.
        byte[] keyArr = new byte[length]; // Key bytes of 64 bytes length
        /*
         * If key's length is longer than 64,then use hash to digest it and use the
         * result as actual key. 如果密钥长度，大于64字节，就使用哈希算法，计算其摘要，作为真正的密钥。
         */
        if (key.length > length) {
            actualKey = md5(key);
        }
        for (int i = 0; i < actualKey.length; i++) {
            keyArr[i] = actualKey[i];
        }

        /*
         * append zeros to K 如果密钥长度不足64字节，就使用0x00补齐到64字节。
         */
        if (actualKey.length < length) {
            for (int i = actualKey.length; i < keyArr.length; i++)
                keyArr[i] = 0x00;
        }

        /*
         * calc K XOR ipad 使用密钥和ipad进行异或运算。
         */
        byte[] kIpadXorResult = new byte[length];
        for (int i = 0; i < length; i++) {
            kIpadXorResult[i] = (byte) (keyArr[i] ^ ipad[i]);
        }

        /*
         * append "text" to the end of "K XOR ipad" 将待加密数据追加到K XOR ipad计算结果后面。
         */
        byte[] firstAppendResult = new byte[kIpadXorResult.length + data.length];
        for (int i = 0; i < kIpadXorResult.length; i++) {
            firstAppendResult[i] = kIpadXorResult[i];
        }
        for (int i = 0; i < data.length; i++) {
            firstAppendResult[i + keyArr.length] = data[i];
        }

        /*
         * calc H(K XOR ipad, text) 使用哈希算法计算上面结果的摘要。
         */
        byte[] firstHashResult = md5(firstAppendResult);

        /*
         * calc K XOR opad 使用密钥和opad进行异或运算。
         */
        byte[] kOpadXorResult = new byte[length];
        for (int i = 0; i < length; i++) {
            kOpadXorResult[i] = (byte) (keyArr[i] ^ opad[i]);
        }

        /*
         * append "H(K XOR ipad, text)" to the end of "K XOR opad" 将H(K XOR ipad,
         * text)结果追加到K XOR opad结果后面
         */
        byte[] secondAppendResult = new byte[kOpadXorResult.length + firstHashResult.length];
        for (int i = 0; i < kOpadXorResult.length; i++) {
            secondAppendResult[i] = kOpadXorResult[i];
        }
        for (int i = 0; i < firstHashResult.length; i++) {
            secondAppendResult[i + keyArr.length] = firstHashResult[i];
        }

        /*
         * H(K XOR opad, H(K XOR ipad, text)) 对上面的数据进行哈希运算。
         */
        byte[] src = md5(secondAppendResult);

        // 字节转十六进制
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static byte[] md5(byte[] data) throws NoSuchAlgorithmException {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 执行消息摘要
        return md.digest(data);
    }


}
