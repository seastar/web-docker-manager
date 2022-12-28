package com.github.seastar.wdm.exps;

import java.io.Serial;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public class ClockException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2503043348143234943L;

    public ClockException(String message) {
        super(message);
    }
}