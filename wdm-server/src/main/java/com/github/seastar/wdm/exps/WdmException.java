package com.github.seastar.wdm.exps;

import com.github.seastar.wdm.exps.codes.ErrCode;
import lombok.Getter;

import java.io.Serial;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public class WdmException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3787792194899781491L;

    @Getter
    private final ErrCode errCode;

    public WdmException(ErrCode code, String message) {
        super(message);
        this.errCode = code;
    }
}