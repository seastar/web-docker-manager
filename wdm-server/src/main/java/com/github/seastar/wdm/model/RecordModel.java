package com.github.seastar.wdm.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

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
public abstract class RecordModel extends IDModel {
    @Serial
    private static final long serialVersionUID = -5913691219962633165L;

    @CreatedDate
    private Date recordTime;
}