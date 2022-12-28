package com.github.seastar.wdm.filter.security;

import com.github.seastar.wdm.filter.security.configurer.Configurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
@Configuration
public class SecurityFilterConfigurer {

    @Bean
    public PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, Configurer[] configurers)
            throws Exception {
        for (Configurer configurer : configurers) {
            httpSecurity = configurer.next(httpSecurity);
        }
        return httpSecurity.build();
    }
}