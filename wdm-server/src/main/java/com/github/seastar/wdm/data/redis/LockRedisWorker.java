package com.github.seastar.wdm.data.redis;

import com.github.seastar.wdm.exps.RedisLockException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Optional;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public class LockRedisWorker<V> extends RedisWorker<V> {

    private final ValueOperations<String, V> opsForValue;

    public LockRedisWorker(RedisTemplate<String, V> holdTemplate, String group) {
        super(holdTemplate, group);
        this.opsForValue = holdTemplate().opsForValue();
    }

    public boolean lock(V ownerProve, Duration expire) {
        return Optional.ofNullable(opsForValue.setIfAbsent(group(), ownerProve, expire))
                .orElseThrow(() -> new RedisLockException("Cannot resolve lock state."));
    }

    public void unlock(V ownerProve) {
        var val = opsForValue.get(group());
        if (val == null) {
            return;
        }
        if (val.equals(ownerProve)) {
            holdTemplate().delete(group());
        }
    }

    public Optional<V> getLockOwner() {
        return Optional.ofNullable(opsForValue.get(group()));
    }
}
