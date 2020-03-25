package com.progmasters.mars.account_institution.account.controller;

import com.progmasters.mars.account_institution.account.security.AuthenticatedUserDetails;
import com.progmasters.mars.account_institution.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final AccountService accountService;

    @Autowired
    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticatedUserDetails> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        logger.info("Login is requested!");

        AuthenticatedUserDetails authenticatedUserDetails = accountService.getAuthenticatedUserDetails(user.getUsername());
        ResponseEntity<AuthenticatedUserDetails> responseEntity;
        if (accountService.isUserConfirmed(authenticatedUserDetails.getName())) {
            responseEntity = new ResponseEntity<>(authenticatedUserDetails, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }
}
