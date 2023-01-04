package com.github.seastar.wdm.service.captcha;

import com.github.seastar.wdm.model.captcha.CaptchaPair;
import com.github.seastar.wdm.utils.attr.StrAttrKey;
import jakarta.servlet.http.HttpServletRequest;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public interface CaptchaService {
    CaptchaPair buildAndSave(HttpServletRequest request, StrAttrKey attrKey);
    VerifyRet validAndRemove(HttpServletRequest request, String captcha, StrAttrKey attrKey);
}
