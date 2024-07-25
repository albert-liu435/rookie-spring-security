package com.rookie.bigdata.basic.basic.ripemd;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class RipeMDCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:31
 * @Version 1.0
 */

@Slf4j
class RipeMDCoderTest {

    @Test
    void encodeRipeMD() throws NoSuchAlgorithmException {
        String str="RIPEMD消息摘要";
        log.info("原文:{}",str);

        String data1hex=RipeMDCoder.encodeRipeMDHex(RipeMDCoder.RipeMD128,str.getBytes(StandardCharsets.UTF_8));
        log.info("16进制消息摘要算法值：{}",data1hex);

        data1hex=RipeMDCoder.encodeRipeMDHex(RipeMDCoder.RipeMD160,str.getBytes(StandardCharsets.UTF_8));
        log.info("16进制消息摘要算法值：{}",data1hex);

        data1hex=RipeMDCoder.encodeRipeMDHex(RipeMDCoder.RipeMD256,str.getBytes(StandardCharsets.UTF_8));
        log.info("16进制消息摘要算法值：{}",data1hex);


        data1hex=RipeMDCoder.encodeRipeMDHex(RipeMDCoder.RipeMD320,str.getBytes(StandardCharsets.UTF_8));
        log.info("16进制消息摘要算法值：{}",data1hex);








    }

}
