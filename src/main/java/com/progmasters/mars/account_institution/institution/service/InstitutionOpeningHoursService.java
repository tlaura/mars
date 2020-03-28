package com.progmasters.mars.account_institution.institution.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.service.OpeningHoursService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionOpeningHoursService {

    private final OpeningHoursService openingHoursService;
    private final InstitutionService institutionService;

    public InstitutionOpeningHoursService(OpeningHoursService openingHoursService, InstitutionService institutionService) {
        this.openingHoursService = openingHoursService;
        this.institutionService = institutionService;
    }

    public Institution save(InstitutionCreationCommand institutionCreationCommand) throws NotFoundException {
        Institution institution = institutionService.saveInstitution(institutionCreationCommand);
        saveOpeningHoursToInstitution(institution, institutionCreationCommand.getOpeningHours());

        return institution;
    }

    private void saveOpeningHoursToInstitution(Institution institution, List<OpeningHoursCreationCommand> openingHours) {
        openingHours.forEach(openingHoursCreationCommand -> openingHoursService.saveOpeningHoursToInstitution(openingHoursCreationCommand, institution));
    }

}
