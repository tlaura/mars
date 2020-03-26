package com.progmasters.mars.account_institution.account.controller;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.account.ProviderAccountValidator;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetails;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetailsEdit;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.account_institution.connector.AccountInstitutionListData;
import com.progmasters.mars.account_institution.connector.AccountInstitutionService;
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

    @GetMapping("/provider-details/{id}")
    public ResponseEntity<ProviderUserDetails> getProviderUserDetail(@PathVariable Long id) {
        logger.info("User details is requested by id:\t" + id);
        ProviderUserDetails foundUserDetails = accountService.getUserDetailsById(id);

        return new ResponseEntity<>(foundUserDetails, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createProviderAccount(@RequestBody @Valid ProviderAccountCreationCommand providerAccountCreationCommand) {
        try {
            accountInstitutionService.save(providerAccountCreationCommand);
        } catch (NotFoundException e) {
            logger.info("Location not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Provider Account creation requested!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProviderUserDetails> getProviderUser(@PathVariable String username) {
        logger.info("Provider Account Details requested!");
        return new ResponseEntity<>(accountService.getProviderAccountByEmail(username), HttpStatus.OK);
    }

    @GetMapping("/providersByType")
    public ResponseEntity<List<AccountInstitutionListData>> getInstitutionByType(@RequestParam("type") String type) {
        ProviderType providerType = ProviderType.valueOf(type);
        List<AccountInstitutionListData> providerListByTypes = accountInstitutionService.getInstitutionsByAccountType(providerType);

        logger.info("Institution List is requested by type!");
        return new ResponseEntity<>(providerListByTypes, HttpStatus.OK);
    }

    @GetMapping("/edit/{loggedInUser}")
    public ResponseEntity<ProviderUserDetailsEdit> getProviderAccountEditData(@PathVariable String loggedInUser) {
        return new ResponseEntity<>(accountService.getProviderAccountEditDetailsByEmail(loggedInUser), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProviderAccount(@Valid @RequestBody ProviderUserDetails providerUserDetails, @PathVariable Long id) {
        logger.info("Provider Account update requested");
        accountService.updateProviderAccountDetails(providerUserDetails, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{loggedInUser}/{id}")
    public ResponseEntity<Void> removeAccountInstitutionConnection(@PathVariable String loggedInUser, @PathVariable Long id) {
        logger.info("Detach Institution by id:\t" + id + "\tfrom\t" + loggedInUser);
        accountInstitutionService.detachInstitutionFromAccount(loggedInUser, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
