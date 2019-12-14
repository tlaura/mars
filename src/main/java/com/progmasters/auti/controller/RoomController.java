package com.progmasters.auti.controller;

import com.progmasters.auti.dto.AddInstitutionForm;
import com.progmasters.auti.dto.InstitutionDetails;
import com.progmasters.auti.dto.InstitutionList;
import com.progmasters.auti.service.InstitutionService;
import com.progmasters.auti.validation.AddInstitutionFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/institutions")
public class RoomController {

    private InstitutionService institutionService;
    private AddInstitutionFormValidator addInstitutionFormValidator;

    @Autowired
    public RoomController(InstitutionService institutionService, AddInstitutionFormValidator addInstitutionFormValidator) {
        this.institutionService = institutionService;
        this.addInstitutionFormValidator = addInstitutionFormValidator;
    }

    @InitBinder("addInstitutionForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addInstitutionFormValidator);
    }

    @GetMapping
    public InstitutionList institutions() {
        return institutionService.getInstitutionList();
    }

    @GetMapping("/{id}")
    public InstitutionDetails institutionDetails(@PathVariable("id") Long id) {
        return institutionService.getInstitutionDetails(id);
    }

    @PostMapping
    public ResponseEntity createInstitution(@RequestBody @Valid AddInstitutionForm addInstitutionForm) {
        institutionService.createInstitution(addInstitutionForm);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
