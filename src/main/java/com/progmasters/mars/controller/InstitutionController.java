package com.progmasters.mars.controller;

import com.progmasters.mars.dto.InstitutionCreationForm;
import com.progmasters.mars.dto.InstitutionDetails;
import com.progmasters.mars.dto.InstitutionListData;
import com.progmasters.mars.service.InstitutionService;
import com.progmasters.mars.validation.AddInstitutionFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

    private InstitutionService institutionService;
    private AddInstitutionFormValidator addInstitutionFormValidator;

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
    public ResponseEntity testMail() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public List<InstitutionListData> institutions() {
        //todo handle sorting
        return institutionService.getInstitutionList();
    }


    @GetMapping("/{id}")
    public InstitutionDetails getInstitutionDetails(@PathVariable("id") Long id) {
        return institutionService.getInstitutionDetails(id);
    }

    @PostMapping
    public ResponseEntity<Void> createInstitution(@RequestBody @Valid InstitutionCreationForm institutionCreationForm) {
        institutionService.createInstitution(institutionCreationForm);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
