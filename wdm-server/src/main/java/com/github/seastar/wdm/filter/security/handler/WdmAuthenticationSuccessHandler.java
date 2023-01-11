package com.github.seastar.wdm.filter.security.handler;

import com.github.seastar.wdm.exps.codes.OkCode;
import com.github.seastar.wdm.model.repos.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Component
@Slf4j
public class WdmAuthenticationSuccessHandler extends BaseHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        var user = (User) (authentication.getPrincipal());
        log.info("AuthenticationHandler success[id={}, account={}, nickname={}]",
                user.getId(),
                user.getAccount(),
                user.getNickname()
        );
        writeResponse(OkCode.DONE, response);
    }
}
