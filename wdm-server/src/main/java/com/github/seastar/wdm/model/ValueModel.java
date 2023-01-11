package com.github.seastar.wdm.model;

import jakarta.persistence.MappedSuperclass;
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
@MappedSuperclass
public abstract class ValueModel extends BaseModel {
    @Serial
    private static final long serialVersionUID = 7135973041087397523L;

    @jakarta.persistence.Column(
            name = "create_at",
            nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Date createAt;

    @jakarta.persistence.Column(
            name = "update_at",
            nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    private Date updateAt;
}