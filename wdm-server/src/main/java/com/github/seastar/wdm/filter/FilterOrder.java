package com.github.seastar.wdm.filter;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
@Getter
@Accessors(fluent = true)
public enum FilterOrder {
    WdmRequestLoggerFilter
    ;
    private final int val = ordinal() + 1;
}