package com.vinhnguyen.netChargeNZ.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/version").permitAll()
                .requestMatchers(toH2Console()).permitAll()
                .and().csrf().ignoringRequestMatchers(toH2Console())
                .and().formLogin().and().httpBasic();
        http.headers().frameOptions().disable();

        return http.build();
    }
}
