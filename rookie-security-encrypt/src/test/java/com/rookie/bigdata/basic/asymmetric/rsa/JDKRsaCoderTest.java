package com.rookie.bigdata.basic.asymmetric.rsa;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class JDKRsaCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 9:47
 * @Version 1.0
 */
@Slf4j
class JDKRsaCoderTest {


    @Test
    void initKeyPair512() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String msg="cesfds";
        Map.Entry<byte[],byte[]> pair=JDKRsaCoder.initKeyPair(JDKRsaCoder.RSA,512);
//        System.out.println("pubKey:"+);

        log.info("pubKey:{}", Base64.getEncoder().encodeToString(pair.getKey()));
        log.info("priKey:{}",Base64.getEncoder().encodeToString(pair.getValue()));

        byte[] encrypt=JDKRsaCoder.pubKeyEncrypt(JDKRsaCoder.RSA,pair.getKey(),msg.getBytes(StandardCharsets.UTF_8));
        log.info("encrypt:{}",Base64.getEncoder().encodeToString(encrypt));

        byte[]bytes=JDKRsaCoder.priKeyDecrypt(JDKRsaCoder.RSA,pair.getValue(),encrypt);
        log.info("txt:{}",(new String(bytes)));
    }


    @Test
    void initKeyPair1024() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String msg="cesfds";
        Map.Entry<byte[],byte[]> pair=JDKRsaCoder.initKeyPair(JDKRsaCoder.RSA,1024);
//        System.out.println("pubKey:"+);

        log.info("pubKey:{}", Base64.getEncoder().encodeToString(pair.getKey()));
        log.info("priKey:{}",Base64.getEncoder().encodeToString(pair.getValue()));

        byte[] encrypt=JDKRsaCoder.pubKeyEncrypt(JDKRsaCoder.RSA,pair.getKey(),msg.getBytes(StandardCharsets.UTF_8));
        log.info("encrypt:{}",Base64.getEncoder().encodeToString(encrypt));

        byte[]bytes=JDKRsaCoder.priKeyDecrypt(JDKRsaCoder.RSA,pair.getValue(),encrypt);
        log.info("txt:{}",(new String(bytes)));
    }


    @Test
    void initKeyPair2048() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String msg="cesfds";
        Map.Entry<byte[],byte[]> pair=JDKRsaCoder.initKeyPair(JDKRsaCoder.RSA,2048);
//        System.out.println("pubKey:"+);

        log.info("pubKey:{}", Base64.getEncoder().encodeToString(pair.getKey()));
        log.info("priKey:{}",Base64.getEncoder().encodeToString(pair.getValue()));

        byte[] encrypt=JDKRsaCoder.pubKeyEncrypt(JDKRsaCoder.RSA,pair.getKey(),msg.getBytes(StandardCharsets.UTF_8));
        log.info("encrypt:{}",Base64.getEncoder().encodeToString(encrypt));

        byte[]bytes=JDKRsaCoder.priKeyDecrypt(JDKRsaCoder.RSA,pair.getValue(),encrypt);
        log.info("txt:{}",(new String(bytes)));
    }

}
