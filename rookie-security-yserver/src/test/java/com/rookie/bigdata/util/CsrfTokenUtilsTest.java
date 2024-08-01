package com.rookie.bigdata.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class CsrfTokenUtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/8/1 9:29
 * @Version 1.0
 */
@Slf4j
class CsrfTokenUtilsTest {


    @Test
    void create(){

        log.info("uuid:{}",CsrfTokenUtils.create());
    }

}
