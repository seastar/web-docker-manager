package com.github.seastar.wdm.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
public abstract class ValueModel extends IDModel {
    @Serial
    private static final long serialVersionUID = 7135973041087397523L;

    @Column(nullable = false)
    @CreatedDate
    private Date createTime;
    @Column(nullable = false)
    @LastModifiedDate
    private Date updateTime;
}