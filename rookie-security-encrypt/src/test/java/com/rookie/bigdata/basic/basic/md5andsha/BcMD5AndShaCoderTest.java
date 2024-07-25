package com.rookie.bigdata.basic.basic.md5andsha;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


/**
 * @Class BcMD5AndShaCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:14
 * @Version 1.0
 */
@Slf4j
class BcMD5AndShaCoderTest {


    @Test
    void msgSafeBase() throws Exception {
        log.info("MD2:{}", BcMD5AndShaCoder.msgSafeBase("测试", BcMD5AndShaCoder.MD2));

//        //MD3不存在
//        log.info("MD3:{}", JDKMd5AndShaCoder.msgSafeBase("测试", JDKMd5AndShaCoder.MD3));
        log.info("MD4:{}", BcMD5AndShaCoder.msgSafeBase("测试", BcMD5AndShaCoder.MD4));

        log.info("MD5:{}", BcMD5AndShaCoder.msgSafeBase("测试", BcMD5AndShaCoder.MD5));

        log.info("SHA1:{}", BcMD5AndShaCoder.msgSafeBase("测试", BcMD5AndShaCoder.SHA1));

        log.info("SHA224:{}", BcMD5AndShaCoder.msgSafeBase("测试", BcMD5AndShaCoder.SHA224));

        log.info("SHA256:{}", BcMD5AndShaCoder.msgSafeBase("测试", BcMD5AndShaCoder.SHA256));

        log.info("SHA384:{}", BcMD5AndShaCoder.msgSafeBase("测试", BcMD5AndShaCoder.SHA384));

        log.info("SHA512:{}", BcMD5AndShaCoder.msgSafeBase("测试", BcMD5AndShaCoder.SHA512));
    }
}
