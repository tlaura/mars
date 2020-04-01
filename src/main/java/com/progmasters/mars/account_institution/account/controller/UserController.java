package com.progmasters.mars.account_institution.account.controller;

import com.progmasters.mars.account_institution.account.dto.JwtAuthenticationResponse;
import com.progmasters.mars.account_institution.account.dto.LoginRequest;
import com.progmasters.mars.account_institution.account.dto.UserCreationCommand;
import com.progmasters.mars.account_institution.account.dto.UserDetailsData;
import com.progmasters.mars.account_institution.account.security.AuthenticatedUserDetails;
import com.progmasters.mars.account_institution.account.security.JwtTokenProvider;
import com.progmasters.mars.account_institution.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(AccountService accountService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticatedUserDetails> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("Login is requested!");

        AuthenticatedUserDetails authenticatedUserDetails = accountService.getAuthenticatedUserDetails(user.getUsername());
        ResponseEntity<AuthenticatedUserDetails> responseEntity;
        if (accountService.isUserConfirmed(authenticatedUserDetails.getEmail())) {
            responseEntity = new ResponseEntity<>(authenticatedUserDetails, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login requested");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<>(new JwtAuthenticationResponse(jwt), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserCreationCommand> createAccount(@RequestBody @Valid UserCreationCommand userCreationCommand) {
        accountService.save(userCreationCommand);
        log.info("Normal Account creation requested!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{loggedInUser}")
    public ResponseEntity<Void> deleteUser(@PathVariable String loggedInUser) {
        boolean isAccountDeleted = accountService.deleteUser(loggedInUser);
        if (isAccountDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/details/{email}")
    public ResponseEntity<UserDetailsData> getUserDetailsByEmail(@PathVariable String email) {
        UserDetailsData userDetails = accountService.getUserDetails(email);
        log.info("UserDetails is requested by email: " + email);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDetailsData> updateUserDetailsByEmail(@PathVariable String email, @RequestBody UserDetailsData userDetailsData) {
        UserDetailsData modified = accountService.updateUser(userDetailsData, email);

        return new ResponseEntity<>(modified, HttpStatus.OK);
    }
}
