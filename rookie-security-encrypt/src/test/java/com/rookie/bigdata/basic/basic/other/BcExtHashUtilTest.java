package com.rookie.bigdata.basic.basic.other;

import org.junit.jupiter.api.Test;


/**
 * @Class BcExtHashUtilTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:45
 * @Version 1.0
 */
class BcExtHashUtilTest {
    @Test
    void encodeExtHash() throws Exception {
        String str = "RIPEMD消息摘要";
        System.out.println("原文：" + str);
        String data1hex = "";

        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.RipeMD128, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.RipeMD160, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.RipeMD256, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.RipeMD320, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);


        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.Tiger, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.Whirlpool, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.Gost3411, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
    }
}
