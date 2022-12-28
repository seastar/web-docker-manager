package com.github.seastar.wdm.exps;

import java.io.Serial;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public class PropsException extends Exception {
    @Serial
    private static final long serialVersionUID = -1230250811432775783L;

    public PropsException(String message) {
        super(message);
    }
}