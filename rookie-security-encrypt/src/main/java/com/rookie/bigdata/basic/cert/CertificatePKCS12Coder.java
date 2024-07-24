package com.rookie.bigdata.basic.cert;

import cn.hutool.core.codec.Base64Encoder;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

/**
 * @Class CertificatePKCS12Coder
 * @Description
 * @Author rookie
 * @Date 2024/7/24 17:18
 * @Version 1.0
 */
public class CertificatePKCS12Coder {


    /**
     * Java密钥库(Java Key Store，PKCS12)KEY_STORE
     */
    public static final String KEY_STORE = "PKCS12";

    /**
     * 由KeyStore获得私钥
     *
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String keyStorePath, String alias, String password) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, password);
        PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
        return key;
    }


    public static PublicKey getPublicKey(String keyStorePath, String alias, String password) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, password);

        Certificate certificate = ks.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();
//        PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
//        return key;
        return publicKey;
    }

    /**
     * 获得KeyStore
     *
     * @param keyStorePath
     * @param password
     * @return
     * @throws Exception
     */
    public static KeyStore getKeyStore(String keyStorePath, String password) throws Exception {
        FileInputStream is = new FileInputStream(keyStorePath);
        KeyStore ks = KeyStore.getInstance(KEY_STORE);
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }


    //导出私钥
    public static void exportPrivateKey(PrivateKey privateKey, String exportFile) throws Exception {
        Base64Encoder encoder = new Base64Encoder();

        String encoded = encoder.encode(privateKey.getEncoded());
        FileWriter fileWriter = new FileWriter(exportFile);
        fileWriter.write("-----Begin Private Key-----\r\n");//非必须
        fileWriter.write(encoded);
        fileWriter.write("\r\n-----End Private Key-----");//非必须
        fileWriter.close();
    }

    //导出公钥
    public static void exportPublicKey(PublicKey publicKey, String exportFile) throws Exception {
        Base64Encoder encoder = new Base64Encoder();
        String encoded = encoder.encode(publicKey.getEncoded());
        FileWriter fileWriter = new FileWriter(exportFile);
        fileWriter.write("-----Begin Public Key-----\r\n");//非必须
        fileWriter.write(encoded);
        fileWriter.write("\r\n-----End Public Key-----");//非必须
        fileWriter.close();
    }


}
