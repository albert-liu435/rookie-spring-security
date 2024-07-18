package com.rookie.bigdata.base64;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class Base64UtilTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131280324
 * @Author rookie
 * @Date 2024/7/18 16:41
 * @Version 1.0
 */
class Base64UtilTest {

    @Test
    void encode() throws Exception{

        String hello = Base64Util.encode("hello");
        System.out.println("base64: "+hello);
    }

    @Test
    void decode() throws Exception{
        String hello = Base64Util.decode("aGVsbG8=");
        System.out.println("base64: "+hello);
    }


    @Test
    void test01()throws Exception{
        String man = Base64Util.encode("Man");
        System.out.println("base64: "+man);
    }

}
