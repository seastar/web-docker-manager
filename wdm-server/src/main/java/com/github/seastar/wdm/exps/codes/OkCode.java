package com.github.seastar.wdm.exps.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public enum OkCode implements ErrCode {
    DONE(0, "请求已完成响应"),
    ACCEPTED(1, "请求已接受"),
    ;

    private final int code;
    private final String desc;
}