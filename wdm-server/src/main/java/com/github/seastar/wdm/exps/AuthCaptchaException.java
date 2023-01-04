package com.github.seastar.wdm.exps;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

/**********************************
 * Date: 2022/12/29
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public class AuthCaptchaException extends AuthenticationException {
    @Serial
    private static final long serialVersionUID = 753923394203705405L;

    public AuthCaptchaException(String msg) {
        super(msg);
    }
}
