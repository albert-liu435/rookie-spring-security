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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    private final TokenAuthenticateFilter tokenAuthenticateFilter;

    @Autowired
    public SecurityConfiguration(TokenAuthenticateFilter tokenAuthenticateFilter) {
        this.tokenAuthenticateFilter = tokenAuthenticateFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


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

    @Bean
    UserDetailsService users() {
        // @formatter:off
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password("{noop}password")
                        .authorities("app")
                        .build()
        );
        // @formatter:on
    }

}
