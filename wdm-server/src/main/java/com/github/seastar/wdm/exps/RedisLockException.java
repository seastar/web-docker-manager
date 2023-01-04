package com.github.seastar.wdm.exps;

import java.io.Serial;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public class RedisLockException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4476676818605119700L;

    public RedisLockException(String message) {
        super(message);
    }
}
