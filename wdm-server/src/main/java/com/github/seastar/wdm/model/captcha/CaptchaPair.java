package com.github.seastar.wdm.model.captcha;

import java.util.Base64;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public record CaptchaPair(byte[] image, ImageFmt fmt, String text) {

    public String base64() {
        return "data:image/%s;base64,%s".formatted(
                fmt,
                Base64.getEncoder().encodeToString(image)
        );
    }
}
