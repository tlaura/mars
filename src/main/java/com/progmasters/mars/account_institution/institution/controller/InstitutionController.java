package com.progmasters.mars.account_institution.institution.controller;

import com.google.maps.errors.NotFoundException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.connector.AccountInstitutionService;
import com.progmasters.mars.account_institution.institution.InstitutionValidator;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.dto.InstitutionDetailsData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionTypeData;
import com.progmasters.mars.account_institution.institution.service.ConfirmationInstitutionService;
import com.progmasters.mars.account_institution.institution.service.InstitutionService;
import com.progmasters.mars.map.MapService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class InstitutionController {

    private final InstitutionService institutionService;
    private InstitutionValidator institutionValidator;
    private final ConfirmationInstitutionService confirmationInstitutionService;
    private final AccountInstitutionService accountInstitutionService;

    @Autowired
    private MapService mapService;

    @Autowired
    public InstitutionController(InstitutionService institutionService,
                                 InstitutionValidator institutionValidator,
                                 ConfirmationInstitutionService confirmationInstitutionService,
                                 AccountInstitutionService accountInstitutionService) {
        this.institutionService = institutionService;
        this.institutionValidator = institutionValidator;
        this.confirmationInstitutionService = confirmationInstitutionService;
        this.accountInstitutionService = accountInstitutionService;
    }

    @GetMapping
    public ResponseEntity<List<InstitutionListData>> institutions() {
        //todo handle sorting

        List<InstitutionListData> institutionList = institutionService.getInstitutionList();
        log.info("Institution List is requested!");
        return new ResponseEntity<>(institutionList, HttpStatus.OK);
    }

    @GetMapping("/institutionType")
    public ResponseEntity<List<InstitutionTypeData>> getInstitutionType() {
        List<InstitutionTypeData> institutionTypeDataList = Arrays.stream(ProviderType.values()).map(InstitutionTypeData::new).collect(Collectors.toList());
        log.info("Institution types is requested!");
        return new ResponseEntity<>(institutionTypeDataList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDetailsData> getInstitutionDetails(@PathVariable("id") Long id) {
        log.info("Institution is requested by id: " + id);
        return new ResponseEntity<>(institutionService.getInstitutionDetailsById(id), HttpStatus.OK);
    }

    @InitBinder("institutionCreationCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(institutionValidator);
    }

    @PostMapping
    public ResponseEntity<Void> createInstitution(@RequestBody @Valid InstitutionCreationCommand institutionCreationCommand) {

        confirmationInstitutionService.save(institutionCreationCommand);
        log.info("Institution creation is requested!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/import")
    public ResponseEntity<Void> importInstitutions(@RequestParam("file") MultipartFile excelDataFile) {
        institutionService.importInstitutions(excelDataFile);
        log.info("Institution import requested!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/evaluationList")
    public ResponseEntity<List<InstitutionListData>> getConfirmationList() {
        List<InstitutionListData> confirmationList = confirmationInstitutionService.getConfirmationListData();
        log.info("Admin list is requested!");
        return new ResponseEntity<>(confirmationList, HttpStatus.OK);
    }

    @GetMapping("/evaluate/{id}")
    public ResponseEntity<Void> evaluateInstitution(@PathVariable Long id, @RequestParam("accepted") Boolean accepted) {
        try {
            accountInstitutionService.evaluateInstitution(id, accepted);
        } catch (NotFoundException e) {
            log.info("Location not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Institution evaluation is requested by id:\t" + id + "\n Decision:\t" + accepted);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/distance")
    public ResponseEntity<DistanceMatrix> getDistance(@RequestParam("origin") String origin, @RequestParam("destination") String destination) {
        DistanceMatrix matrix = mapService.calculateDistanceByGivenTravelMode(origin, destination, TravelMode.TRANSIT);

        return new ResponseEntity<>(matrix, HttpStatus.OK);
    }
}
