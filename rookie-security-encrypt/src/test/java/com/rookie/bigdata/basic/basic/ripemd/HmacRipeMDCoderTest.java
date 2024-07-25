package com.rookie.bigdata.basic.basic.ripemd;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class HmacRipeMDCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:42
 * @Version 1.0
 */
@Slf4j
class HmacRipeMDCoderTest {

    @Test
    void initHmacRipeMDKey() {
    }

    @Test
    void encodeHmacRipeMD() {
    }

    @Test
    void encodeHmacRipeMDHex() throws NoSuchAlgorithmException, InvalidKeyException {

        String str = "RIPEMD消息摘要";
        log.info("原文：" + str);
        String key = HmacRipeMDCoder.initHmacRipeMDKey(HmacRipeMDCoder.HmacRipeMD128);
        String hex = HmacRipeMDCoder.encodeHmacRipeMDHex(HmacRipeMDCoder.HmacRipeMD128, key,str.getBytes());
        log.info("十六进制消息摘要算法值：" + hex);


        key = HmacRipeMDCoder.initHmacRipeMDKey(HmacRipeMDCoder.HmacRipeMD160);
        hex = HmacRipeMDCoder.encodeHmacRipeMDHex(HmacRipeMDCoder.HmacRipeMD160, key,str.getBytes());
        log.info("十六进制消息摘要算法值：" + hex);

    }
}
