package com.progmasters.mars.controller;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.dto.InstitutionCreationCommand;
import com.progmasters.mars.dto.InstitutionDetailsData;
import com.progmasters.mars.dto.InstitutionListData;
import com.progmasters.mars.dto.InstitutionTypeData;
import com.progmasters.mars.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;
    private final Logger logger = LoggerFactory.getLogger(InstitutionController.class);

    //TODO Legalább a controllerekbe érdemes rakni mindenhova logolást!

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping
    public ResponseEntity<List<InstitutionListData>> institutions() {
        //todo handle sorting

        List<InstitutionListData> institutionList = institutionService.getInstitutionList();
        logger.info("Institution List is requested!");
        return new ResponseEntity<>(institutionList, HttpStatus.OK);
    }

    @GetMapping("/institutionType")
    public ResponseEntity<List<InstitutionTypeData>> getInstitutionType() {
        List<InstitutionTypeData> institutionTypeDataList = Arrays.stream(InstitutionType.values()).map(InstitutionTypeData::new).collect(Collectors.toList());

        logger.info("Institution types is requested!");
        return new ResponseEntity<>(institutionTypeDataList, HttpStatus.OK);
    }

//    @GetMapping("/details")
//    public List<InstitutionDetailsData> institutionsDetails() {
//        return institutionService.getInstitutionDetailsDataList();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDetailsData> getInstitutionDetails(@PathVariable("id") Long id) {
        logger.info("Institution is requested by id: " + id);
        return new ResponseEntity<>(institutionService.getInstitutionDetails(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createInstitution(@RequestBody @Valid InstitutionCreationCommand institutionCreationCommand) {
        institutionService.createInstitution(institutionCreationCommand);
        logger.info("Institution creation is requested!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @PutMapping("/locationUpdate/{id}")
//    public ResponseEntity<Void> updateInstitutionLocation(@PathVariable Long id, @RequestBody GeoLocationData geoLocationData) {
//        institutionService.updateInstitutionLocation(geoLocationData, id);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/import")
    public ResponseEntity<Void> importInstitutions(@RequestParam("file") MultipartFile excelDataFile) {
        institutionService.importInstitutions(excelDataFile);
        logger.info("Institution import requested!");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
