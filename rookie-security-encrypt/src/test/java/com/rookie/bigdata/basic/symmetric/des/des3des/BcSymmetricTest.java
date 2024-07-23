//package com.rookie.bigdata.basic.symmetric.des.des3des;
//
//
//
//import org.junit.jupiter.api.Test;
//
//import java.util.Base64;
//import java.util.Map;
//
//
///**
// * @Class BcSymmetricTest
// * @Description
// * @Author rookie
// * @Date 2024/7/23 15:38
// * @Version 1.0
// */
//public class BcSymmetricTest {
//
//    AbstractDHCoder abstractDHCoder=null;
////    @Before
////    public void setUp(){
////        abstractDHCoder=new BcDHCoder();
////    }
//
//    @Test
//    public void initKey() throws Exception {
//        abstractDHCoder=new BcDHCoder();
//        String msg="我是密文";
//        // 甲方构建密钥对
//        Map.Entry<byte[], byte[]> entry = AbstractDHCoder.initKey();
//        System.out.println("甲方 pubkey："+ Base64.getEncoder().encodeToString(entry.getKey()));
//        System.out.println("甲方 prikey："+ Base64.getEncoder().encodeToString(entry.getValue()));
//
//        System.out.println("甲方将 公钥 发送给 乙方");
//        System.out.println("乙方 使用甲方pubkey构建密钥对");
//        Map.Entry<byte[], byte[]> entryYi = AbstractDHCoder.initKeyByPubKey(entry.getKey());
//        System.out.println("乙方 pubkey："+ Base64.getEncoder().encodeToString(entryYi.getKey()));
//        System.out.println("乙方 prikey："+ Base64.getEncoder().encodeToString(entryYi.getValue()));
//
//        System.out.println("乙方 pubkey 发送给 甲方");
//
//        System.out.println("甲方加密数据，使用自己私钥以及 对方公钥");
//        byte[] encrypt = abstractDHCoder.encrypt(AbstractDHCoder.AES,msg.getBytes(), entryYi.getKey(), entry.getValue());
//        System.out.println("甲方将加密数据，发送给乙方");
//        System.out.println("乙方 使用自己私钥以及 对方公钥 解密");
//        byte[] decrypt = abstractDHCoder.decrypt(AbstractDHCoder.AES,encrypt, entry.getKey(), entryYi.getValue());
//        System.out.println(new String(decrypt));
//
////        System.out.println("乙方回发消息");
//        byte[] encrypt2 = abstractDHCoder.encrypt(AbstractDHCoder.AES,"乙方回发消息".getBytes(), entry.getKey(), entryYi.getValue());
//
//        byte[] decrypt2 = abstractDHCoder.decrypt(AbstractDHCoder.AES,encrypt2, entryYi.getKey(), entry.getValue());
//        System.out.println(new String(decrypt2));
//    }
//}
