package com.github.seastar.wdm.data.redis.key;

import org.springframework.lang.NonNull;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public record LongKeyStore(@NonNull Long id) implements KeyStore<Long> {
    @Override
    public String strAs() {
        return id.toString();
    }
}