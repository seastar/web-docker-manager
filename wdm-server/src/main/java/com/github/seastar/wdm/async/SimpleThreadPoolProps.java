package com.github.seastar.wdm.async;

import java.time.Duration;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public record SimpleThreadPoolProps(
        int minThreadNum,
        int maxThreadNum,
        Duration keepAlive,
        String name
) implements ThreadPoolProps {

    @Override
    public String toString() {
        return "SimpleThreadPoolProps{" +
                "minThreadNum=" + minThreadNum +
                ", maxThreadNum=" + maxThreadNum +
                ", keepAlive=" + keepAlive +
                ", name='" + name + '\'' +
                '}';
    }
}