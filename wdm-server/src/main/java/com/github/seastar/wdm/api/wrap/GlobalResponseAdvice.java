package com.github.seastar.wdm.api.wrap;

import com.github.seastar.wdm.api.ApiCode;
import com.github.seastar.wdm.api.ApiReturn;
import com.github.seastar.wdm.exps.codes.OkCode;
import com.github.seastar.wdm.utils.JsonConverter;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@RestControllerAdvice
@AllArgsConstructor
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private JsonConverter jsonConverter;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().isAssignableFrom(ApiReturn.class)
                && !returnType.getParameterType().isAssignableFrom(ResponseEntity.class)
                && !returnType.getParameterType().isAssignableFrom(ModelAndView.class);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        var code = Optional.ofNullable(returnType.getMethodAnnotation(ApiCode.class))
                .map(ApiCode::code)
                .orElse(OkCode.DONE);
        if (body instanceof String) {
            if (response instanceof ServletServerHttpResponse resp) {
                resp.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return jsonConverter.asStr(new ApiReturn(code, body));
            } else {
                return body;
            }
        }
        return new ApiReturn(code, body);
    }
}