package com.rookie.bigdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.context.DelegatingApplicationListener;

/**
 * @Class EventConfig
 * @Description
 * @Author rookie
 * @Date 2024/7/2 14:49
 * @Version 1.0
 */
@Configuration
@Slf4j
public class EventConfig {






//    @Bean
//    @Order(value = Ordered.HIGHEST_PRECEDENCE)
//    public static DelegatingApplicationListener delegatingApplicationListener() {
//        DelegatingApplicationListener d = new DelegatingApplicationListener();
//
//        d.addListener(new SmartApplicationListener() {
//            @Override
//            public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
//                return AuthenticationSuccessEvent.class.isAssignableFrom(eventType);
////                        || ApplicationPreparedEvent.class.isAssignableFrom(eventType)
////                        || ApplicationFailedEvent.class.isAssignableFrom(eventType);
//            }
//
//            @Override
//            public void onApplicationEvent(ApplicationEvent event) {
//
//                log.info("成功event1: {}", event);
//            }
//        });
//
//        return d;
//    }

//    @Autowired
//    private DelegatingApplicationListener delegatingApplicationListener;


//    @Bean
//    public SmartApplicationListener createSmartApplicationListener() {
//        SmartApplicationListener mySmartApplicationListener = new SmartApplicationListener() {
//
//            @Override
//            public void onApplicationEvent(ApplicationEvent event) {
//
//                log.info("成功event: {}", event);
//
//            }
//
//            @Override
//            public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
//
//                return AuthenticationSuccessEvent.class.isAssignableFrom(eventType);
////                        || ApplicationPreparedEvent.class.isAssignableFrom(eventType)
////                        || ApplicationFailedEvent.class.isAssignableFrom(eventType);
//            }
//        };
//
//
////        delegatingApplicationListener.addListener(mySmartApplicationListener);
//        return mySmartApplicationListener;
//    }


}
