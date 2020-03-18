package com.progmasters.mars.controller;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.dto.InstitutionListData;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.dto.ProviderUserDetails;
import com.progmasters.mars.service.AccountService;
import com.progmasters.mars.validation.ProviderAccountValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class AccountController {

    private AccountService accountService;
    private ProviderAccountValidator providerAccountValidator;


    public AccountController(AccountService accountService, ProviderAccountValidator providerAccountValidator) {
        this.accountService = accountService;
        this.providerAccountValidator = providerAccountValidator;
    }

    @InitBinder("providerAccountCreationCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(providerAccountValidator);
    }

    @PostMapping
    public ResponseEntity createInstitution(@RequestBody @Valid ProviderAccountCreationCommand providerAccountCreationCommand) {
        accountService.createProviderAccount(providerAccountCreationCommand);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProviderUserDetails> getProviderUser(@PathVariable String username) {
        return new ResponseEntity<>(accountService.getProviderAccount(username), HttpStatus.OK);
    }

    @GetMapping("/getInstitutionsByType")
    public ResponseEntity<List<InstitutionListData>> getInstitutionByType(@RequestParam("type") String type) {

        InstitutionType institutionType = InstitutionType.getTypeByName(type);
        List<InstitutionListData> institutionListData = accountService.getInstitutionsByType(institutionType);
        return new ResponseEntity<>(institutionListData, HttpStatus.OK);
    }
}
