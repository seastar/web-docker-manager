package com.github.seastar.wdm.filter.registrar;

import com.github.seastar.wdm.filter.FilterOrder;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
public abstract class BaseWebFilterRegistrar<T extends Filter> extends FilterRegistrationBean<T> {

    public abstract String name();
    public abstract FilterOrder order();

    public String[] urlPatterns() {
        return new String[] { "/*" };
    }

    public BaseWebFilterRegistrar(T filter) {
        this.setFilter(filter);
        this.setName(name());
        this.addUrlPatterns(urlPatterns());
        this.setOrder(order().val());
    }
}