package com.github.seastar.wdm.service.captcha.built;

import java.util.Random;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public record TextCreator(int num) {

    private static final char[] CODES = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789".toCharArray();

    public String create() {
        var sb = new StringBuilder();
        var random = new Random();
        for (var i = 0; i < num; i ++) {
            sb.append(CODES[random.nextInt(CODES.length)]);
        }
        return sb.toString();
    }
}
