package com.progmasters.mars.account_institution.institution.controller;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.institution.InstitutionValidator;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.dto.InstitutionDetailsData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionTypeData;
import com.progmasters.mars.account_institution.institution.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
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
    private InstitutionValidator institutionValidator;

    @Autowired
    public InstitutionController(InstitutionService institutionService, InstitutionValidator institutionValidator) {
        this.institutionService = institutionService;
        this.institutionValidator = institutionValidator;
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
        List<InstitutionTypeData> institutionTypeDataList = Arrays.stream(ProviderType.values()).map(InstitutionTypeData::new).collect(Collectors.toList());
        logger.info("Institution types is requested!");
        return new ResponseEntity<>(institutionTypeDataList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDetailsData> getInstitutionDetails(@PathVariable("id") Long id) {
        logger.info("Institution is requested by id: " + id);
        return new ResponseEntity<>(institutionService.getInstitutionDetails(id), HttpStatus.OK);
    }

    @InitBinder("institutionCreationCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(institutionValidator);
    }

    @PostMapping
    public ResponseEntity<Void> createInstitution(@RequestBody @Valid InstitutionCreationCommand institutionCreationCommand) {
        try {
            institutionService.saveInstitution(institutionCreationCommand);
        } catch (NotFoundException e) {
            logger.info("Location not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Institution creation is requested!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importInstitutions(@RequestParam("file") MultipartFile excelDataFile) {
        institutionService.importInstitutions(excelDataFile);
        logger.info("Institution import requested!");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
