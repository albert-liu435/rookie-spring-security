package com.rookie.bigdata.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class PathVariableEnpointController
 * @Description https://blog.csdn.net/weixin_44188105/article/details/131696941
 * @Author rookie
 * @Date 2024/8/2 13:36
 * @Version 1.0
 */
@RestController
@Slf4j
public class PathVariableEnpointController {


    @RequestMapping("/resource/{appid}")
    public String endpointApp(@PathVariable(value = "appid") String appid) {
        log.info("appid:{}", appid);
        return "endpoint";
    }
//    }
}
