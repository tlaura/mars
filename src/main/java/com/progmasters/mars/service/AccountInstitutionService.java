package com.progmasters.mars.service;

import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import org.springframework.stereotype.Service;

@Service
public class AccountInstitutionService {

    private final AccountService accountService;
    private final InstitutionService institutionService;
    private final InstitutionOpeningHoursService institutionOpeningHoursService;

    public AccountInstitutionService(AccountService accountService, InstitutionService institutionService, InstitutionOpeningHoursService institutionOpeningHoursService) {
        this.accountService = accountService;
        this.institutionService = institutionService;
        this.institutionOpeningHoursService = institutionOpeningHoursService;
    }

    public void saveAccount(ProviderAccountCreationCommand providerAccountCreationCommand) {
        Long accountId = accountService.save(providerAccountCreationCommand);
        institutionOpeningHoursService.saveInstitution(providerAccountCreationCommand.getInstitutions(), accountId);
    }
}
