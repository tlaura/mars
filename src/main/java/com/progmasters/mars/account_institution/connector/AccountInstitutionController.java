package com.progmasters.mars.account_institution.connector;

import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connectors")
public class AccountInstitutionController {

    private final Logger logger = LoggerFactory.getLogger(AccountInstitutionController.class);
    private final AccountInstitutionService accountInstitutionService;

    @Autowired
    public AccountInstitutionController(AccountInstitutionService accountInstitutionService) {
        this.accountInstitutionService = accountInstitutionService;
    }

    @GetMapping
    public ResponseEntity<List<AccountInstitutionListData>> getAllListItems() {
        logger.info("List items requested");
        List<AccountInstitutionListData> allListItems = accountInstitutionService.getAllListItems();

        return new ResponseEntity<>(allListItems, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> testSave(@RequestBody ProviderAccountCreationCommand providerAccountCreationCommand) {

        accountInstitutionService.tempSave(providerAccountCreationCommand);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
