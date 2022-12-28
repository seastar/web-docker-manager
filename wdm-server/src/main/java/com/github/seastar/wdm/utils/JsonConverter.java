package com.github.seastar.wdm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.seastar.wdm.exps.WdmIOException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@Component
public class JsonConverter {

    @Resource
    private ObjectMapper objectMapper;

    public String asStr(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (IOException e) {
            throw new WdmIOException(e);
        }
    }

    public byte[] asBytes(Object data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (IOException e) {
            throw new WdmIOException(e);
        }
    }
}