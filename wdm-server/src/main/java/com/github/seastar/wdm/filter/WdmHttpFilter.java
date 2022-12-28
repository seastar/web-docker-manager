package com.github.seastar.wdm.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public abstract class WdmHttpFilter extends OncePerRequestFilter {

    protected boolean shouldFilter(HttpServletRequest request) throws ServletException {
        return true;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !shouldFilter(request);
    }
}