package com.rookie.bigdata;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class RestMfaController
 * @Description
 * @Author rookie
 * @Date 2024/7/18 9:10
 * @Version 1.0
 */

@RestController
public class RestMfaController {

    @Autowired
    private  BytesEncryptor encryptor;


    @SneakyThrows
    @RequestMapping("/code")
    public String aaa() {

        String hexSecret = "80ed266dd80bcd32564f0f4aaa8d9b149a2b1eaa";
        String encrypted = new String(Hex.encode(encryptor.encrypt(hexSecret.getBytes())));
        Integer code1 = TimeBasedOneTimePasswordUtil.generateCurrentNumberHex(hexSecret);

        String code = code1.toString();

        return code;
    }
}
