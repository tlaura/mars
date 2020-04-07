package com.progmasters.mars.account_institution.connector.controller;

import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionAttachData;
import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionListData;
import com.progmasters.mars.account_institution.connector.service.AccountInstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @Async
    @GetMapping
    public CompletableFuture<ResponseEntity<List<AccountInstitutionListData>>> getAllListItems() {
        log.info("List items requested");
//        ResponseEntity<List<AccountInstitutionListData>> responseEntity;
//        try {
//            List<AccountInstitutionListData> allAccounts = accountInstitutionService.getAllListItems().get();
//            responseEntity = new ResponseEntity<>(allAccounts, HttpStatus.OK);
//        } catch (InterruptedException | ExecutionException e) {
//            log.info(e.getMessage());
//            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return responseEntity;
        return accountInstitutionService.getAllListItems().thenApplyAsync(ResponseEntity::ok);
    }

    @Async
    @GetMapping("/listByRange")
    public CompletableFuture<ResponseEntity<List<AccountInstitutionListData>>> getListByRange(@RequestParam("range") Long maxDistance, @RequestParam("lng") Double originLng, @RequestParam("lat") Double originLat) {

        log.info("List items by range requested");

        return accountInstitutionService.getListItemsByDistance(originLng, originLat, maxDistance).thenApplyAsync(ResponseEntity::ok);

//        CompletableFuture<List<AccountInstitutionListData>> filteredList = accountInstitutionService.getListItemsByDistance(originLng, originLat, maxDistance);
//        ResponseEntity<List<AccountInstitutionListData>> responseEntity;
//        try {
//            return new ResponseEntity<>(filteredList.get(), HttpStatus.OK);
//        } catch (InterruptedException | ExecutionException e) {
//            log.info(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

    }

    @PostMapping
    public ResponseEntity<Void> attachInstitutionToProvicer(@RequestBody AccountInstitutionAttachData accountInstitutionAttachData) {
        accountInstitutionService.attachInstitutionToProvider(accountInstitutionAttachData);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
