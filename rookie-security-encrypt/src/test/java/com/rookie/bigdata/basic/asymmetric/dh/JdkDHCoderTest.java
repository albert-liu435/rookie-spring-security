package com.rookie.bigdata.basic.asymmetric.dh;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class JdkDHCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 14:44
 * @Version 1.0
 */
@Slf4j
class JdkDHCoderTest {

    AbstractDHCoder abstractDHCoder=null;
    @BeforeEach
    public void setUp(){
        abstractDHCoder=new JdkDHCoder();
    }

    @Test
    public void initKey() throws Exception {
        String msg="我是密文";
        // 甲方构建密钥对
        Map.Entry<byte[], byte[]> entry = AbstractDHCoder.initKey();
        log.info("甲方 pubkey："+ Base64.getEncoder().encodeToString(entry.getKey()));
        log.info("甲方 prikey："+ Base64.getEncoder().encodeToString(entry.getValue()));

        log.info("甲方将 公钥 发送给 乙方");
        log.info("乙方 使用甲方pubkey构建密钥对");
        Map.Entry<byte[], byte[]> entryYi = AbstractDHCoder.initKeyByPubKey(entry.getKey());
        log.info("乙方 pubkey："+ Base64.getEncoder().encodeToString(entryYi.getKey()));
        log.info("乙方 prikey："+ Base64.getEncoder().encodeToString(entryYi.getValue()));

        log.info("乙方 pubkey 发送给 甲方");

        log.info("甲方加密数据，使用自己私钥以及 对方公钥");
        byte[] encrypt = abstractDHCoder.encrypt(AbstractDHCoder.DESede,msg.getBytes(), entryYi.getKey(), entry.getValue());
        log.info("甲方将加密数据，发送给乙方");
        log.info("乙方 使用自己私钥以及 对方公钥 解密");
        byte[] decrypt = abstractDHCoder.decrypt(AbstractDHCoder.DESede,encrypt, entry.getKey(), entryYi.getValue());
        log.info(new String(decrypt));

//        System.out.println("乙方回发消息");
        byte[] encrypt2 = abstractDHCoder.encrypt(AbstractDHCoder.DESede,"乙方回发消息".getBytes(), entry.getKey(), entryYi.getValue());

        byte[] decrypt2 = abstractDHCoder.decrypt(AbstractDHCoder.DESede,encrypt2, entryYi.getKey(), entry.getValue());
        log.info(new String(decrypt2));
    }
}
