package com.rookie.bigdata.basic.asymmetric.ecc;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

/**
 * @Class BcEccCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 10:15
 * @Version 1.0
 */
@Slf4j
class BcEccCoderTest {


    @Test
    void initKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String msg = "我是测试数据";
        KeyPair keyPair = BcEccCoder.initKeyPair("", 256);
        log.info("pub:{}", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        log.info("pri:{}", Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));

        byte[] encrypt = BcEccCoder.encrypt(msg.getBytes(StandardCharsets.UTF_8), keyPair.getPublic());
        log.info("encrypt Data:{}", Base64.getEncoder().encodeToString(encrypt));

        byte[] decrypt = BcEccCoder.decrypt(encrypt, keyPair.getPrivate());
        log.info("txt Data:{}", (new String(decrypt)));
    }

}
