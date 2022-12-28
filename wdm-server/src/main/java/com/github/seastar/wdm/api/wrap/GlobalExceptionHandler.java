package com.github.seastar.wdm.api.wrap;

import com.github.seastar.wdm.api.ApiReturn;
import com.github.seastar.wdm.exps.WdmException;
import com.github.seastar.wdm.exps.codes.BuiltErrCode;
import com.github.seastar.wdm.exps.codes.WdmErrCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WdmException.class)
    public ApiReturn handleWdmException(HttpServletRequest request, WdmException wdmException) {
        var code = wdmException.getErrCode();
        log.error("ExceptionHandler[{}] handle WdmException[code={}, message={}]",
                request.getServletPath(),
                code.code(),
                wdmException.getMessage()
        );
        return new ApiReturn(code);
    }

    @ExceptionHandler(Throwable.class)
    public ApiReturn handleThrowable(HttpServletRequest request, Throwable throwable) {
        if (throwable instanceof ErrorResponse errorResponse) {
            var code = errorResponse.getStatusCode().value();
            log.error("ExceptionHandler[{}] handle ErrorResponse[code={}, message={}]",
                    request.getServletPath(),
                    code,
                    throwable.getMessage()
            );
            return new ApiReturn(new BuiltErrCode(code, throwable.getMessage()));
        }
        log.error("ExceptionHandler[{}] handle UnknownException[type={}, message={}]",
                request.getServletPath(),
                throwable.getClass().getName(),
                throwable.getMessage(),
                throwable
        );
        return new ApiReturn(WdmErrCode.ServerError);
    }
}