package com.rookie.bigdata.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class EnpointController
 * @Description
 * @Author rookie
 * @Date 2024/8/2 11:48
 * @Version 1.0
 */
//@Controller
@RestController
@Slf4j
public class EnpointController {

//    /**
//     * http://locahost:8080/endpoint
//     * @return
//     */
//    @RequestMapping("/endpoint")
//    public String endpointSource() {
//        return "endpoint";
//    }

    //    @Controller
//    public class MyController {
    @GetMapping("/endpoint")
    public String endpoint() {
        return "endpoint";
    }
//    }


    @GetMapping("/endpoint/{appid}")
    public String endpointApp(@PathVariable String appid) {
        log.info("appid:{}", appid);
        return "endpoint";
    }
//    }
}
