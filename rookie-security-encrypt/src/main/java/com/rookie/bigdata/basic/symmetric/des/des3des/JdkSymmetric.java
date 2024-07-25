package com.rookie.bigdata.basic.symmetric.des.des3des;

import javax.crypto.KeyGenerator;
import java.util.Map;

/**
 * @Class JdkSymmetric
 * @Description
 * @Author rookie
 * @Date 2024/7/23 15:35
 * @Version 1.0
 */
public class JdkSymmetric extends AbstractSymmetric {
    @Override
    public void decryptBefore() throws Exception {

    }

    @Override
    public void encryptBefore() throws Exception {

    }

    @Override
    public KeyGenerator initKeyBefore(Map.Entry<String, String> algorithm) throws Exception {
        return KeyGenerator.getInstance(algorithm.getKey());
    }

//    @Override
//    public KeyGenerator initKeyBefore(Map.Entry<String, String> algorithm) throws Exception {
//        return KeyGenerator.getInstance(algorithm.getKey());
//    }
//
//    @Override
//    public void encryptBefore() throws Exception {
//        return;
//    }
//
//    @Override
//    public void decryptBefore() throws Exception {
//        return;
//    }


}
