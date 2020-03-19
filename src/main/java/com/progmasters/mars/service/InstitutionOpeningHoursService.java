package com.progmasters.mars.service;

import com.progmasters.mars.dto.InstitutionCreationCommand;
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

    public void saveInstitution(List<InstitutionCreationCommand> institutionList, Long accountId) {
        for (InstitutionCreationCommand institutionCreationCommand : institutionList) {
            Long institutionId = institutionService.saveToAccount(institutionCreationCommand, accountId);
            openingHoursService.saveToInstitution(institutionCreationCommand.getOpeningHours(), institutionId);

        }
    }
}
