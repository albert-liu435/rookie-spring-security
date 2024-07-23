package com.rookie.bigdata.basic.symmetric.des.des3des;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @Class JdkSymmetricBlowfishTest
 * @Description
 * @Author rookie
 * @Date 2024/7/23 15:42
 * @Version 1.0
 */

public class JdkSymmetricBlowfishTest {
    String msg = "测试数据2222";
    Map.Entry<String, String> padding = null;
    byte[] key = null;
    byte[] iv = null;
    byte[] encrypt = null;
    byte[] decrypt = null;
    AbstractSymmetric symmetric=null;
//    @Before
//    public void before(){
//        symmetric=new JdkSymmetric();
//    }

//    SunJCE: Cipher.Blowfish -> com.sun.crypto.provider.BlowfishCipher
//    attributes: {SupportedPaddings=NOPADDING|PKCS5PADDING|ISO10126PADDING,
//    SupportedKeyFormats=RAW, SupportedModes=ECB|CBC|PCBC|CTR|CTS|CFB|OFB|CFB8|CFB16|CFB24|CFB32|CFB40|CFB48|CFB56|CFB64
//    |OFB8|OFB16|OFB24|OFB32|OFB40|OFB48|OFB56|OFB64}

    @Test
    public void encryptAll() throws Exception {
        symmetric=new JdkSymmetric();
        System.out.println("原文:" + msg);
        List<Map.Entry<String, String>> paddingListMap = new ArrayList<>();
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/ECB/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/ECB/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/ECB/ISO10126PADDING"));

        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/CBC/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/CBC/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/CBC/ISO10126PADDING"));


        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/PCBC/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/PCBC/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/PCBC/ISO10126PADDING"));

        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/CTR/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/CTR/PKCS5PADDING"));

        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/CTS/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("Blowfish", "Blowfish/CTS/PKCS5PADDING"));
        // paddingListMap.add(new AbstractMap.SimpleEntry<>("AES","AES/CTR/ISO10126PADDING"));//不支持
        // paddingListMap.add(new AbstractMap.SimpleEntry<>("AES","AES/CTS/ISO10126PADDING"));//不支持
        for (Map.Entry<String, String> entry : paddingListMap) {
            try {
                boolean b = encryptCheck(entry);
                if (b) {
                    System.out.println(entry.getValue() + ":支持");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(entry.getValue() + ":不支持");
            }
        }
    }

    boolean encryptCheck(Map.Entry<String, String> entry) throws Exception {
        padding = entry;
        key = symmetric.initKey(padding);//Base64.getDecoder().decode("koY9NPFJGf4=");//
        iv = AbstractSymmetric.initIv();
        System.out.println("key:" + Base64.getEncoder().encodeToString(key));
        System.out.println("iv:" + Base64.getEncoder().encodeToString(iv));
        encrypt = symmetric.encrypt(padding, key, iv, msg.getBytes("utf-8"));
        System.out.println("encrypt:" + Base64.getEncoder().encodeToString(encrypt));
        decrypt = symmetric.decrypt(padding, key, iv, this.encrypt);
        //System.out.println("decrypt:" + Base64.getEncoder().encodeToString(encrypt));
        //System.out.println("解密原文:" + new String(decrypt, "utf-8"));
        return true;
    }
}
