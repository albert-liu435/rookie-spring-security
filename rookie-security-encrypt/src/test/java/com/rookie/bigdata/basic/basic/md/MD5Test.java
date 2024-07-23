package com.rookie.bigdata.basic.basic.md;

import com.rookie.bigdata.basic.basic.md.MD5;
import org.junit.jupiter.api.Test;


/**
 * @Class MD5Test
 * @Description
 * @Author rookie
 * @Date 2024/7/23 11:48
 * @Version 1.0
 */
class MD5Test {

    @Test
    void test01()throws Exception{
        MD5 md = new MD5();
        System.out.println("md5(abc)=" + md.digest("abc"));
    }

}
