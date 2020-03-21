package com.progmasters.mars.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.ProviderAccount;
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

    public void saveInstitution(List<InstitutionCreationCommand> institutionList, ProviderAccount providerAccount) {
        for (InstitutionCreationCommand institutionCreationCommand : institutionList) {
            Institution institution = null;
            try {

                institution = institutionService.saveToAccount(institutionCreationCommand, providerAccount);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            openingHoursService.saveToInstitution(institutionCreationCommand.getOpeningHours(), institution);

        }
    }
}
