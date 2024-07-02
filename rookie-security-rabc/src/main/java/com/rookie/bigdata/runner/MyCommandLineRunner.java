package com.rookie.bigdata.runner;

import com.rookie.bigdata.event.MySmartApplicationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.GenericApplicationListenerAdapter;
import org.springframework.context.event.SmartApplicationListener;
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
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private DelegatingApplicationListener delegatingApplicationListener;

    @Override
    public void run(String... args) throws Exception {


        delegatingApplicationListener.addListener(new MySmartApplicationListener());

        log.info("启动任务：{} ", args);
    }
}
