package com.rookie.bigdata.util;

import com.rookie.bigdata.config.ApplicationConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * @Class RequestUtils
 * @Description
 * @Author rookie
 * @Date 2024/7/31 17:01
 * @Version 1.0
 */
public class RequestUtils {

    private RequestUtils() {

    }


    public static String getURI(HttpServletRequest request) {
        return StringUtils.replace(request.getRequestURI(), ApplicationConfiguration.CONTEXT_PATH, "");

    }

}
