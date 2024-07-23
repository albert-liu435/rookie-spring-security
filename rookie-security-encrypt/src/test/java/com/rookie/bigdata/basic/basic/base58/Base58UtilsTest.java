package com.rookie.bigdata.basic.basic.base58;

import com.rookie.bigdata.basic.basic.base58.Base58Utils;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

/**
 * @Class Base58UtilsTest
 * @Description https://www.cnblogs.com/bjlhx/p/6544311.html
 * @Author rookie
 * @Date 2024/7/23 11:12
 * @Version 1.0
 */
class Base58UtilsTest {

    @Test
     void encode() throws Exception {
//        String msg = "453534534534543534:dfsdfsdfsdfsdfsdfdsfdsfs";
        String msg="1234";
        String encode = Base58Utils.encode(msg.getBytes("UTF-8"), "UTF-8");
        System.out.println(encode);

        String dencode = new String(Base58Utils.decode(encode), "UTF-8");
        System.out.println(dencode);
        Assert.assertEquals(msg, dencode);
    }
}
