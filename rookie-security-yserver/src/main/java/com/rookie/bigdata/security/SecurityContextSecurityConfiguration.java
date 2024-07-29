package com.rookie.bigdata.security;

import com.rookie.bigdata.security.config.SecurityContextConfigurerCustomizer;
import com.rookie.bigdata.security.core.context.MyThreadLocalSecurityContextHolderStrategy;
import com.rookie.bigdata.security.web.authentication.www.MyBasicAuthenticationEntryPoint;
import com.rookie.bigdata.security.web.context.MySecurityContextRepository;
import com.rookie.bigdata.security.web.context.MySecurityContextSaveFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Class SecurityContextSecurityConfiguration
 * @Description
 * @Author rookie
 * @Date 2024/7/29 14:23
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityContextSecurityConfiguration {

    /**
     * 第一次请求  http://localhost:8888/httpSecuritySecurityContextConfigurer/code 采用basic得方式进行，则可以通过请求
     * 第二次请求 http://localhost:8888/httpSecuritySecurityContextConfigurer/code 将basic方式去掉，然后在header中添加code=abcd得方式就可以通过了
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )

                //配置安全上下文持久化
//                .securityContext(new SecurityContextConfigurerCustomizer()
                .securityContext((httpSecuritySecurityContextConfigurer -> {

                    SecurityContextRepository securityContextRepository = new DelegatingSecurityContextRepository(
                            new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository(), MySecurityContextRepository());

                    httpSecuritySecurityContextConfigurer
                            .securityContextRepository(securityContextRepository);
                }))

                .addFilterAfter(new MySecurityContextSaveFilter(MySecurityContextRepository()), AuthorizationFilter.class)

                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }


    @Bean
    public SecurityContextRepository MySecurityContextRepository() {
        return new MySecurityContextRepository();
    }

//    /**
//     * 为了能够保存SecurityContext,这里暂时这样处理，其实还有更好得方法，比如添加一个Filter,在Filter中进行处理SecurityContext并进行保存，
//     * 实际上很多也是通过添加Filter来处理得，我这里只是为了说明可以这样处理
//     *
//     * @return
//     */
//    @Bean
//    public SecurityContextHolderStrategy MyThreadLocalSecurityContextHolderStrategy() {
//        return new MyThreadLocalSecurityContextHolderStrategy(MySecurityContextRepository());
//    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


//    Security filter chain: [
//  DisableEncodeUrlFilter
//  WebAsyncManagerIntegrationFilter
//  SecurityContextHolderFilter
//  HeaderWriterFilter
//  CorsFilter
//  CsrfFilter
//  LogoutFilter
//  UsernamePasswordAuthenticationFilter
//  DefaultLoginPageGeneratingFilter
//  DefaultLogoutPageGeneratingFilter
//  BasicAuthenticationFilter
//  RequestCacheAwareFilter
//  SecurityContextHolderAwareRequestFilter
//  AnonymousAuthenticationFilter
//  ExceptionTranslationFilter
//  AuthorizationFilter
//]


}
