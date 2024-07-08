package com.rookie.bigdata.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Class ResourceController
 * @Description TODO
 * @Author rookie
 * @Date 2024/7/8 18:15
 * @Version 1.0
 */
@Controller
public class ResourceController {

    /**
     * 返回用户信息
     */
    @GetMapping("/api/userInfo")
    @ResponseBody
    public Authentication showUserInfoApi() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("username","张三");
        return "index";
    }
}
