package com.rookie.bigdata.util;

import com.rookie.bigdata.domain.SecurityResponseDtoBuilder;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Class ResponseUtils
 * @Description
 * @Author rookie
 * @Date 2024/8/1 9:34
 * @Version 1.0
 */
public final class ResponseUtils {
    private static final String RESPONSE_CONTENT_TYPE = "application/json;charset=UTF-8";


    private static void preHandle(HttpServletResponse response) {
        response.setContentType(RESPONSE_CONTENT_TYPE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    }

    private static void wrapResponse(HttpStatus httpStatus, HttpServletResponse response, String message) throws IOException {
        preHandle(response);
        response.setStatus(httpStatus.value());
        try (final PrintWriter writer = response.getWriter()) {
            writer.write(
                    SecurityResponseDtoBuilder.of().with(securityResponseDtoBuilder -> {
                        securityResponseDtoBuilder.httpStatus = httpStatus;
                        securityResponseDtoBuilder.message = message;
                    }).build().toString()
            );

            writer.flush();
        }
    }

    /**
     * Description: 包装 HttpStatus 为 200 的 Response
     *
     * @param response {@link HttpServletResponse}
     * @param message  消息
     * @return void
     * @author LiKe
     * @date 2020-05-09 10:27:59
     */
    public static void okResponse(HttpServletResponse response, String message) throws IOException {
        wrapResponse(HttpStatus.OK, response, message);
    }

    /**
     * Description: 包装 HttpStatus 为 401 的 Response
     *
     * @param response {@link HttpServletResponse}
     * @param message  消息
     * @return void
     * @author LiKe
     * @date 2020-05-08 18:31:22
     */
    public static void unauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        wrapResponse(HttpStatus.UNAUTHORIZED, response, message);
    }

    /**
     * Description: 包装 HttpStatus 为 403 的 Response
     *
     * @param response {@link HttpServletResponse}
     * @param message  消息
     * @return void
     * @author LiKe
     * @date 2020-05-08 18:31:09
     */
    public static void forbiddenResponse(HttpServletResponse response, String message) throws IOException {
        wrapResponse(HttpStatus.FORBIDDEN, response, message);
    }

    private ResponseUtils() {
    }
}

