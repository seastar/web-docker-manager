package com.github.seastar.wdm.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.seastar.wdm.exps.codes.ErrCode;
import lombok.Getter;

import java.util.Date;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@Getter
public class ApiReturn {

    private final int code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private final Date time;
    private final String desc;
    private final Object data;

    public ApiReturn(ErrCode code, Object data) {
        this.code = code.code();
        this.desc = code.desc();
        this.time = new Date();
        this.data = data;
    }

    public ApiReturn(ErrCode code) {
        this(code, null);
    }
}