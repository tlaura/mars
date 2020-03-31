package com.progmasters.mars.account_institution.connector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/connectors")
@Slf4j
public class AccountInstitutionController {

    private final AccountInstitutionService accountInstitutionService;

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
}
