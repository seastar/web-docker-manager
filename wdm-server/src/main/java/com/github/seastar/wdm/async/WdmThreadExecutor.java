package com.github.seastar.wdm.async;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public class WdmThreadExecutor extends ThreadPoolExecutor {

    public WdmThreadExecutor(ThreadPoolProps props) {
        super(
                props.minThreadNum(),
                props.maxThreadNum(),
                props.keepAlive().toMillis(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new NamedThreadFactory(props.name()),
                new AbortHandler(props.name())
        );
    }
}