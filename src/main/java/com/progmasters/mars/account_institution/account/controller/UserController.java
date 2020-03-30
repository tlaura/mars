package com.progmasters.mars.account_institution.account.controller;

import com.progmasters.mars.account_institution.account.dto.NormalAccountCreationCommand;
import com.progmasters.mars.account_institution.account.security.AuthenticatedUserDetails;
import com.progmasters.mars.account_institution.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final AccountService accountService;

    @Autowired
    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticatedUserDetails> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("Login is requested!");

        AuthenticatedUserDetails authenticatedUserDetails = accountService.getAuthenticatedUserDetails(user.getUsername());
        ResponseEntity<AuthenticatedUserDetails> responseEntity;
        if (accountService.isUserConfirmed(authenticatedUserDetails.getName())) {
            responseEntity = new ResponseEntity<>(authenticatedUserDetails, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }

    @PostMapping()
    public ResponseEntity<NormalAccountCreationCommand> createNormalAccount(@RequestBody @Valid NormalAccountCreationCommand normalAccountCreationCommand) {
        //TODO: save account
        log.info("Normal Account creation requested!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
