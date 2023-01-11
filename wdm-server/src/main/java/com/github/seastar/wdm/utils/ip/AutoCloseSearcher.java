package com.github.seastar.wdm.utils.ip;

import org.lionsoul.ip2region.xdb.Searcher;

import java.io.Closeable;
import java.io.IOException;

/**********************************
 * Date: 2023/1/11
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
class AutoCloseSearcher extends Searcher implements Closeable {
    AutoCloseSearcher(byte[] cBuff) throws IOException {
        super(null, null, cBuff);
    }
}
