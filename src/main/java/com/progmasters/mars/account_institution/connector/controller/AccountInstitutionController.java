package com.progmasters.mars.account_institution.connector.controller;

import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionAttachData;
import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionListData;
import com.progmasters.mars.account_institution.connector.service.AccountInstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connectors")
@Slf4j
public class AccountInstitutionController {

    private final AccountInstitutionService accountInstitutionService;

    public AccountInstitutionService getAccountInstitutionService() {
        return accountInstitutionService;
    }

    @Autowired
    public AccountInstitutionController(AccountInstitutionService accountInstitutionService) {
        this.accountInstitutionService = accountInstitutionService;
    }

    @GetMapping
    public ResponseEntity<List<AccountInstitutionListData>> getAllListItems() {
        log.info("List items requested");
        List<AccountInstitutionListData> allListItems = accountInstitutionService.getAllListItems();
        return new ResponseEntity<>(allListItems, HttpStatus.OK);
    }

    @GetMapping("/listByRange")
    public ResponseEntity<List<AccountInstitutionListData>> getListByRange(@RequestParam("range") Long maxDistance, @RequestParam("lng") Double originLng, @RequestParam("lat") Double originLat) {

        List<AccountInstitutionListData> accountsByRange = accountInstitutionService.getListItemsByDistance(originLng, originLat, maxDistance);
        log.info("List items by range requested");
        return new ResponseEntity<>(accountsByRange, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> attachInstitutionToProvicer(@RequestBody AccountInstitutionAttachData accountInstitutionAttachData) {
        accountInstitutionService.attachInstitutionToProvider(accountInstitutionAttachData);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
