package com.rookie.bigdata.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Class BasicTextEncryptorTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 15:00
 * @Version 1.0
 */
public class BasicTextEncryptorTest {

    BasicTextEncryptor textEncryptor;

    @BeforeEach
    public void setUp() {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }

    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //TJetNWzmC4os1CCb+gHtz+5MpL9NFMML
        //KCTSu/Dv1elE1A/ZyppCHgJAAwKiez/p
    }

    @Test
    public void decyptPwd() {
        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("TJetNWzmC4os1CCb+gHtz+5MpL9NFMML"));

//        root@1234
        System.out.println(textEncryptor.decrypt("KCTSu/Dv1elE1A/ZyppCHgJAAwKiez/p"));
    }
}
