package com.rookie.bigdata.jasypt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Class RC128TextEncryptorTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 15:01
 * @Version 1.0
 */
public class RC128TextEncryptorTest {

    RC128TextEncryptor textEncryptor;

    @BeforeEach
    public void setUp() {
        textEncryptor = new RC128TextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }

    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //zjhmIP38jmvob56qyNevHjs=
        //iMX2aR70CkLGdtlAdhe2XKI=
    }

    @Test
    public void decyptPwd() {
        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("zjhmIP38jmvob56qyNevHjs="));

//        root@1234
        System.out.println(textEncryptor.decrypt("iMX2aR70CkLGdtlAdhe2XKI="));
    }
}
