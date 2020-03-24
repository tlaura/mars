package com.progmasters.mars.account.controller;

import com.progmasters.mars.account.ProviderAccountValidator;
import com.progmasters.mars.account.domain.InstitutionType;
import com.progmasters.mars.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account.dto.ProviderUserDetails;
import com.progmasters.mars.account.dto.ProviderUserDetailsEdit;
import com.progmasters.mars.account.service.AccountInstitutionService;
import com.progmasters.mars.account.service.AccountService;
import com.progmasters.mars.institution.dto.InstitutionListData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private ProviderAccountValidator providerAccountValidator;
    private final AccountInstitutionService accountInstitutionService;
    private final AccountService accountService;


    @Autowired
    public AccountController(ProviderAccountValidator providerAccountValidator, AccountInstitutionService accountInstitutionService, AccountService accountService) {
        this.accountInstitutionService = accountInstitutionService;
        this.providerAccountValidator = providerAccountValidator;
        this.accountService = accountService;
    }

    @InitBinder("providerAccountCreationCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(providerAccountValidator);
    }

    @PostMapping
    public ResponseEntity<Void> createProviderAccount(@RequestBody @Valid ProviderAccountCreationCommand providerAccountCreationCommand) {
        accountInstitutionService.saveAccount(providerAccountCreationCommand);
        logger.info("Provider Account creation requested!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProviderUserDetails> getProviderUser(@PathVariable String username) {
        logger.info("Provider Account Details requested!");
        return new ResponseEntity<>(accountService.getProviderAccountByEmail(username), HttpStatus.OK);
    }

    @GetMapping("/getInstitutionsByType")
    public ResponseEntity<List<InstitutionListData>> getInstitutionByType(@RequestParam("type") String type) {
        //TODO FYI Az ENUM-nak van valueOf methodja  --fixed
        InstitutionType institutionType = InstitutionType.valueOf(type);
        List<InstitutionListData> institutionListData = accountInstitutionService.getInstitutionsByAccountType(institutionType);
        logger.info("Institution List is requested by type!");
        return new ResponseEntity<>(institutionListData, HttpStatus.OK);
    }

    @GetMapping("/edit/{loggedInUser}")
    public ResponseEntity<ProviderUserDetailsEdit> getProviderAccountEditData(@PathVariable String loggedInUser) {
        return new ResponseEntity<>(accountService.getProviderAccountEditDetailsByEmail(loggedInUser), HttpStatus.OK);
    }

    @PatchMapping("/{loggedInUser}")
    public ResponseEntity<ProviderUserDetailsEdit> updateProviderAccountDetails(@Valid @RequestBody ProviderUserDetailsEdit providerUserDetailsEdit, @PathVariable String loggedInUser) {
        logger.info("provider account update requested");
        ProviderUserDetailsEdit updatedAccount = accountService.updateProviderAccount(providerUserDetailsEdit, loggedInUser);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}