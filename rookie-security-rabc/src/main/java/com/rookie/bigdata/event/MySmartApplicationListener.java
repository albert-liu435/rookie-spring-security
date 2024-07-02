package com.rookie.bigdata.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

/**
 * @Class MySmart
 * @Description
 * @Author rookie
 * @Date 2024/7/2 16:33
 * @Version 1.0
 */
@Slf4j
public class MySmartApplicationListener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return AuthenticationSuccessEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("成功event: {}", event);
    }
}
