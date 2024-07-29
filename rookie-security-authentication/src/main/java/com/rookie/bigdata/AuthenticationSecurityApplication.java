package com.rookie.bigdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @Author rookie
 * @Description Hello Security application.
 * @Date 2024/6/26 21:10
 * @Version 1.0
 */
@SpringBootApplication
public class AuthenticationSecurityApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(AuthenticationSecurityApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AuthenticationSecurityApplication.class);
    }

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void run(String... args) throws Exception {
//        String[] beans = appContext.getBeanDefinitionNames();
//        Arrays.sort(beans);
//        for (String bean : beans) {
//            System.out.println(bean + " of Type :: " + appContext.getBean(bean).getClass());
//        }
    }

}
