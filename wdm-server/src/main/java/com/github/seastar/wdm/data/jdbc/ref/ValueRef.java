package com.github.seastar.wdm.data.jdbc.ref;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public record ValueRef<T>(T val) implements Serializable {
    @Serial
    private static final long serialVersionUID = 152936175215774826L;

    public Optional<T> option() {
        return Optional.ofNullable(val);
    }
}