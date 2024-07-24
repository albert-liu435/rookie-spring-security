package com.rookie.bigdata.jasypt;

import org.junit.jupiter.api.Test;

/**
 * @Class JasyptUtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 15:00
 * @Version 1.0
 */
public class JasyptUtilsTest {

    @Test
    public void encryptPwd() {
        // 加密
        System.out.println(JasyptUtils.encryptPwd("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7", "root@1234"));
        //TJetNWzmC4os1CCb+gHtz+5MpL9NFMML
        //KCTSu/Dv1elE1A/ZyppCHgJAAwKiez/p

//        StrongTextEncryptor
    }

    @Test
    public void decyptPwd() {

        // 解密
//        root@1234
        System.out.println(JasyptUtils.decyptPwd("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7", "TJetNWzmC4os1CCb+gHtz+5MpL9NFMML"));

//        root@1234
        System.out.println(JasyptUtils.decyptPwd("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7", "KCTSu/Dv1elE1A/ZyppCHgJAAwKiez/p"));
    }
}
