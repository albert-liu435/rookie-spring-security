package com.rookie.bigdata.basic.basic.base64;

import com.rookie.bigdata.basic.basic.base64.Base64Coder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @Class Base64CoderTest
 * @Description https://blog.csdn.net/chenwewi520feng/article/details/131302314
 * @Author rookie
 * @Date 2024/7/18 17:51
 * @Version 1.0
 */
class Base64CoderTest {
    @Test
     void test() throws Exception {
        String inputStr = "Java加密与解密";
        // 进行Base64编码
        String code = Base64Coder.encode(inputStr);
        System.err.println("Base64编码后:\n\t" + code);

        // 进行Base64解码
        String outputStr = Base64Coder.decode(code);
        System.err.println("Base64解码后:\n\t" + outputStr);

        // 验证Base64编码解码一致性
        assertEquals(inputStr, outputStr);
    }

    @Test
     void demo() throws Exception {
        String str = "Java加密与解密";
        // Base64编码
        String data = Base64Coder.encode(str);
        System.err.println("编码后:\n\t" + data);

        // Base64解码
        String output = Base64Coder.decode(data);
        System.err.println("解码后:\n\t" + output);
    }


    @Test
    void test001()throws Exception{
        String encodeMsg="A";
        String encoder = Base64Coder.encode(encodeMsg, "utf-8");
        String decoder = Base64Coder.decode(encoder, "utf-8");
        assertEquals(encodeMsg,decoder);
    }

    @Test
    void testHash001()throws Exception{
        //99162322
        int i = "hello".hashCode();
        System.out.println(i);
        int i1 = "hello".hashCode();
        System.out.println(i1);
        //　　根据碰撞概率，哈希算法的输出长度越长，就越难产生碰撞，也就越安全。
        System.out.println("AaAaAa".hashCode());//1952508096
        System.out.println("BBAaBB".hashCode());//1952508096
    }
}
