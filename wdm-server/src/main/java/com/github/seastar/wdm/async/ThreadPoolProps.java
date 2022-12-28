package com.github.seastar.wdm.async;

import java.time.Duration;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public interface ThreadPoolProps {
    int minThreadNum();
    int maxThreadNum();
    Duration keepAlive();
    String name();
}
