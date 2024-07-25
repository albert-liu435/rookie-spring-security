package com.rookie.bigdata.basic.basic.md5andsha;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class JDKMd5AndShaCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:02
 * @Version 1.0
 */
@Slf4j
class JDKMd5AndShaCoderTest {


    @Test
    void msgSafeBase() throws NoSuchAlgorithmException {
        log.info("MD2:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.MD2));

//        //MD3和MD4不存在
//        log.info("MD3:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.MD3));
//        log.info("MD4:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.MD4));

        log.info("MD5:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.MD5));

        log.info("SHA1:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.SHA1));

        log.info("SHA224:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.SHA224));

        log.info("SHA256:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.SHA256));

        log.info("SHA384:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.SHA384));

        log.info("SHA512:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.SHA512));
    }

    @Test
    void hashCheck() throws Exception {
        System.out.println("AaAaAa".hashCode());//1952508096
        System.out.println("BBAaBB".hashCode());//1952508096
    }
}
