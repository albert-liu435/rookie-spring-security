package com.rookie.bigdata.config;

import com.rookie.bigdata.utils.WriterFileUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class SecurityConfigurationTest
 * @Description
 * @Author rookie
 * @Date 2024/7/31 15:19
 * @Version 1.0
 */

//@Test
class SecurityConfigurationTest {

    @Test
    void testRSAPrivateKey() throws Exception {
//        String privateKeyPath = WriterFileUtils.writeFile("/src/main/resources/", "app.key");
        InputStream inputStream = WriterFileUtils.readFile("/src/main/resources/", "app.key");

//        FileInputStream inputStream = new FileInputStream(privateKeyPath);
//        InputStream inputStream = ResourcesUtil.getResourceAsStream(path);

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            array.write(buffer, 0, length);
        }

        // \\s+表示出现空白匹配
        String privateKey = array.toString("utf-8")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) kf.generatePrivate(
                new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
    }


    @Test
    void testRSAPublicKey() throws Exception {
        InputStream inputStream = WriterFileUtils.readFile("/src/main/resources/", "app.pub");


        ByteArrayOutputStream array = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            array.write(buffer, 0, length);
        }

        // \\s+表示出现空白匹配
        String privateKey = array.toString("utf-8")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        byte[] x509 = Base64.getDecoder().decode(privateKey.toString());

        RSAPublicKey rsaPublicKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(x509));

    }

}
