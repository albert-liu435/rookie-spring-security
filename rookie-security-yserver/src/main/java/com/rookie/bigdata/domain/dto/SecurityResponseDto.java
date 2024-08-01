package com.rookie.bigdata.domain.dto;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.rookie.bigdata.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 * @Class SecurityResponseDto
 * @Description
 * @Author rookie
 * @Date 2024/8/1 9:42
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class SecurityResponseDto {

    /**
     * {@link HttpStatus}
     */
    private HttpStatus httpStatus;

    /**
     * 消息
     */
    private String message;

    /**
     * 时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 数据
     */
    private Object data;

    /**
     * @return { timestamp: '', status: '', message: '', data: {} }
     */
    @Override
    public String toString() {
        // 使用 LinkedHashMap 保证序列化顺序
        return new JSONObject(new LinkedHashMap<>(4))
                .fluentPut("timestamp", TimeUtils.format("yyyy-MM-dd HH:mm:ss", this.timestamp))
                .fluentPut("status", this.httpStatus.value())
                .fluentPut("message", this.message)
                .fluentPut("data", JSON.toJSONString(data))
                .toString();
    }
}
