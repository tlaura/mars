package com.progmasters.mars.account.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account.domain.InstitutionType;
import com.progmasters.mars.account.domain.ProviderAccount;
import com.progmasters.mars.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.institution.dto.InstitutionListData;
import com.progmasters.mars.institution.service.InstitutionOpeningHoursService;
import com.progmasters.mars.institution.service.InstitutionService;
import com.progmasters.mars.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountInstitutionService {

    private final AccountService accountService;
    private final InstitutionService institutionService;
    private final InstitutionOpeningHoursService institutionOpeningHoursService;
    private final EmailService emailService;

    @Autowired
    public AccountInstitutionService(AccountService accountService, InstitutionService institutionService, InstitutionOpeningHoursService institutionOpeningHoursService, EmailService emailService) {
        this.accountService = accountService;
        this.institutionService = institutionService;
        this.institutionOpeningHoursService = institutionOpeningHoursService;
        this.emailService = emailService;
    }

    public void saveAccount(ProviderAccountCreationCommand providerAccountCreationCommand) throws NotFoundException {
        ProviderAccount providerAccount = accountService.save(providerAccountCreationCommand);
        institutionOpeningHoursService.saveInstitution(providerAccountCreationCommand.getInstitutions(), providerAccount);
        emailService.sendConfirmationEmail(providerAccount);
    }

    public List<InstitutionListData> getInstitutionsByAccountType(InstitutionType institutionType) {
        List<InstitutionListData> institutionList = new ArrayList<>();
        List<ProviderAccount> accounts = accountService.getAccountsByType(institutionType);
        accounts.stream().map(institutionService::getInstitutionsByProviderAccount).forEach(institutionList::addAll);

        return institutionList;
    }

    public void deleteAccountById(Long id) {
        ProviderAccount account = accountService.findById(id);
        if (!account.getInstitutions().isEmpty()) {
            account.getInstitutions().forEach(institutionService::detachFromAccount);
        }
        accountService.removeById(id);
    }
}
