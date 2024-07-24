package com.rookie.bigdata.basic.nationalpassword.sm3;

import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @Class SM3UtilTest
 * @Description
 * @Author rookie
 * @Date 2024/7/23 18:05
 * @Version 1.0
 */
class SM3UtilTest {

    public static final byte[] SRC_DATA = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
    public static final byte[] WITH_ID = new byte[] { 1, 2, 3, 4 };

    @Test
    public void testHashAndVerify() {
        try {
            byte[] hash = SM3Util.hash(SRC_DATA);
            System.out.println("SM3 hash result:\n" + ByteUtils.toHexString(hash));
            boolean flag = SM3Util.verify(SRC_DATA, hash);
            if (!flag) {
//                Assert.fail();
                System.out.println("失败1");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            Assert.fail();
            System.out.println("失败2");
        }
    }

    @Test
    public void testHmacSM3() {
        try {
            byte[] hmacKey = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
            byte[] hmac = SM3Util.hmac(hmacKey, SRC_DATA);
            System.out.println("SM3 hashmac result:\n" + Arrays.toString(hmac));
        } catch (Exception ex) {
            ex.printStackTrace();
//            Assert.fail();
            System.out.println("失败3");
        }
    }
}
