package com.github.seastar.wdm.exps.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public enum AuthErrorCode implements ErrCode {
    AccountNotFound(10404, "用户名或密码错误"),
    CredentialsError(10404, "用户名或密码错误"),
    CaptchaError(10401, "验证码错误"),
    AccountExpired(10403, "账户已过期，请联系系统管理员处理"),
    CredentialsExpired(10403, "密码已过期，请联系系统管理员处理"),
    AccountLocked(10402, "账户已锁定，请联系系统管理员处理"),
    AccountDisabled(10405, "账户已禁用，请联系系统管理员"),
    ;

    private final int code;
    private final String desc;
}
