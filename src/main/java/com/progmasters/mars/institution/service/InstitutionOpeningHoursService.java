package com.progmasters.mars.institution.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account.domain.ProviderAccount;
import com.progmasters.mars.institution.domain.Institution;
import com.progmasters.mars.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.institution.openinghours.service.OpeningHoursService;
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

    public void saveInstitution(List<InstitutionCreationCommand> institutionList, ProviderAccount providerAccount) throws NotFoundException {
        for (InstitutionCreationCommand institutionCreationCommand : institutionList) {
            Institution institution = null;

            institution = institutionService.saveToAccount(institutionCreationCommand, providerAccount);

            openingHoursService.saveToInstitution(institutionCreationCommand.getOpeningHours(), institution);

        }
    }
}
