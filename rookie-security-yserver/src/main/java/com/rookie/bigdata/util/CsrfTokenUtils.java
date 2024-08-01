package com.rookie.bigdata.util;

import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @Class CsrfTokenUtils
 * @Description
 * @Author rookie
 * @Date 2024/8/1 9:28
 * @Version 1.0
 */
public class CsrfTokenUtils {


    private CsrfTokenUtils() {

    }

    public static String create() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

}
