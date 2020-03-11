package com.progmasters.mars.controller;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.dto.*;
import com.progmasters.mars.service.InstitutionService;
import com.progmasters.mars.validation.AddInstitutionFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;
    private final AddInstitutionFormValidator addInstitutionFormValidator;


    @Autowired
    public InstitutionController(InstitutionService institutionService, AddInstitutionFormValidator addInstitutionFormValidator) {
        this.institutionService = institutionService;
        this.addInstitutionFormValidator = addInstitutionFormValidator;
    }


    @InitBinder("addInstitutionForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addInstitutionFormValidator);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<InstitutionListData>> institutions() {
        //todo handle sorting

        //todo switch in the future
        // List<InstitutionListData> institutionList = institutionService.getInstitutionList();
        List<InstitutionListData> institutionList = institutionService.tempInstitutionList();
        return new ResponseEntity<>(institutionList, HttpStatus.OK);
    }

    @GetMapping("/institutionType")
    public ResponseEntity<List<InstitutionTypeData>> getInstitutionType() {
        List<InstitutionTypeData> institutionTypeDataList = Arrays.stream(InstitutionType.values()).map(InstitutionTypeData::new).collect(Collectors.toList());

        return new ResponseEntity<>(institutionTypeDataList, HttpStatus.OK);
    }

    @GetMapping("/getInstitutionsByType")
    public ResponseEntity<List<InstitutionListData>> getInstitutionByType(@RequestParam("type") String type) {

        InstitutionType institutionType = InstitutionType.getTypeByName(type);
        //todo switch in the future
        //   List<InstitutionListData> institutionListData = institutionService.getInstitutionsByType(institutionType);
        List<InstitutionListData> institutionListData = institutionService.tempByType(institutionType);
        return new ResponseEntity<>(institutionListData, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDetailsData> getInstitutionDetails(@PathVariable("id") Long id) {
        return new ResponseEntity<>(institutionService.getInstitutionDetails(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createInstitution(@RequestBody @Valid InstitutionCreationForm institutionCreationForm) {
        institutionService.createInstitution(institutionCreationForm);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/locationUpdate/{id}")
    public ResponseEntity<Void> updateInstitutionLocation(@PathVariable Long id, @RequestBody GeoLocationData geoLocationData) {
        institutionService.updateInstitutionLocation(geoLocationData, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
