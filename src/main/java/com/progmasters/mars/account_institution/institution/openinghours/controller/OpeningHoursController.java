package com.progmasters.mars.account_institution.institution.openinghours.controller;

import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursData;
import com.progmasters.mars.account_institution.institution.openinghours.service.OpeningHoursService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/openingHours/")
@Slf4j
public class OpeningHoursController {

    private final OpeningHoursService openingHoursService;

    @Autowired
    public OpeningHoursController(OpeningHoursService openingHoursService) {
        this.openingHoursService = openingHoursService;
    }

    @GetMapping("{id}")
    public ResponseEntity<List<OpeningHoursData>> getOpeningHoursByInstitutionId(@PathVariable Long id) {
        List<OpeningHoursData> openingHoursList = openingHoursService.getOpeningHoursByInstitutionId(id);
        log.info("Opening hours is requested by institution ID: " + id);
        return new ResponseEntity<>(openingHoursList, HttpStatus.OK);
    }
}
