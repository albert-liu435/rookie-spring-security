package com.rookie.bigdata.basic.asymmetric.rsa;

import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;


/**
 * @Class JdkRsaSignTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 15:42
 * @Version 1.0
 */
class JdkRsaSignTest {


    @Test
    void initKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        List<String> list = new ArrayList<>();
        list.add(JdkRsaSign.MD2withRSA);
        list.add(JdkRsaSign.MD5withRSA);
        list.add(JdkRsaSign.SHA1withRSA);
        list.add(JdkRsaSign.SHA224withRSA);
        list.add(JdkRsaSign.SHA256withRSA);
        list.add(JdkRsaSign.SHA384withRSA);
        list.add(JdkRsaSign.SHA512withRSA);
        String msg = "cesfds";

        for (String signAlgo : list) {
            Map.Entry<byte[], byte[]> pair = JdkRsaSign.initKeyPair(JdkRsaSign.RSA, 1024);
            System.out.println("pubKey:" + Base64.getEncoder().encodeToString(pair.getKey()));
            System.out.println("priKey:" + Base64.getEncoder().encodeToString(pair.getValue()));
            byte[] encrypt = JdkRsaSign.priKeySign(JdkRsaSign.RSA, signAlgo, pair.getValue(), msg.getBytes());
            System.out.println("encrypt:" + Base64.getEncoder().encodeToString(encrypt));
            boolean check = JdkRsaSign.pubKeyCheckSign(JdkRsaSign.RSA, signAlgo, pair.getKey(), msg.getBytes(), encrypt);
            System.out.println("txt:" + check);
        }
    }

}
