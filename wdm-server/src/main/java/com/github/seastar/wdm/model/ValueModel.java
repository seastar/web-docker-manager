package com.github.seastar.wdm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Date;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class ValueModel extends IDModel {
    @Serial
    private static final long serialVersionUID = 7135973041087397523L;

    private Date createTime;
    private Date updateTime;
}