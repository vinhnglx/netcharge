package com.vinhnguyen.netChargeNZ.config;

import com.vinhnguyen.netChargeNZ.filter.JWTTokenValidatorFilter;
import com.vinhnguyen.netChargeNZ.util.JWTTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class ProjectSecurityConfig {
    private final JWTTokenUtil jwtTokenUtil;

    public ProjectSecurityConfig(JWTTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilterBefore(new JWTTokenValidatorFilter(jwtTokenUtil), BasicAuthenticationFilter.class)
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/api/connectors").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/chargePoints").hasRole("CUSTOMER")
            .requestMatchers(HttpMethod.POST, "/api/chargingSessions").hasRole("CUSTOMER")
            .requestMatchers("/version", "/api/register", "/api/signIn").permitAll()
            .requestMatchers(toH2Console()).permitAll()
            .and().csrf().ignoringRequestMatchers(toH2Console())
            .and().formLogin().and().httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
