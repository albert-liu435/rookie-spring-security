package com.rookie.bigdata.basic.symmetric.pbe;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKeyFactory;
import java.security.Security;

/**
 * @Class BcPbe
 * @Description
 * @Author rookie
 * @Date 2024/7/23 14:44
 * @Version 1.0
 */
public class BcPbe extends AbstractSymmetric {
    @Override
    public void decryptBefore() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

    }

    @Override
    public void encryptBefore() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

    }

    @Override
    public SecretKeyFactory initKeyBefore(String algorithm) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm,"BC");
        return factory;
    }
}
