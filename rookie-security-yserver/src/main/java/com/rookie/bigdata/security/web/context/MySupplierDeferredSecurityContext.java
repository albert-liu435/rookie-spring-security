package com.rookie.bigdata.security.web.context;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import java.util.function.Supplier;

/**
 * @Class MySupplierDeferredSecurityContext
 * @Description
 * @Author rookie
 * @Date 2024/7/29 15:38
 * @Version 1.0
 */
@Slf4j
public class MySupplierDeferredSecurityContext implements DeferredSecurityContext {

    private final Supplier<SecurityContext> supplier;

    private final SecurityContextHolderStrategy strategy;

    private SecurityContext securityContext;

    private boolean missingContext;


    public MySupplierDeferredSecurityContext(Supplier<SecurityContext> supplier, SecurityContextHolderStrategy strategy) {
        this.supplier = supplier;
        this.strategy = strategy;
    }



    @Override
    public SecurityContext get() {
        init();
        return this.securityContext;
    }

    @Override
    public boolean isGenerated() {
        init();
        return this.missingContext;
    }

    private void init() {
        if (this.securityContext != null) {
            return;
        }

        this.securityContext = this.supplier.get();
        this.missingContext = (this.securityContext == null);
        if (this.missingContext) {
            this.securityContext = this.strategy.createEmptyContext();
//            if (log.isTraceEnabled()) {
//                log.trace(LogMessage.format("Created %s", this.securityContext));
//            }
        }
    }

}
