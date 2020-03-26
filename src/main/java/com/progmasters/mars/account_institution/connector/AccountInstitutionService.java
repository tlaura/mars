package com.progmasters.mars.account_institution.connector;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.location.GeoLocation;
import com.progmasters.mars.account_institution.institution.location.GeocodeService;
import com.progmasters.mars.account_institution.institution.service.InstitutionOpeningHoursService;
import com.progmasters.mars.account_institution.institution.service.InstitutionService;
import com.progmasters.mars.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountInstitutionService {

    private final AccountService accountService;
    private final InstitutionService institutionService;
    private final InstitutionOpeningHoursService institutionOpeningHoursService;
    private final EmailService emailService;
    private final GeocodeService geocodeService;
    private final AccountInstitutionConnectorRepository accountInstitutionConnectorRepository;

    @Autowired
    public AccountInstitutionService(AccountService accountService,
                                     InstitutionService institutionService,
                                     InstitutionOpeningHoursService institutionOpeningHoursService,
                                     EmailService emailService,
                                     AccountInstitutionConnectorRepository accountInstitutionConnectorRepository,
                                     GeocodeService geocodeService) {
        this.accountService = accountService;
        this.institutionService = institutionService;
        this.institutionOpeningHoursService = institutionOpeningHoursService;
        this.emailService = emailService;
        this.accountInstitutionConnectorRepository = accountInstitutionConnectorRepository;
        this.geocodeService = geocodeService;
    }

    //todo read about spring boot exception handling
    public void save(ProviderAccountCreationCommand providerAccountCreationCommand) throws NotFoundException {
        ProviderAccount savedAccount = accountService.save(providerAccountCreationCommand);
        if (providerAccountCreationCommand.getZipcode() != null && providerAccountCreationCommand.getCity() != null && providerAccountCreationCommand.getAddress() != null) {
            String address = providerAccountCreationCommand.getZipcode() + " " + providerAccountCreationCommand.getCity() + " " + providerAccountCreationCommand.getAddress();
            GeoLocation geoLocation = geocodeService.getGeoLocation(address);
            savedAccount.setLongitude(geoLocation.getLongitude());
            savedAccount.setLatitude(geoLocation.getLatitude());
        }
        List<InstitutionCreationCommand> institutions = providerAccountCreationCommand.getInstitutions();
        if (!institutions.isEmpty()) {
            for (InstitutionCreationCommand institutionCreationCommand : institutions) {
                Institution savedInstitution = institutionOpeningHoursService.save(institutionCreationCommand);
                saveAccountInstitutionConnection(savedAccount, savedInstitution);
            }
        }
        emailService.sendConfirmationEmail(savedAccount);
    }

    public void detachInstitutionFromAccount(String email, Long institutionId) {

        ProviderAccount foundAccount = accountService.findByEmail(email);
        Institution foundInstitution = institutionService.findById(institutionId);
        accountInstitutionConnectorRepository.removeConnection(foundAccount, foundInstitution);
    }

    public void deleteAccountById(Long accountId) {
        ProviderAccount foundAccount = accountService.findById(accountId);
        List<Institution> institutions = institutionService.getInstitutionsByAccount(foundAccount);
        institutions.forEach(institution -> accountInstitutionConnectorRepository.removeConnection(foundAccount, institution));
        accountService.removeById(foundAccount.getId());
    }

    public List<AccountInstitutionListData> getAllListItems() {
        List<AccountInstitutionListData> allAccounts = new ArrayList<>();
        List<ProviderAccount> providerAccounts = accountService.findAllAccountsWithoutInstitution();
        List<ProviderAccount> providerAccountWithInstitution = accountService.findAllAccountsWithInstitution();
        List<Institution> institutions = institutionService.findInstitutionsWithoutProvider();
        List<Institution> institutionsWithAccount = institutionService.findInstitutionsWithProvider();
        providerAccounts.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
        institutions.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
      //  providerAccountWithInstitution.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
        institutionsWithAccount.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);

        return allAccounts;
    }


    private void saveAccountInstitutionConnection(ProviderAccount savedAccount, Institution savedInstitution) {
        AccountInstitutionConnector accountInstitutionConnector = new AccountInstitutionConnector(savedAccount, savedInstitution);
        accountInstitutionConnectorRepository.save(accountInstitutionConnector);
    }


    //todo revision

    public List<AccountInstitutionListData> getInstitutionsByAccountType(ProviderType providerType) {

        return institutionService.findInstitutionByProviderType(providerType).stream().map(AccountInstitutionListData::new).collect(Collectors.toList());
    }
}
