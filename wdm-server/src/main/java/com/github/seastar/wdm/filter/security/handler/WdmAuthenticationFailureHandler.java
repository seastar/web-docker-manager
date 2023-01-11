package com.github.seastar.wdm.filter.security.handler;

import com.github.seastar.wdm.exps.AuthCaptchaException;
import com.github.seastar.wdm.exps.codes.AuthErrorCode;
import com.github.seastar.wdm.exps.codes.ErrCode;
import com.github.seastar.wdm.exps.codes.WdmErrCode;
import com.github.seastar.wdm.filter.security.Params;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Component
@Slf4j
public class WdmAuthenticationFailureHandler extends BaseHandler implements AuthenticationFailureHandler {

    private static ErrCode getErrCode(AuthenticationException e) {
        if (e instanceof UsernameNotFoundException)
            return AuthErrorCode.AccountNotFound;
        if (e instanceof BadCredentialsException)
            return AuthErrorCode.CredentialsError;
        if (e instanceof AuthCaptchaException)
            return AuthErrorCode.CaptchaError;
        if (e instanceof LockedException)
            return AuthErrorCode.AccountLocked;
        if (e instanceof DisabledException)
            return AuthErrorCode.AccountDisabled;
        if (e instanceof AccountExpiredException)
            return AuthErrorCode.AccountExpired;
        if (e instanceof CredentialsExpiredException)
            return AuthErrorCode.CredentialsExpired;
        return WdmErrCode.ServerError;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        log.warn("AuthenticationHandler failure[account={}, type={}, desc={}]",
                request.getParameter(Params.Account),
                exception.getClass().getName(),
                exception.getMessage()
        );
        writeResponse(getErrCode(exception), response);
    }
}
