package com.github.seastar.wdm.data.redis.key;

import org.springframework.lang.NonNull;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public record StrKeyStore(@NonNull String str) implements KeyStore {
    @Override
    public String strAs() {
        return str;
    }
}