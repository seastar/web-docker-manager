package com.github.seastar.wdm.filter.security.configurer;

import com.github.seastar.wdm.exps.AuthCaptchaException;
import com.github.seastar.wdm.filter.WdmHttpFilter;
import com.github.seastar.wdm.filter.security.Params;
import com.github.seastar.wdm.filter.security.Paths;
import com.github.seastar.wdm.service.captcha.CaptchaService;
import com.github.seastar.wdm.utils.attr.StrAttrKey;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Component
public class FormLoginConfigurer extends WdmHttpFilter implements Configurer {

    @Resource
    private CaptchaService captchaService;

    @Override
    public HttpSecurity next(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.formLogin(c -> {
            c.loginPage(Paths.WebAuthLogin);
            c.loginProcessingUrl(Paths.ApiAuthLogin);
            c.usernameParameter(Params.Account);
            c.passwordParameter(Params.Password);
        }).addFilterBefore(this, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var captcha = request.getParameter(Params.Captcha);
        switch (captchaService.validAndRemove(request, captcha, StrAttrKey.CAPTCHA_LOGIN)) {
            case BLANK -> throw new AuthCaptchaException("Missing param: captcha");
            case NO_GENERATE -> throw new AuthCaptchaException("Captcha not generate");
            case ERROR -> throw new AuthCaptchaException("Error during valid captcha");
            case NO_EQUALS -> throw new AuthCaptchaException("Captcha not equals");
            case OK -> {}
        }
    }
}
