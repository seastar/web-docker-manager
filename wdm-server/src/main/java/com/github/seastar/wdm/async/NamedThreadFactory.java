package com.github.seastar.wdm.async;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@RequiredArgsConstructor
public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger idx = new AtomicInteger(0);
    private final String namePrefix;

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "%s-%d".formatted(namePrefix, idx.incrementAndGet()));
    }
}