package com.github.seastar.wdm.model.captcha;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public enum ImageFmt {
    PNG("png"),
    ;
    private final String val;
}
