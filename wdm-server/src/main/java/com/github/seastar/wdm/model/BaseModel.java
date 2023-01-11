package com.github.seastar.wdm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
@Data
@MappedSuperclass
public abstract class BaseModel implements Serializable {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

    @Serial
    private static final long serialVersionUID = 14079840772283830L;

    @Id
    private Long id;
}