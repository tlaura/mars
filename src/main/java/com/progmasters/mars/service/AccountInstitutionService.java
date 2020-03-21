package com.progmasters.mars.service;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.InstitutionListData;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
