package com.rookie.bigdata.basic.nationalpassword.sm3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class BcSm3UtilTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 16:15
 * @Version 1.0
 */
class BcSm3UtilTest {

    private String test="woshi测试数据。。..";

    @Test
    public void sm3() throws Exception {
        String s = BcSm3Util.sm3Hex(test.getBytes());
        System.out.println(s);
        String s2 = BcSm3Util.sm3bcHex(test.getBytes());
        System.out.println(s2);
//        Assert.assertEquals(s,s2);
    }

    @Test
    public void hmacSm3Hex() {
        String s = BcSm3Util.hmacSm3Hex("AAAA".getBytes(),test.getBytes());
        System.out.println(s);
    }

}
