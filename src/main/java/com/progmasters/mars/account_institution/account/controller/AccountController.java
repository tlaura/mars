package com.progmasters.mars.account_institution.account.controller;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.account.ProviderAccountValidator;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.dto.PasswordChangeDetails;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetails;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.account_institution.connector.AccountInstitutionListData;
import com.progmasters.mars.account_institution.connector.AccountInstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
@Slf4j
public class AccountController {

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

    @GetMapping("/providerDetails/{id}")
    public ResponseEntity<ProviderUserDetails> getProviderUserDetail(@PathVariable Long id) {
        log.info("UserModel details is requested by id:\t" + id);
        ProviderUserDetails foundUserDetails = accountService.getUserDetailsById(id);

        return new ResponseEntity<>(foundUserDetails, HttpStatus.OK);
    }

    @GetMapping("/ageRange")
    public ResponseEntity<List<AccountInstitutionListData>> findProvidersByAgeRange(@RequestParam("age") Integer age) {
        List<AccountInstitutionListData> accounts = accountService.findProvidersByAgeRange(age);

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createProviderAccount(@RequestBody @Valid ProviderAccountCreationCommand providerAccountCreationCommand) {
        try {
            accountInstitutionService.save(providerAccountCreationCommand);
        } catch (NotFoundException e) {
            log.info("Location not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Provider Account creation requested!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProviderUserDetails> getProviderUser(@PathVariable String username) {
        log.info("Provider Account Details requested!");
        return new ResponseEntity<>(accountService.getProviderAccountByEmail(username), HttpStatus.OK);
    }

    @GetMapping("/providersByType")
    public ResponseEntity<List<AccountInstitutionListData>> getInstitutionByType(@RequestParam("type") String type) {
        ProviderType providerType = ProviderType.valueOf(type);
        List<AccountInstitutionListData> providerListByTypes = accountInstitutionService.getInstitutionsByAccountType(providerType);

        log.info("Institution List is requested by type!");
        return new ResponseEntity<>(providerListByTypes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProviderAccount(@Valid @RequestBody ProviderUserDetails providerUserDetails, @PathVariable Long id) {
        log.info("Provider Account update requested");
        accountService.updateProviderAccountDetails(providerUserDetails, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{loggedInUser}/{id}")
    public ResponseEntity<Void> removeAccountInstitutionConnection(@PathVariable String loggedInUser, @PathVariable Long id) {
        log.info("Detach Institution by id:\t" + id + "\tfrom\t" + loggedInUser);
        accountInstitutionService.detachInstitutionFromAccount(loggedInUser, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/passwordChange")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody PasswordChangeDetails passwordChangeDetails) {
        log.info("Password update requested");
        boolean isChangeValid = accountService.updatePasswordOfLoggedInUser(passwordChangeDetails);
        return isChangeValid ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
