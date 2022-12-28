package com.github.seastar.wdm.io;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
public class WdmBytesIO extends WdmIO<byte[]> {

    public WdmBytesIO(String path) {
        super(path);
    }

    @Override
    protected byte[] map(byte[] bytes) {
        return bytes;
    }
}