package com.progmasters.mars.controller;

import com.progmasters.mars.dto.AddInstitutionForm;
import com.progmasters.mars.dto.InstitutionDetails;
import com.progmasters.mars.dto.InstitutionListItem;
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

    @GetMapping("/test")
    public ResponseEntity testMail() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @InitBinder("addInstitutionForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addInstitutionFormValidator);
    }

    @GetMapping
    public List<InstitutionListItem> institutions() {
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
