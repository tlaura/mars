package com.progmasters.mars.controller;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.InstitutionListData;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.dto.ProviderUserDetails;
import com.progmasters.mars.service.AccountInstitutionService;
import com.progmasters.mars.dto.ProviderUserDetailsEdit;
import com.progmasters.mars.service.AccountService;
import com.progmasters.mars.validation.ProviderAccountValidator;
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
        return new ResponseEntity<>(accountService.getProviderAccount(username), HttpStatus.OK);
    }

    @GetMapping("/getInstitutionsByType")
    public ResponseEntity<List<InstitutionListData>> getInstitutionByType(@RequestParam("type") String type) {
        //TODO FYI Az ENUM-nak van valueOf methodja  --fixed
        InstitutionType institutionType = InstitutionType.valueOf(type);
        List<InstitutionListData> institutionListData = accountService.getInstitutionsByType(institutionType);
        logger.info("Institution List is requested by type!");
        return new ResponseEntity<>(institutionListData, HttpStatus.OK);
    }

    @GetMapping("/edit/{loggedInUser}")
    public ResponseEntity<ProviderUserDetailsEdit> getProviderAccountEditData(@PathVariable String loggedInUser) {
        return new ResponseEntity<>(accountService.getProviderAccountEditDetails(loggedInUser), HttpStatus.OK);
    }

    @PutMapping("/{loggedInUser}")
    public ResponseEntity<ProviderUserDetailsEdit> updateProviderAccountDetails(@Valid @RequestBody ProviderUserDetailsEdit providerUserDetailsEdit, @PathVariable String loggedInUser) {
        ProviderAccount providerUpdate = accountService.updateProviderAccount(providerUserDetailsEdit, loggedInUser);
        ResponseEntity<ProviderUserDetailsEdit> result;
        if (providerUpdate == null) {
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            result = new ResponseEntity<>(new ProviderUserDetailsEdit(providerUpdate), HttpStatus.OK);
            logger.info("provider account updated");
        }
        return result;
    }
}
