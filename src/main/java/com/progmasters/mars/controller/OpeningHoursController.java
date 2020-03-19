package com.progmasters.mars.controller;

import com.progmasters.mars.dto.OpeningHoursData;
import com.progmasters.mars.service.OpeningHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/openingHours/")
public class OpeningHoursController {

    private final OpeningHoursService openingHoursService;

    public OpeningHoursController(OpeningHoursService openingHoursService) {
        this.openingHoursService = openingHoursService;
    }

    @GetMapping("{id}")
    public ResponseEntity<List<OpeningHoursData>> getOpeningHoursByInstitutionId(@PathVariable Long id) {
        List<OpeningHoursData> openingHoursList = openingHoursService.getOpeningHoursByInstitutionId(id);

        return new ResponseEntity<>(openingHoursList, HttpStatus.OK);
    }
}
