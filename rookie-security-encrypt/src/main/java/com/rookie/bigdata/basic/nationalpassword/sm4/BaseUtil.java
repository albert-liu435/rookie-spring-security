package com.rookie.bigdata.basic.nationalpassword.sm4;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @Class BaseUtil
 * @Description
 * @Author rookie
 * @Date 2024/7/23 17:24
 * @Version 1.0
 */
public class BaseUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
