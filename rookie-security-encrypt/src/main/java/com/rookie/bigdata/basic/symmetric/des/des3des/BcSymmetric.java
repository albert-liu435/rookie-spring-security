package com.rookie.bigdata.basic.symmetric.des.des3des;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyGenerator;
import java.security.Security;
import java.util.Map;

/**
 * @Class BcSymmetric
 * @Description
 * @Author rookie
 * @Date 2024/7/23 15:35
 * @Version 1.0
 */
public class BcSymmetric extends AbstractSymmetric {

    @Override
    public void decryptBefore() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        return;
    }

    @Override
    public void encryptBefore() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        return;
    }

    @Override
    public KeyGenerator initKeyBefore(Map.Entry<String, String> algorithm) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator kg = KeyGenerator.getInstance(algorithm.getKey(), "BC");
        return kg;
    }
}
