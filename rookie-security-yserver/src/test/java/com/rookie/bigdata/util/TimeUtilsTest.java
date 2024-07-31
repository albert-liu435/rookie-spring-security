package com.rookie.bigdata.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class TimeUtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/7/31 17:15
 * @Version 1.0
 */
@Slf4j
class TimeUtilsTest {


    @Test
    void testZoneId(){

        ZoneId of = ZoneId.of("America/New_York");
        log.info("zoneIdof:{}",of);
        ZoneId zoneId = ZoneId.systemDefault();
        log.info("zoneId:{}",zoneId);

    }

    @Test
    void now() {
        ZoneId of = ZoneId.of("America/New_York");
        log.info("zoneIdof:{}",of);
        Date dataAmerica=Date.from(LocalDateTime.now().atZone(of).toInstant());
        log.info("dataAmerica:{}",dataAmerica);
        ZoneId zoneId = ZoneId.systemDefault();
        log.info("zoneId:{}",zoneId);
        Date dataChina=Date.from(LocalDateTime.now().atZone(of).toInstant());
        log.info("dataChina:{}",dataChina);

    }

    @Test
    void format() {

        String format = TimeUtils.format("yyyy-MM-dd HH:mm:ss", LocalDateTime.now());
        log.info("format:{}",format);
    }

    @Test
    void fromTimestamp() {
    }

    @Test
    void fromDate() {
    }
}
