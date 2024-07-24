package com.rookie.bigdata.basic.cert;

import cn.hutool.core.codec.Base64Encoder;
import com.rookie.bigdata.utils.WriterFileUtils;
import org.junit.jupiter.api.Test;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class CertificateCoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 16:01
 * @Version 1.0
 */
class CertificateCoderTest {

    Class<CertificateCoder> certificateCoderClass = CertificateCoder.class;


//[root@VM-0-4-centos ~]# keytool -genkeypair -alias fire -storetype PKCS12  -keyalg RSA -keystore fire.pkcs12 -storepass 13987664391 -validity 3650 -keysize 2048
//What is your first and last name?
//  [Unknown]:  liu.dm
//What is the name of your organizational unit?
//  [Unknown]:  com.home
//What is the name of your organization?
//  [Unknown]:  home
//What is the name of your City or Locality?
//  [Unknown]:  bj
//What is the name of your State or Province?
//  [Unknown]:  bj
//What is the two-letter country code for this unit?
//  [Unknown]:  cn
//Is CN=liu.dm, OU=com.home, O=home, L=bj, ST=bj, C=cn correct?
//  [no]:  y

    /**
     * 5.从密钥库keystore里提取私钥和证书
     */
    @Test
    void testGetPrivateKey()throws Exception{



        String fileName="fire.pkcs12";

        String storepass = "13987664391";
        String keyAlias = "fire";
        Base64Encoder base64Encoder = new Base64Encoder();
        KeyStore keystore = KeyStore.getInstance("PKCS12");

        keystore.load(WriterFileUtils.readFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/",fileName), storepass.toCharArray());

//        keystore.load(CertificateCoderTest.class.getResourceAsStream("/key/home.pkcs12"), storepass.toCharArray());
        PrivateKey key = (PrivateKey) keystore.getKey(keyAlias, storepass.toCharArray());
        System.out.println(key.toString());
        String privateKeyStr = base64Encoder.encode(key.getEncoded());
        System.out.println();
        System.out.println("-----BEGIN PRIVATE KEY-----");
        System.out.println(privateKeyStr);
        System.out.println("-----END PRIVATE KEY-----");

        Certificate certificate = keystore.getCertificate(keyAlias);
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println(publicKey);

        // 打印certificate的base64编码
        String certificateString = base64Encoder.encode(certificate.getEncoded());
        System.out.println();
        System.out.println("-----BEGIN CERTIFICATE-----");
        System.out.println(certificateString);
        System.out.println("-----END CERTIFICATE-----");
    }


    //从证书中提取公钥BASE64编码字符串
    @Test
    void testGetPublicKeyFromCertificate()throws Exception{
        String fileName="fire.pkcs12";
        InputStream inputStream = WriterFileUtils.readFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/",fileName);
        CertificateFactory ft = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) ft.generateCertificate(inputStream);
        PublicKey publicKey = certificate.getPublicKey();
//        org.bouncycastle.util.encoders.Base64Encoder b64 = new org.bouncycastle.util.encoders.Base64Encoder();
//        String result = b64.encode(publicKey.getEncoded());
        Base64.Encoder encoder = Base64.getEncoder();
//        byte[] b = data.getBytes(ENCODING);
        String result = encoder.encodeToString(publicKey.getEncoded());
        System.out.println("-----BEGIN PUBLIC KEY-----");
        System.out.println(result);
        System.out.println("-----END PUBLIC KEY-----");
    }



    //导出证书
    @Test
    void testExport()throws Exception{

    }



    /**
     * 使用java代码生成密钥库
     * @throws
     */
//    @Test
//    void testCreateKeyStoreFile() throws Exception {
//
//        String fileName="home.keystore";
//
//        WriterFileUtils.writeFile("/src/main/resources/com/rookie/bigdata/basic/cert/",fileName);
//        final int keySize=2048;
//        final String commonName = "xu.dm";
//        final String organizationalUnit = "com.home";
//        final String organization = "home";
//        final String city = "km";
//        final String state = "yn";
//        final String country = "cn";
//        final long validity = 3650; // 10 years
//        final String alias = "home";
//        final String keyPassword = "13987664391";
//        // keytool工具
//        CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA1WithRSA");
//        // 通用信息
//        X500Name x500Name = new X500Name(commonName, organizationalUnit, organization, city, state, country);
//        // 根据密钥长度生成公钥和私钥
//        keyGen.generate(keySize);
//
//        PrivateKey privateKey = keyGen.getPrivateKey();
//
//        // 证书
//        X509Certificate certificate = keyGen.getSelfCertificate(x500Name, new Date(), (long) validity * 24 * 60 * 60);
//
//
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        keyStore.load(null,null);
//        keyStore.setKeyEntry(alias,privateKey,keyPassword.toCharArray(),new Certificate[]{certificate});
//
//        FileOutputStream outputStream = new FileOutputStream(filePath);
//        keyStore.store(outputStream,keyPassword.toCharArray());
//
//        outputStream.close();
//        System.out.println("keyStore file created ...");
//
//
////        String path = ResourceUtils.getURL("classpath:").getPath();
////        System.out.println(path);
//
//
//    }

}
