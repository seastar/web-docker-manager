package com.github.seastar.wdm.filter.registrar;

import com.github.seastar.wdm.filter.FilterOrder;
import com.github.seastar.wdm.filter.WdmRequestLoggerFilter;
import org.springframework.stereotype.Component;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
@Component
public class WdmRequestLoggerFilterRegistrar extends BaseWebFilterRegistrar<WdmRequestLoggerFilter> {

    public WdmRequestLoggerFilterRegistrar(WdmRequestLoggerFilter wdmRequestLoggerFilter) {
        super(wdmRequestLoggerFilter);
    }

    @Override
    public String name() {
        return "wdmRequestLoggerFilter";
    }

    @Override
    public FilterOrder order() {
        return FilterOrder.WdmRequestLoggerFilter;
    }
}