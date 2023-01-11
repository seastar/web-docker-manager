package com.github.seastar.wdm.utils.ip;

import org.apache.commons.lang3.StringUtils;

/**********************************
 * Date: 2023/1/11
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public sealed interface IpValue permits IpValue.Addr, IpValue.Region {

    String val();

    default boolean hasValue() {
        return StringUtils.isNotBlank(val());
    }

    record Addr(String val) implements IpValue {}
    record Region(String val) implements IpValue {}
    record Value(Addr addr, Region region){}
}
