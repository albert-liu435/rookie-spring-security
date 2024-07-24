package com.rookie.bigdata.basic.cert;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import com.rookie.bigdata.utils.WriterFileUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class CertificatePKCS12CoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 17:20
 * @Version 1.0
 * <p>
 * pem:https://blog.csdn.net/u014644574/article/details/122628383
 */
class CertificatePKCS12CoderTest {


    Class<CertificatePKCS12Coder> certificatePKCS12CoderClass = CertificatePKCS12Coder.class;

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


    @Test
    void testGetPrivateKey() throws Exception {


        String fileName = "fire.pkcs12";
        //pkcs12文件路径
        String path = WriterFileUtils.filePath("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", fileName);

        String storepass = "13987664391";
        String keyAlias = "fire";

        PrivateKey key = CertificatePKCS12Coder.getPrivateKey(path, keyAlias, storepass);
        System.out.println(key.toString());
//        System.out.println(key.);
        String privateKeyStr = Base64Encoder.encode(key.getEncoded());
        System.out.println();
        System.out.println("-----BEGIN PRIVATE KEY-----");
        System.out.println(privateKeyStr);
        System.out.println("-----END PRIVATE KEY-----");


    }

    @Test
    void testGetgetPublicKey() throws Exception {


        String fileName = "fire.pkcs12";
        //pkcs12文件路径
        String path = WriterFileUtils.filePath("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", fileName);

        String storepass = "13987664391";
        String keyAlias = "fire";

        PublicKey key = CertificatePKCS12Coder.getPublicKey(path, keyAlias, storepass);
        System.out.println(key.toString());
        String publicKeyStr = Base64Encoder.encode(key.getEncoded());
        System.out.println();
        System.out.println("-----BEGIN Public KEY-----");
        System.out.println(publicKeyStr);
        System.out.println("-----END Public KEY-----");


    }

    /**
     * 导出公钥与私钥
     * <p>
     * https://www.cnblogs.com/aigeileshei/p/8855873.html
     *
     * @throws Exception
     */
    @Test
    void testExport() throws Exception {

        String fileName = "fire.pkcs12";

        String storepass = "13987664391";
        String keyAlias = "fire";
//        Base64Encoder base64Encoder = new Base64Encoder();
        KeyStore keystore = KeyStore.getInstance("PKCS12");

        keystore.load(WriterFileUtils.readFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", fileName), storepass.toCharArray());


//        keystore.load(CertificateCoderTest.class.getResourceAsStream("/key/home.pkcs12"), storepass.toCharArray());
        PrivateKey privateKey = (PrivateKey) keystore.getKey(keyAlias, storepass.toCharArray());
//        System.out.println(key.toString());
//        String privateKeyStr = base64Encoder.encode(key.getEncoded());
//        System.out.println();
//        System.out.println("-----BEGIN PRIVATE KEY-----");
//        System.out.println(privateKeyStr);
//        System.out.println("-----END PRIVATE KEY-----");

        String privateKeyPath = WriterFileUtils.writeFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", "privateKey.txt");

        Certificate certificate = keystore.getCertificate(keyAlias);
        PublicKey publicKey = certificate.getPublicKey();
//        System.out.println(publicKey);

        String publicKeyPath = WriterFileUtils.writeFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", "publicKey.txt");

        CertificatePKCS12Coder.exportPrivateKey(privateKey, privateKeyPath);
        CertificatePKCS12Coder.exportPublicKey(publicKey, publicKeyPath);

    }


    @Test
    void testLoadPrivateKey() throws Exception {
//        InputStream inputStream = WriterFileUtils.readFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", "privateKey.txt");
//
//        ByteArrayOutputStream array = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = inputStream.read(buffer)) != -1) {
//            array.write(buffer, 0, length);
//        }
//
//        String privateKeyStr = array.toString("utf-8")
//                .replace("-----BEGIN PRIVATE KEY-----", "")
//                .replace("-----END PRIVATE KEY-----", "")
//                .replaceAll("\\s+", "");
//
//        CertificateFactory certificateFactory = CertificateFactory.getInstance(CertificateCoder.X509);
//        FileInputStream in = new FileInputStream(certificatePath);
//
//        Certificate certificate = certificateFactory.generateCertificate(inputStream);
//        in.close();
//
//
//        Base64Decoder decoder = new Base64Decoder();
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoder.decode(privateKeyStr));
//        PrivateKey privateKey = kf.generatePrivate(keySpec);


        InputStream inputStream = WriterFileUtils.readFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", "publicKey.txt");

        CertificateFactory certificateFactory = CertificateFactory.getInstance(CertificateCoder.X509);
//        FileInputStream in = new FileInputStream(certificatePath);

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            array.write(buffer, 0, length);
        }

