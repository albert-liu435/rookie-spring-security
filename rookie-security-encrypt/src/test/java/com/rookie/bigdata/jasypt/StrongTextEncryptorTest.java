package com.rookie.bigdata.jasypt;

import org.jasypt.util.text.StrongTextEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Class StrongTextEncryptorTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 15:47
 * @Version 1.0
 */
public class StrongTextEncryptorTest {

    StrongTextEncryptor textEncryptor;

    @BeforeEach
    public void setUp() {
        textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }

    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //KU6h0PmBBJ2mZa1zq3PkaXmhmxnTT9pL
        //DOiGEcatVuYitcWFkdS5MzeA2W3ZttN0
    }

    @Test
    public void decyptPwd() {

        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("KU6h0PmBBJ2mZa1zq3PkaXmhmxnTT9pL"));

//        root@1234
        System.out.println(textEncryptor.decrypt("DOiGEcatVuYitcWFkdS5MzeA2W3ZttN0"));
    }
}
