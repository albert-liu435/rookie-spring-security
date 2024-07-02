package com.rookie.bigdata.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

/**
 * @Class AbstractAuthenticationEventListener
 * @Description AbstractAuthenticationEvent 可以做更细的细分 参考DefaultAuthenticationEventPublisher里面的异常信息
 * @Author rookie
 * @Date 2024/7/2 17:21
 * @Version 1.0
 */
@Slf4j
public class AbstractAuthenticationEventListener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return AbstractAuthenticationEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        log.info("失败event: {}", event);

    }
}
