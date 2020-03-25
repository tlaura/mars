package com.progmasters.mars.account_institution;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.account.domain.InstitutionType;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;
import com.progmasters.mars.account_institution.institution.service.InstitutionOpeningHoursService;
import com.progmasters.mars.account_institution.institution.service.InstitutionService;
import com.progmasters.mars.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountInstitutionService {

    private final AccountService accountService;
    private final InstitutionService institutionService;
    private final InstitutionOpeningHoursService institutionOpeningHoursService;
    private final EmailService emailService;
    private final AccountInstitutionConnectorRepository accountInstitutionConnectorRepository;

    @Autowired
    public AccountInstitutionService(AccountService accountService, InstitutionService institutionService, InstitutionOpeningHoursService institutionOpeningHoursService, EmailService emailService, AccountInstitutionConnectorRepository accountInstitutionConnectorRepository) {
        this.accountService = accountService;
        this.institutionService = institutionService;
        this.institutionOpeningHoursService = institutionOpeningHoursService;
        this.emailService = emailService;
        this.accountInstitutionConnectorRepository = accountInstitutionConnectorRepository;
    }

    //todo read about spring boot exception handling
    public void save(ProviderAccountCreationCommand providerAccountCreationCommand) throws NotFoundException {
        ProviderAccount savedAccount = accountService.save(providerAccountCreationCommand);
        List<InstitutionCreationCommand> institutions = providerAccountCreationCommand.getInstitutions();
        for (InstitutionCreationCommand institutionCreationCommand : institutions) {
            Institution savedInstitution = institutionOpeningHoursService.save(institutionCreationCommand);
            saveAccountInstitutionConnection(savedAccount, savedInstitution);
        }

        emailService.sendConfirmationEmail(savedAccount);
    }

    public void detachInstitutionFromAccount(String email, Long institutionId) {

    }


    private void saveAccountInstitutionConnection(ProviderAccount savedAccount, Institution savedInstitution) {
        AccountInstitutionConnector accountInstitutionConnector = new AccountInstitutionConnector(savedAccount, savedInstitution);
        accountInstitutionConnectorRepository.save(accountInstitutionConnector);
    }


    //todo revision

    public List<InstitutionListData> getInstitutionsByAccountType(InstitutionType institutionType) {
        List<InstitutionListData> institutionList = new ArrayList<>();
        List<ProviderAccount> accounts = accountService.getAccountsByType(institutionType);
        //  accounts.stream().map(institutionService::getInstitutionsByProviderAccount).forEach(institutionList::addAll);

        return institutionList;
    }

//    public void deleteAccountById(Long id) {
//        ProviderAccount account = accountService.findById(id);
//        if (!account.getInstitutions().isEmpty()) {
//            account.getInstitutions().forEach(institutionService::detachFromAccount);
//        }
//        accountService.removeById(id);
//    }
}
