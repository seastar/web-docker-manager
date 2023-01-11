package com.github.seastar.wdm.filter.security.handler;

import com.github.seastar.wdm.api.ApiReturn;
import com.github.seastar.wdm.exps.codes.ErrCode;
import com.github.seastar.wdm.utils.JsonConverter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public abstract class BaseHandler {

    @Resource
    private JsonConverter jsonConverter;

    public void writeResponse(ErrCode errCode, HttpServletResponse response)
            throws IOException {
        writeResponse(new ApiReturn(errCode), response);
    }

    public void writeResponse(ApiReturn apiReturn, HttpServletResponse response)
            throws IOException {
        var bytes = jsonConverter.asBytes(apiReturn);
        try (var out = response.getOutputStream()) {
            out.write(bytes);
            out.flush();
        }
    }
}
