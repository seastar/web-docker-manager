package com.github.seastar.wdm.filter.security.configurer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
@Component
public class CSRFConfigurer implements Configurer {
    @Override
    public HttpSecurity next(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable();
    }
}