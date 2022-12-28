package com.github.seastar.wdm.exps.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public enum WdmErrCode implements ErrCode {
    BadRequest(400, "请求参数错误"),
    RequireAuthentication(401, "需要登录以继续访问"),
    AccessDenied(403, "当前账户无权访问此接口"),
    NotFound(404, "访问的资源不存在"),
    ServerError(500, "服务内部错误"),
    ServiceDown(503, "服务暂时不可用，请稍后重试"),
    ;

    private final int code;
    private final String desc;
}