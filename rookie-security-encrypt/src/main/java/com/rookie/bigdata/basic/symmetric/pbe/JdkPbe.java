package com.rookie.bigdata.basic.symmetric.pbe;

import javax.crypto.SecretKeyFactory;

/**
 * @Class JdkPbe
 * @Description
 * @Author rookie
 * @Date 2024/7/23 14:46
 * @Version 1.0
 */
public class JdkPbe extends AbstractSymmetric {
    @Override
    public void decryptBefore() throws Exception {

    }

    @Override
    public void encryptBefore() throws Exception {

    }

    @Override
    public SecretKeyFactory initKeyBefore(String algorithm) throws Exception {
        return SecretKeyFactory.getInstance(algorithm);
    }
}
