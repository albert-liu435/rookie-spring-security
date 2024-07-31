package com.rookie.bigdata.security;

import com.rookie.bigdata.security.config.annotation.authentication.configuration.MyInitializeUserDetailsBeanManagerConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Class FormLoginSecurityConfiguration
 * @Description
 * @Author rookie
 * @Date 2024/7/30 9:09
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SessionManagementSecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
//                .securityContext((securityContext) -> securityContext
//                        .securityContextRepository(new DelegatingSecurityContextRepository(
//                                new RequestAttributeSecurityContextRepository(),
//                                new HttpSessionSecurityContextRepository()
//                        )))
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(new HttpSessionSecurityContextRepository())
                )
//                .sessionManagement(sessionManagement -> sessionManagement
//                        .maximumSessions(10)
//                )
                .sessionManagement(withDefaults())
                .httpBasic(withDefaults())
                .formLogin(withDefaults())


//                .formLogin(new FormLoginConfigurerCustomizer())
//                .formLogin(httpFormLogin -> {
//                    httpFormLogin
//                            .loginPage("/login")
//                            //默认为username password,可以在这里进行修改
//                            .usernameParameter("username")
//                            .passwordParameter("password");
//                })
//                .rememberMe(withDefaults())
        ;


//                        .authorizeHttpRequests((authorize) -> authorize
//                .anyRequest().authenticated()
//        )
//                .csrf((csrf) -> csrf.ignoringRequestMatchers("/token"))
//                .httpBasic(Customizer.withDefaults())
////                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
////                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt(Customizer.withDefaults()))
//                .oauth2ResourceServer((oAuth2ResourceServerConfigurer) -> oAuth2ResourceServerConfigurer
//                        .jwt(Customizer.withDefaults())
//                )
//                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling((exceptions) -> exceptions
//                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
//                );

        return http.build();
    }


    @Bean
    public static MyInitializeUserDetailsBeanManagerConfigurer initializeMyUserDetailsBeanManagerConfigurer(
            ApplicationContext context) {
        return new MyInitializeUserDetailsBeanManagerConfigurer(context);
    }


    /**
     * 这里能起作用并注入到spring security中，可以参考InitializeUserDetailsBeanManagerConfigurer.InitializeUserDetailsManagerConfigurer#configure()方法中的
     * PasswordEncoder passwordEncoder = getBeanOrNull(PasswordEncoder.class);从spring容器中获取PasswordEncoder
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
