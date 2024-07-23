package com.rookie.bigdata.basic.basic.md;

import com.rookie.bigdata.basic.basic.md.MD5Hash;
import org.junit.jupiter.api.Test;

/**
 * @Class MD5HashTest
 * @Description
 * @Author rookie
 * @Date 2024/7/23 11:53
 * @Version 1.0
 */
class MD5HashTest {

    @Test
    void getMD5() {

        MD5Hash md5Hash=new MD5Hash();
        String hello = md5Hash.getMD5("hello");
        System.out.println(hello);
    }
}
