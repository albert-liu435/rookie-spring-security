package com.rookie.bigdata.basic.basic.mac;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class HmacUtilsCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:17
 * @Version 1.0
 */
@Slf4j
class HmacUtilsCoderTest {
    @Test
    void initMacKey() throws Exception {
        String msg = "测试数据";
        String key = HmacUtilsCoder.initMacKey(HmacUtilsCoder.HmacMD5);
        String hash = HmacUtilsCoder.hashMsgCode(HmacUtilsCoder.HmacMD5, key, msg.getBytes("utf-8"));
        log.info("key:" + msg + ";hash:" + hash);
        boolean check = HmacUtilsCoder.check(HmacUtilsCoder.HmacMD5, key, hash, msg);
        log.info("check:" + check);


        key = HmacUtilsCoder.initMacKey(HmacUtilsCoder.HmacSHA1);
        hash = HmacUtilsCoder.hashMsgCode(HmacUtilsCoder.HmacSHA1, key, msg.getBytes("utf-8"));
        log.info("key:" + msg + ";hash:" + hash);
        check = HmacUtilsCoder.check(HmacUtilsCoder.HmacSHA1, key, hash, msg);
        log.info("check:" + check);

        key = HmacUtilsCoder.initMacKey(HmacUtilsCoder.HmacSHA224);
        hash = HmacUtilsCoder.hashMsgCode(HmacUtilsCoder.HmacSHA224, key, msg.getBytes("utf-8"));
        log.info("key:" + msg + ";hash:" + hash);
        check = HmacUtilsCoder.check(HmacUtilsCoder.HmacSHA224, key, hash, msg);
        log.info("check:" + check);

        key = HmacUtilsCoder.initMacKey(HmacUtilsCoder.HmacSHA256);
        hash = HmacUtilsCoder.hashMsgCode(HmacUtilsCoder.HmacSHA256, key, msg.getBytes("utf-8"));
        log.info("key:" + msg + ";hash:" + hash);
        check = HmacUtilsCoder.check(HmacUtilsCoder.HmacSHA256, key, hash, msg);
        log.info("check:" + check);


        key = HmacUtilsCoder.initMacKey(HmacUtilsCoder.HmacSHA384);
        hash = HmacUtilsCoder.hashMsgCode(HmacUtilsCoder.HmacSHA384, key, msg.getBytes("utf-8"));
        log.info("key:" + msg + ";hash:" + hash);
        check = HmacUtilsCoder.check(HmacUtilsCoder.HmacSHA384, key, hash, msg);
        log.info("check:" + check);


        key = HmacUtilsCoder.initMacKey(HmacUtilsCoder.HmacSHA512);
        hash = HmacUtilsCoder.hashMsgCode(HmacUtilsCoder.HmacSHA512, key, msg.getBytes("utf-8"));
        log.info("key:" + msg + ";hash:" + hash);
        check = HmacUtilsCoder.check(HmacUtilsCoder.HmacSHA512, key, hash, msg);
        log.info("check:" + check);
    }
}
