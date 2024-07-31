package com.rookie.bigdata.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * @Class TimeUtils
 * @Description
 * @Author rookie
 * @Date 2024/7/31 17:12
 * @Version 1.0
 */
public class TimeUtils {

    private TimeUtils(){

    }


    public static Date now(){
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String format(String pattern, LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).orElse(LocalDateTime.now()).format(DateTimeFormatter.ofPattern(pattern, Locale.CHINA));
    }


    public static LocalDateTime fromTimestamp(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, OffsetDateTime.now().getOffset());
    }


    public static LocalDateTime fromDate(Date date) {
        return Objects.requireNonNull(date, "Parameter date cannot be null.").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
