package com.github.seastar.wdm.filter.security.configurer;

import com.github.seastar.wdm.filter.security.Paths;
import jakarta.annotation.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**********************************
 * Date: 2022/12/29
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Component
public class LogoutConfigurer implements Configurer {

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    public HttpSecurity next(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.logout(c -> {
            c.logoutSuccessHandler(logoutSuccessHandler);
            c.logoutSuccessUrl(Paths.ApiAuthLogout);
        });
    }
}
