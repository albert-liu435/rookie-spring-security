package com.rookie.bigdata.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.TextEncryptor;

/**
 * @Class RC128TextEncryptor
 * @Description
 * @Author rookie
 * @Date 2024/7/24 14:31
 * @Version 1.0
 */
public class RC128TextEncryptor implements TextEncryptor {
    private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    public RC128TextEncryptor() {
        this.encryptor.setAlgorithm("PBEWITHSHA1ANDRC4_128");
    }

    public void setPassword(String password) {
        this.encryptor.setPassword(password);
    }

    public void setPasswordCharArray(char[] password) {
        this.encryptor.setPasswordCharArray(password);
    }

    public String encrypt(String message) {
        return this.encryptor.encrypt(message);
    }

    public String decrypt(String encryptedMessage) {
        return this.encryptor.decrypt(encryptedMessage);
    }
}
