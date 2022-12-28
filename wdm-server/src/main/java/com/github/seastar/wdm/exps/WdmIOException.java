package com.github.seastar.wdm.exps;

import java.io.IOException;
import java.io.Serial;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public class WdmIOException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1006518234139022407L;

    public WdmIOException(IOException e) {
        super(e.getMessage(), e);
    }
}