package com.rookie.bigdata.config;


import com.rookie.bigdata.token.TokenAuthenticateFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @Class SecurityConfiguration
 * @Description
 * @Author rookie
 * @Date 2024/7/3 10:23
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
//    @Configuration
//    @EnableWebSecurity
//    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
//    public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticateFilter tokenAuthenticateFilter;

    @Autowired
    public SecurityConfiguration(TokenAuthenticateFilter tokenAuthenticateFilter) {
        this.tokenAuthenticateFilter = tokenAuthenticateFilter;
    }

        @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                // 设置 session 为无状态，因为基于 token 不需要 session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().none()
                .and()
                .authorizeRequests()
                .antMatchers("/getToken/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .addFilterBefore(tokenAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .authorizeHttpRequests((authorizeHttpRequests) ->
//                            authorizeHttpRequests
//                                    .requestMatchers("/admin/**").hasRole("ADMIN")
//                                    .requestMatchers("/**").hasRole("USER")
//                    )
//                    .formLogin(withDefaults());
//            return http.build();
//        }


        http.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/getToken/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(Customizer.withDefaults())
                .addFilterBefore(tokenAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
////        http.sessionManagement()
////                // 设置 session 为无状态，因为基于 token 不需要 session
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .sessionFixation().none()
////                .and()
////                .authorizeRequests()
////                .antMatchers("/getToken/**").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .csrf().disable()
////                .addFilterBefore(tokenAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);
//        // @formatter:off
////        http
////                .authorizeHttpRequests((authorize) -> authorize
////                        .anyRequest().authenticated()
////                )
////                .csrf((csrf) -> csrf.ignoringRequestMatchers("/token"))
////                .httpBasic(Customizer.withDefaults())
//////                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
//////                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt(Customizer.withDefaults()))
////                .oauth2ResourceServer((oAuth2ResourceServerConfigurer) -> oAuth2ResourceServerConfigurer
////                        .jwt(Customizer.withDefaults())
////                )
////                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .exceptionHandling((exceptions) -> exceptions
////                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
////                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
////                );
////        // @formatter:on
////        return http.build();
//    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement()
//                // 设置 session 为无状态，因为基于 token 不需要 session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .sessionFixation().none()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/getToken/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable()
//                .addFilterBefore(tokenAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}
