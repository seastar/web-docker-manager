package com.github.seastar.wdm.utils.attr;

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
public enum StrAttrKey implements AttrKey<String> {
    CAPTCHA_LOGIN("cpl-01"),
    ;
    private final String key;
}
