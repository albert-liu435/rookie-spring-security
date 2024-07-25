package com.rookie.bigdata.basic.asymmetric.dh;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Class JdkDHCoder
 * @Description
 * @Author rookie
 * @Date 2024/7/25 14:42
 * @Version 1.0
 */
public class JdkDHCoder extends AbstractDHCoder {
    @Override
    public void encryptBefore(String secretAlgorithm) {

    }

    @Override
    public Cipher getCipher(String secretAlgorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(secretAlgorithm);
    }

    @Override
    public void decryptBefore(String secretAlgorithm) {

    }

    @Override
    public void getSecretKeyBefore(String secretAlgorithm) {

    }
}
