package com.github.seastar.wdm.service.captcha.impl;

import com.github.seastar.wdm.model.captcha.CaptchaPair;
import com.github.seastar.wdm.service.captcha.CaptchaService;
import com.github.seastar.wdm.service.captcha.VerifyRet;
import com.github.seastar.wdm.utils.attr.StrAttrKey;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Service
public class SessionCaptchaService implements CaptchaService {

    @Override
    public CaptchaPair buildAndSave(HttpServletRequest request, StrAttrKey attrKey) {
        return null;
    }

    @Override
    public VerifyRet validAndRemove(HttpServletRequest request, String captcha, StrAttrKey attrKey) {
        return null;
    }
}
