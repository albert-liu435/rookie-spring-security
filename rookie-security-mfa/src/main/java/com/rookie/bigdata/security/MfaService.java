package com.rookie.bigdata.security;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

/**
 * @Class MfaService
 * @Description
 * @Author rookie
 * @Date 2024/7/12 17:43
 * @Version 1.0
 */
@Service
public class MfaService {

    public boolean check(String hexKey, String code) {
        try {
            return TimeBasedOneTimePasswordUtil.validateCurrentNumberHex(hexKey, Integer.parseInt(code), 10000);
        }
        catch (GeneralSecurityException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
