package com.progmasters.mars.config;

import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.chat.domain.LoginState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private AccountService accountService;

    @Autowired
    public CustomLogoutHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(currentUserName);
        accountService.setLoginState(currentUserName, LoginState.OFFLINE);
    }
}
