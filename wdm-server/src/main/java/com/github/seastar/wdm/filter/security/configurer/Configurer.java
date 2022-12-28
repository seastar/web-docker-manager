package com.github.seastar.wdm.filter.security.configurer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
public interface Configurer {
    HttpSecurity next(HttpSecurity httpSecurity) throws Exception;
}
