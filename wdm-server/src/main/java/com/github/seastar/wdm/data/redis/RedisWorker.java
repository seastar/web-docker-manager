package com.github.seastar.wdm.data.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public abstract class RedisWorker<V> {

    private final RedisTemplate<String, V> holdTemplate;
    private final String group;
}
