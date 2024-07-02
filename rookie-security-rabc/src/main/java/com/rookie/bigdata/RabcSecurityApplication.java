package com.rookie.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author rookie
 * @Description Hello Security application.
 * @Date 2024/6/26 21:10
 * @Version 1.0
 */
@SpringBootApplication
public class RabcSecurityApplication /*extends SpringBootServletInitializer implements CommandLineRunner*/ {

    public static void main(String[] args) {
//org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
//org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
//org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
//
//HttpSecurityConfiguration
//WebMvcSecurityConfiguration
//WebSecurityConfiguration
//入口：        SpringBootWebSecurityConfiguration#WebSecurityEnablerConfiguration

        //UserDetailsServiceAutoConfiguration
        SpringApplication.run(RabcSecurityApplication.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(HelloSecurityApplication.class);
//    }
//
//    @Autowired
//    private ApplicationContext appContext;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String[] beans = appContext.getBeanDefinitionNames();
//        Arrays.sort(beans);
//        for (String bean : beans) {
//            System.out.println(bean + " of Type :: " + appContext.getBean(bean).getClass());
//        }
//    }

}
