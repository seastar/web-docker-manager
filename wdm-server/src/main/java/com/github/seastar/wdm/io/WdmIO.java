package com.github.seastar.wdm.io;

import com.github.seastar.wdm.exps.WdmIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
public abstract class WdmIO<T> extends ClassPathResource {

    public WdmIO(String path) {
        super(path);
    }

    protected abstract T map(byte[] bytes);

    public T read() {
        try (var in = getInputStream()) {
            var bytes = StreamUtils.copyToByteArray(in);
            return map(bytes);
        } catch (IOException e) {
            throw new WdmIOException(e);
        }
    }
}