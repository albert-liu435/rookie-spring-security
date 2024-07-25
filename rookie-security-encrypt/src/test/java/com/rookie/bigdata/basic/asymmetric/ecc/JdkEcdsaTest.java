package com.rookie.bigdata.basic.asymmetric.ecc;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class JdkEcdsaTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 16:11
 * @Version 1.0
 */
class JdkEcdsaTest {
    @Test
    public void initKey() throws Exception {
        String msg="我是测试数据";
        KeyPair keyPair = JdkEcdsa.initKey( 256);
        System.out.println("pub:"+ Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        System.out.println("pri:"+ Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        List<String> list= new ArrayList<>();
//        sun.security.ec.ECDSASignature
        list.add(JdkEcdsa.NONEwithECDSA);
//        list.add(JdkEcdsa.RIPEMD160withECDSA);
        list.add(JdkEcdsa.SHA1withECDSA);
        list.add(JdkEcdsa.SHA224withECDSA);
        list.add(JdkEcdsa.SHA256withECDSA);
        list.add(JdkEcdsa.SHA384withECDSA);
        list.add(JdkEcdsa.SHA512withECDSA);
        for (String algo : list) {
            byte[] sign = JdkEcdsa.sign(algo,keyPair.getPrivate(),msg.getBytes());
            System.out.println("sign:"+ Base64.getEncoder().encodeToString(sign));

            boolean checkb= JdkEcdsa.verify(algo,keyPair.getPublic(),msg.getBytes(),sign);
            System.out.println("checkb:"+ checkb);
        }


    }
}
