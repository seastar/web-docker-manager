package com.github.seastar.wdm.io;

import java.nio.charset.StandardCharsets;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
public class WdmStrIO extends WdmIO<String> {

    public WdmStrIO(String path) {
        super(path);
    }

    @Override
    protected String map(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}