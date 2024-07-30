package com.rookie.bigdata.security.core.context;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;

import java.util.function.Supplier;

/**
 * @Class MyThreadLocalSecurityContextHolderStrategy
 * @Description
 * @Author rookie
 * @Date 2024/7/29 16:10
 * @Version 1.0
 */
public class MyThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    private static final ThreadLocal<Supplier<SecurityContext>> contextHolder = new ThreadLocal<>();

    private SecurityContextRepository securityContextRepository;

    public MyThreadLocalSecurityContextHolderStrategy(SecurityContextRepository mySecurityContextRepository) {
        this.securityContextRepository = mySecurityContextRepository;
    }

    @Override
    public void clearContext() {
        //将SecurityContext保存下来，方便下次请求使用
        SecurityContext securityContext = contextHolder.get().get();
        //将其设置到
        securityContextRepository.saveContext(securityContext, null, null);
        contextHolder.remove();
    }

    @Override
    public SecurityContext getContext() {
        return getDeferredContext().get();
    }

    @Override
    public Supplier<SecurityContext> getDeferredContext() {
        Supplier<SecurityContext> result = contextHolder.get();
        if (result == null) {
            SecurityContext context = createEmptyContext();
            result = () -> context;
            contextHolder.set(result);
        }
        return result;
    }

    @Override
    public void setContext(SecurityContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        contextHolder.set(() -> context);
    }

    @Override
    public void setDeferredContext(Supplier<SecurityContext> deferredContext) {
        Assert.notNull(deferredContext, "Only non-null Supplier instances are permitted");
        Supplier<SecurityContext> notNullDeferredContext = () -> {
            SecurityContext result = deferredContext.get();
            Assert.notNull(result, "A Supplier<SecurityContext> returned null and is not allowed.");
            return result;
        };
        contextHolder.set(notNullDeferredContext);
    }

    @Override
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }

}
