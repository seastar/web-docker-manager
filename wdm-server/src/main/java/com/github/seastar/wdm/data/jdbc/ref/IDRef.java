package com.github.seastar.wdm.data.jdbc.ref;

import java.io.Serial;
import java.io.Serializable;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public record IDRef(Long val) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1969067487684825751L;
}