package com.rookie.bigdata;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Random;

import static com.j256.twofactorauth.TimeBasedOneTimePasswordUtil.DEFAULT_OTP_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Class TimeBasedOneTimePasswordUtilTest
 * @Description
 * @Author rookie
 * @Date 2024/7/17 18:04
 * @Version 1.0
 */
public class TimeBasedOneTimePasswordUtilTest {

    @Test
    public void testZeroPrepend() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int num = random.nextInt(1000000);
            /**
             * NOTE: Did a speed test of these and the zeroPrepend is ~13x faster.
             */
            //assertEquals(String.format("%06d", num), TimeBasedOneTimePasswordUtil.zeroPrepend(num, 6));
        }
    }

    @Test
    void test01()throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey key = generator.generateKey();
        AesBytesEncryptor encryptor = new AesBytesEncryptor(key, KeyGenerators.secureRandom(12), AesBytesEncryptor.CipherAlgorithm.GCM);

        String hexSecret = "80ed266dd80bcd32564f0f4aaa8d9b149a2b1eaa";
        String encrypted = new String(Hex.encode(encryptor.encrypt(hexSecret.getBytes())));


        Integer code = TimeBasedOneTimePasswordUtil.generateCurrentNumberHex(encrypted);
        Integer code1 =  TimeBasedOneTimePasswordUtil.generateNumberHex(encrypted, System.currentTimeMillis(), 30, DEFAULT_OTP_LENGTH);
        System.out.println(code1);

//        Thread.sleep(12000);

        boolean b = TimeBasedOneTimePasswordUtil.validateCurrentNumberHex(encrypted, code1, 110000);
        System.out.println(b);

    }

}
