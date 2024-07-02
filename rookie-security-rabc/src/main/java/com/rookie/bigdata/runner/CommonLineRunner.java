package com.rookie.bigdata.runner;

import com.rookie.bigdata.event.AbstractAuthenticationEventListener;
import com.rookie.bigdata.event.AuthenticationSuccessEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Class MyCommandLineRunner
 * @Description
 * @Author rookie
 * @Date 2024/7/2 16:31
 * @Version 1.0
 */
@Component
@Slf4j
public class CommonLineRunner implements CommandLineRunner {

    //与之对应的为DefaultAuthenticationEventPublisher
    @Autowired
    private DelegatingApplicationListener delegatingApplicationListener;

    @Override
    public void run(String... args) throws Exception {

        //添加监听器
        delegatingApplicationListener.addListener(new AuthenticationSuccessEventListener());
        delegatingApplicationListener.addListener(new AbstractAuthenticationEventListener());

        log.info("启动任务：{} ", args);
    }
}
