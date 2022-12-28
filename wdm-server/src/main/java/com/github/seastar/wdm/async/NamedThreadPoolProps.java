package com.github.seastar.wdm.async;

import java.time.Duration;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public record NamedThreadPoolProps(String name) implements ThreadPoolProps {

    @Override
    public int minThreadNum() {
        return 5;
    }

    @Override
    public int maxThreadNum() {
        return 10;
    }

    @Override
    public Duration keepAlive() {
        return Duration.ofMinutes(1);
    }

    @Override
    public String toString() {
        return "NamedThreadPoolProps{" +
                "name='" + name + '\'' +
                '}';
    }
}