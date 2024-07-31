package com.rookie.bigdata.basic.cert;

import com.rookie.bigdata.utils.WriterFileUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Class CertificatePKCS12Coder2Test
 * @Description
 * @Author rookie
 * @Date 2024/7/31 15:53
 * @Version 1.0
 */
public class CertificatePKCS12Coder2Test {

    @Test
    void testRSAPrivateKey() throws Exception {
//        String privateKeyPath = WriterFileUtils.writeFile("/src/main/resources/", "app.key");
        InputStream inputStream = WriterFileUtils.readFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", "app.key");

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
        InputStream inputStream = WriterFileUtils.readFile("/src/main/resources/com/rookie/bigdata/basic/cert/pkcs/", "app.pub");


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