        String privateKeyStr = array.toString("utf-8")
                .replace("-----Begin Public Key-----", "")
                .replace("-----End Public Key-----", "");
//                .replaceAll("\\s+", "");

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(privateKeyStr.getBytes(StandardCharsets.UTF_8));

        Certificate certificate = certificateFactory.generateCertificate(byteArrayInputStream);
//        in.close();
        PublicKey key = certificate.getPublicKey();

//        System.out.println(privateKey.get);
    }


    /**
     * https://www.cnblogs.com/asker009/p/14325752.html
     */
    @Test
    void testLoadPublicKey() throws Exception{
        String str = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiygpX1UmhG0V5kPf/knWGtXVXdi1aVN5G3G4oB/WbxLcWH5yPioVBI95UOqiuYHloYJITQTkRVs73euzi2ZnAAJDtQdEJ4kgeUbKDIXoVdfBjpyq7Yjr+ZU4zJM+GEJtf3qZpHx10vJ8ntMXeWVv9dw9JGq4q2uiRZPRV7bi1qRQpRV4Yx6xrYFVnvEZ5o1ezq4XHw4d9pX1u4wOpYFkrAWr4dV5bBqAtQyXiwJU6lItA5wVGR53S+innLgBASUdNDhF8dGmBCsZoFeyDge2MsSvnRIJRMDR5TWCMnch2c3UOfoo3EzBDmSVF3W5M1Rc03bhBFPCCQyXaFosz59w1QIDAQAB";
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(new Base64Decoder().decode(str));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        String str2="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCLKClfVSaEbRXmQ9/+SdYa1dVd2LVpU3kbcbigH9ZvEtxYfnI+KhUEj3lQ6qK5geWhgkhNBORFWzvd67OLZmcAAkO1B0QniSB5RsoMhehV18GOnKrtiOv5lTjMkz4YQm1/epmkfHXS8nye0xd5ZW/13D0karira6JFk9FXtuLWpFClFXhjHrGtgVWe8RnmjV7OrhcfDh32lfW7jA6lgWSsBavh1XlsGoC1DJeLAlTqUi0DnBUZHndL6KecuAEBJR00OEXx0aYEKxmgV7IOB7YyxK+dEglEwNHlNYIydyHZzdQ5+ijcTMEOZJUXdbkzVFzTduEEU8IJDJdoWizPn3DVAgMBAAECggEBAIXyxsBJSqY2Ctp3g2lW0Y1LP/Lx4s3b17ufH8SiYNrSIN/d90H3Z/vXz0MgWDqQ2uU4bzgT+Vb5lwrrEwygKY1MoLfkoqobyPJ0PZEIZLgc0uffzniPT/YSuXXSVfcsGhKXhMVcOF4xo9uN/g6QodDbis8/GzuGg9DNlLkGBhvs60nTSFujVrCrkVZXI6HzJ2xKUrUvdBth+2TX1oRfea2narFIm1Rb8QYXaUFS2SDug9kKvVnt9kGmWCPiSLeQOpWhaDK/iX0JuVpJLtELdnmNTL04lFSc4DY9GdL34+oxb/xGzEIhQ8jI+Nu4VjAGsCWQn+WJvASYmtuUmDeCxZECgYEAw7EQ1crq5Jmdg+HPTorNfaX5vMyhbjPArym67jqmkcQvwnLCylYlog7iKBkLewm4mSb97e2nHMxsU15SsqvzDeN5MCW4VWAgsJa5bw2muSRtfcJGfL6N8kbhTo9vGz4LVKpLr0t9Mm3IRW0ZLs6d9/7+z8qNeM7cPZNPtOHr768CgYEAtgrUj/bWiXQrwLg2ML23buK8cMoc6mnGKxfRX2oI+vI4R0D+BIdf7rohN9BjJKkaYL98itmLYilF+dplD2BX438v6JsYNdKZIaXT69t5Oa38hyUy4KrxncBUjv35X1/js2Z9JIVP5fs/svjXBaC0HuJDa4BH+dumN04nCcaJZLsCgYA7ixPpSV3hgtBHwZyXdGqEMc1kYgs4gey+H39uX1AxiDVUACE++alnvwmXU8+Hd3TAvPmEvMwdWiIX19pn3SQOdMl6GfAVojMwKBxyKwAw02GxYzyZVk3Qizqg8cnHy0AdWde/lcOZZYgLf9+qu1mbjO4xgZs8SA0Be2JEUn28pwKBgGGETbfND8HUNHfpdkYH6xKAbVeyPWkpPlnpSWRmZecgCBvLage9CRvWI2NFw0ZpO9Ml4K50uODdoTmlz0s8XfhlMqHVK1Pxu8w1i9+l0zudw1AWEjod+I578BAlZwe7UzvQJCuDKnjVbZsPCAhgvPxqUplsv6HOEy0/NVRcQU9vAoGAS2TjtYudV7UgNr46MjsUN9KEUFx0q6ELD8AWznY+xztXMK0eGBjdJMOpfzw9dsXsTRmgO/YlnL/PheQRekqB7T0q3atk3yLjVWd1f0lH5aLeejxH9UJPEuDt0bBeqVaSXMUH3heI1Ft4nmFsOsqPdaKdS9gB3JliXhbIC/eMiUQ=";
        X509EncodedKeySpec keySpec1 = new X509EncodedKeySpec(new Base64Decoder().decode(str2));
        KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory1.generatePrivate(keySpec1);

//        RSAPrivateKeySpec rsaPrivateKeySpec=new RSAPrivateKeySpec();
//        rsaPrivateKeySpec.get

    }


    @Test
    void testGetPrivateKey2() throws Exception {


        String fileName = "fire.pkcs12";

        String storepass = "13987664391";
        String keyAlias = "fire";
        Base64Encoder base64Encoder = new Base64Encoder();
        KeyStore keystore = KeyStore.getInstance("PKCS12");

        keystore.load(WriterFileUtils.readFile("/src/main/resources/com/rookie/bigdata/basic/cert/", fileName), storepass.toCharArray());


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

        String publicKeyString = base64Encoder.encode(publicKey.getEncoded());

        System.out.println("-----BEGIN Public KEY-----");
        System.out.println(publicKeyString);
        System.out.println("-----END Public KEY-----");
        // 打印certificate的base64编码
        String certificateString = base64Encoder.encode(certificate.getEncoded());
        System.out.println();
        System.out.println("-----BEGIN CERTIFICATE-----");
        System.out.println(certificateString);
        System.out.println("-----END CERTIFICATE-----");
    }


}


//import sun.misc.BASE64Encoder;
//
//        import java.io.FileInputStream;
//        import java.security.PublicKey;
//        import java.security.cert.CertificateFactory;
//        import java.security.cert.X509Certificate;
//
//public class Cerdemo {
//
//    public static void main(String[] args) throws Exception{
//
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        X509Certificate cert = (X509Certificate)cf.generateCertificate(new FileInputStream("my.cer"));
//        PublicKey publicKey = cert.getPublicKey();
//        BASE64Encoder base64Encoder=new BASE64Encoder();
//        String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
//        System.out.println("-----------------公钥--------------------");
//        System.out.println(publicKeyString);
//        System.out.println("-----------------公钥--------------------");
//    }
//}
//https://blog.51cto.com/u_14444/6510732
