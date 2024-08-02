//package com.rookie.bigdata.security.config.annotation.authentication.configuration;
//
//import com.rookie.bigdata.security.authentication.dao.MyDaoAuthenticationProvider;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.authentication.password.CompromisedPasswordChecker;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsPasswordService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * @Class MyInitializeUserDetailsBeanManagerConfigurer
// * @Description 仿照InitializeUserDetailsBeanManagerConfigurer写一个自己的InitializeUserDetailsBeanManagerConfigurer, 然后在AuthenticationSecurityConfiguration中进行一个实例化即可，参考
// * @Bean public static InitializeUserDetailsBeanManagerConfigurer initializeUserDetailsBeanManagerConfigurer(
// * ApplicationContext context) {
// * return new InitializeUserDetailsBeanManagerConfigurer(context);
// * }
// * @Author rookie
// * @Date 2024/7/31 9:49
// * @Version 1.0
// */
//@Order(MyInitializeUserDetailsBeanManagerConfigurer.DEFAULT_ORDER)
//public class MyInitializeUserDetailsBeanManagerConfigurer extends GlobalAuthenticationConfigurerAdapter {
//
//    //这个优先循序会影响到ProviderManager中的多个AuthenticationProvider的执行顺序
//    static final int DEFAULT_ORDER = Ordered.LOWEST_PRECEDENCE - 4999;
//
//    private final ApplicationContext context;
//
//    /**
//     * @param context
//     */
//    public MyInitializeUserDetailsBeanManagerConfigurer(ApplicationContext context) {
//        this.context = context;
//    }
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.apply(new MyInitializeUserDetailsManagerConfigurer());
//    }
//
//
//    class MyInitializeUserDetailsManagerConfigurer extends GlobalAuthenticationConfigurerAdapter {
//
//        private final Log logger = LogFactory.getLog(getClass());
//
//        @Override
//        public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//
//            //这里可以根据自己的需要，添加自定义实现AuthenticationProvider provider;
//            //伪代码
//            PasswordEncoder passwordEncoder = getBeanOrNull(PasswordEncoder.class);
//
//            AuthenticationProvider provider;
//            if (passwordEncoder != null) {
//                provider = new MyDaoAuthenticationProvider(passwordEncoder);
//            }else {
//                provider=new MyDaoAuthenticationProvider();
//            }
//            //通过打断点最终发现ProviderManager中的	private List<AuthenticationProvider> providers = Collections.emptyList();属性有两个AuthenticationProvider
//            auth.authenticationProvider(provider);
//
//
//            super.configure(auth);
//        }
//    }
//
//    private <T> T getBeanOrNull(Class<T> type) {
//        String[] beanNames = MyInitializeUserDetailsBeanManagerConfigurer.this.context.getBeanNamesForType(type);
//        if (beanNames.length != 1) {
//            return null;
//        }
//        return MyInitializeUserDetailsBeanManagerConfigurer.this.context.getBean(beanNames[0], type);
//    }
//
//}
