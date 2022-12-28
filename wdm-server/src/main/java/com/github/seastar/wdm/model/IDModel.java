package com.github.seastar.wdm.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
@Data
public abstract class IDModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 14079840772283830L;

    private Long id;
}