package com.rookie.bigdata.jasypt;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Class BasicPasswordEncryptorTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 14:59
 * @Version 1.0
 */
public class BasicPasswordEncryptorTest {

    @BeforeEach
    public void setUp() {
//        textEncryptor = new BasicPasswordEncryptor();
    }

    @Test
    public void encrypt() {
        BasicPasswordEncryptor textEncryptor = new BasicPasswordEncryptor();
        String encryptPassword = textEncryptor.encryptPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
        System.out.println(encryptPassword);
        boolean checkPassword = textEncryptor.checkPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7", encryptPassword);
        System.out.println(checkPassword);
    }

}
