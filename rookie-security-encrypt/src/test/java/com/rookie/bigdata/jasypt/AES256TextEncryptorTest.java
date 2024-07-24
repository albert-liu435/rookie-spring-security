package com.rookie.bigdata.jasypt;

import org.jasypt.util.text.AES256TextEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Class JasyptUtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 14:39
 * @Version 1.0
 */
class AES256TextEncryptorTest {


    AES256TextEncryptor textEncryptor;
    @BeforeEach
    /*static*/ void setUp(){
        textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }


    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //n6T6UM9+hLKg6QMl+r9snFvXULR3YAK2rhqQpxiAh+f0dkXVhcP6ak/Soy+9gAs8
        //b7WieTYlCCRgFIQNpt+zqKzye7Bs8Vl4s4qwIErWHQax93LFKK/V2jG5kIyiLOe+
        //7dCPEHZ5VN07eJ/kyHv5b6CiIcm8Q/P80GnS9l2/YqXwIat+lonCHtjACiK6dlsK
    }

    @Test
    public void decyptPwd() {
        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("n6T6UM9+hLKg6QMl+r9snFvXULR3YAK2rhqQpxiAh+f0dkXVhcP6ak/Soy+9gAs8"));
    }

}
