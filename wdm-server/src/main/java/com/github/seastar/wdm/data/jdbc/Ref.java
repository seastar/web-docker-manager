package com.github.seastar.wdm.data.jdbc;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**********************************
 * Date: 2023/1/4
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public record Ref<T>(T val) implements Serializable {
    @Serial
    private static final long serialVersionUID = 2928842529689024075L;

    public Optional<T> optional() {
        return Optional.ofNullable(val);
    }

    public boolean hasValue() {
        return val != null;
    }
}
