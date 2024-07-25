package com.rookie.bigdata.basic.asymmetric.dh;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

/**
 * @Class BcDHCoder
 * @Description
 * @Author rookie
 * @Date 2024/7/25 14:59
 * @Version 1.0
 */
public class BcDHCoder extends AbstractDHCoder{
    @Override
    public void encryptBefore(String secretAlgorithm) {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public Cipher getCipher(String secretAlgorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        return Cipher.getInstance(secretAlgorithm,"BC");
    }

    @Override
    public void decryptBefore(String secretAlgorithm) {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void getSecretKeyBefore(String secretAlgorithm) {
        Security.addProvider(new BouncyCastleProvider());
    }
}
