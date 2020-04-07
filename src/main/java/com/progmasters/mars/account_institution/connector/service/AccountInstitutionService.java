package com.progmasters.mars.account_institution.connector.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.account_institution.connector.domain.AccountInstitutionConnector;
import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionAttachData;
import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionListData;
import com.progmasters.mars.account_institution.connector.repository.AccountInstitutionConnectorRepository;
import com.progmasters.mars.account_institution.institution.domain.ConfirmationInstitution;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import com.progmasters.mars.account_institution.institution.openinghours.service.OpeningHoursService;
import com.progmasters.mars.account_institution.institution.repository.InstitutionRepository;
import com.progmasters.mars.account_institution.institution.service.ConfirmationInstitutionService;
import com.progmasters.mars.account_institution.institution.service.InstitutionService;
import com.progmasters.mars.exception.EmailAlreadyExistsException;
import com.progmasters.mars.map.MapService;
import com.progmasters.mars.map.dto.GeoLocationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AccountInstitutionService {

    private static final Integer M_TO_KM = 1000;
    private static final Integer GOOGLE_MAX_DESTINATION_LIMIT_PER_QUERY = 25;


    private final AccountService accountService;
    private final InstitutionService institutionService;
    private final ConfirmationInstitutionService confirmationInstitutionService;
    private final MapService mapService;
    private final AccountInstitutionConnectorRepository accountInstitutionConnectorRepository;
    private final OpeningHoursService openingHoursService;
    private final InstitutionRepository institutionRepository;

    public ConfirmationInstitutionService getConfirmationInstitutionService() {
        return confirmationInstitutionService;
    }

    public InstitutionService getInstitutionService() {
        return institutionService;
    }

    @Autowired
    public AccountInstitutionService(AccountService accountService,
                                     InstitutionService institutionService,
                                     AccountInstitutionConnectorRepository accountInstitutionConnectorRepository,
                                     MapService mapService,
                                     ConfirmationInstitutionService confirmationInstitutionService,
                                     OpeningHoursService openingHoursService,
                                     InstitutionRepository institutionRepository) {
        this.accountService = accountService;
        this.institutionService = institutionService;
        this.accountInstitutionConnectorRepository = accountInstitutionConnectorRepository;
        this.mapService = mapService;
        this.confirmationInstitutionService = confirmationInstitutionService;
        this.openingHoursService = openingHoursService;
        this.institutionRepository = institutionRepository;
    }

    public void save(ProviderAccountCreationCommand providerAccountCreationCommand) throws NotFoundException {
        ProviderAccount savedAccount = (ProviderAccount) accountService.save(providerAccountCreationCommand);
        saveProviderLocation(providerAccountCreationCommand, savedAccount);
        List<InstitutionCreationCommand> institutions = providerAccountCreationCommand.getInstitutions();
        if (institutions != null && !institutions.isEmpty()) {
            for (InstitutionCreationCommand institutionCreationCommand : institutions) {
                @NotBlank @NotEmpty String institutionName = institutionCreationCommand.getName();
                Institution institution = institutionService.findByName(institutionName);
                ConfirmationInstitution confirmationInstitution = institutionService.findConfirmationInstitutionByName(institutionName);
                if (institution != null) {
                    AccountInstitutionConnector connection = new AccountInstitutionConnector(savedAccount, institution);
                    accountInstitutionConnectorRepository.save(connection);
                } else if (confirmationInstitution != null) {
                    confirmationInstitutionService.attachEmailToInstitution(confirmationInstitution, providerAccountCreationCommand.getEmail());
                } else {
                    confirmationInstitutionService.save(institutionCreationCommand, providerAccountCreationCommand.getEmail());
                }
            }
        }
    }


    private void saveProviderLocation(ProviderAccountCreationCommand providerAccountCreationCommand, ProviderAccount savedAccount) throws NotFoundException {
        if (providerAccountCreationCommand.getZipcode() != null && providerAccountCreationCommand.getCity() != null && providerAccountCreationCommand.getAddress() != null) {
            String address = providerAccountCreationCommand.getZipcode() + " " + providerAccountCreationCommand.getCity() + " " + providerAccountCreationCommand.getAddress();
            GeoLocationData geoLocationData = mapService.getGeoLocation(address);
            savedAccount.setLongitude(geoLocationData.getLongitude());
            savedAccount.setLatitude(geoLocationData.getLatitude());
        }
    }

    public void detachInstitutionFromAccount(String email, Long institutionId) {
        ProviderAccount foundAccount = (ProviderAccount) accountService.findByEmail(email);
        Institution foundInstitution = institutionService.findById(institutionId);
        accountInstitutionConnectorRepository.removeConnection(foundAccount, foundInstitution);
    }

    public void deleteAccountByEmail(String accountEmail) {
        User foundAccount = accountService.findByEmail(accountEmail);
        if (foundAccount instanceof ProviderAccount) {
            List<Institution> institutions = institutionService.getInstitutionsByAccount((ProviderAccount) foundAccount);
            institutions.forEach(institution -> accountInstitutionConnectorRepository.removeConnection((ProviderAccount) foundAccount, institution));
        }
        accountService.removeByEmail(foundAccount.getEmail());
    }

    public CompletableFuture<List<AccountInstitutionListData>> getAllListItems() {
        List<AccountInstitutionListData> allAccounts = new ArrayList<>();
        //   List<ProviderAccount> providerAccounts = accountService.findAllAccountsWithoutInstitution();
        //   List<Institution> institutions = institutionService.findInstitutionsWithoutProvider();
        //   List<Institution> institutionsWithAccount = institutionService.findInstitutionsWithProvider();


        CompletableFuture<Void> futureProviders = accountService.findAllAccountsWithoutInstitutionConcurrently()
                .thenAcceptAsync(providerAccounts -> providerAccounts
                        .stream()
                        .map(AccountInstitutionListData::new)
                        .forEach(allAccounts::add));
        CompletableFuture<Void> futureInstitutions = institutionService.findInstitutionsWithoutProviderConcurrently()
                .thenAcceptAsync(institutions -> institutions
                        .stream()
                        .map(AccountInstitutionListData::new)
                        .forEach(allAccounts::add));
        CompletableFuture<Void> futureInstitutionsWithProviders = institutionService.findInstitutionsWithProviderConcurrently()
                .thenAcceptAsync(institutions -> institutions
                        .stream()
                        .map(AccountInstitutionListData::new)
                        .forEach(allAccounts::add));

        CompletableFuture<Void> futureResult = CompletableFuture.allOf(futureProviders, futureInstitutions, futureInstitutionsWithProviders);

        //   providerAccounts.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
        //    institutions.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
        //    institutionsWithAccount.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
        return futureResult.thenApplyAsync(aVoid -> allAccounts);
    }

    public CompletableFuture<List<AccountInstitutionListData>> getListItemsByDistance(Double originLng, Double originLat, Long maxDistance) {

        return getAllListItems()
                .thenApplyAsync(allAccounts -> getSubLists(allAccounts)
                        .stream()
                        .map(subList ->
                                getAccountInstitutionListData(originLng, originLat, maxDistance, subList)
                        )
                        .flatMap(Collection::stream).collect(Collectors.toList()));
    }

    private List<List<AccountInstitutionListData>> getSubLists(List<AccountInstitutionListData> allAccounts) {
        List<List<AccountInstitutionListData>> listOfSublists = new ArrayList<>();
        for (int i = 0; i < allAccounts.size(); i += GOOGLE_MAX_DESTINATION_LIMIT_PER_QUERY) {
            int step = ((i + GOOGLE_MAX_DESTINATION_LIMIT_PER_QUERY) > allAccounts.size()) ? (allAccounts.size() - i) : GOOGLE_MAX_DESTINATION_LIMIT_PER_QUERY;
            List<AccountInstitutionListData> subList = allAccounts.subList(i, i + step);
            listOfSublists.add(subList);
        }
        return listOfSublists;
    }

    private List<AccountInstitutionListData> getAccountInstitutionListData(Double originLng, Double originLat, Long maxDistance, List<AccountInstitutionListData> subList) {
        List<AccountInstitutionListData> result = new ArrayList<>();
        try {
            result = mapService.findAccountsWithinRange(originLng, originLat, subList, maxDistance).get();
        } catch (InterruptedException | ExecutionException e) {
            log.info(e.getMessage());
        }
        return result;
    }

    public void evaluateInstitution(Long id, Boolean accepted) throws NotFoundException {
        ConfirmationInstitution confirmationInstitution = confirmationInstitutionService.findById(id);

        if (!institutionRepository.findAllByEmail(confirmationInstitution.getEmail()).isEmpty()) {
            throw new EmailAlreadyExistsException("Institution with the same email already exists.");
        }

        if (accepted) {
            saveAccountInstitutionConnection(confirmationInstitution);
        } else {
            List<OpeningHours> openingHours = confirmationInstitution.getOpeningHours();
            openingHours.forEach(openingHoursService::removeOpeningHours);
            confirmationInstitutionService.delete(confirmationInstitution);
        }
    }

    private void saveAccountInstitutionConnection(ConfirmationInstitution confirmationInstitution) throws NotFoundException {
        Institution createdInstitution = new Institution(confirmationInstitution);
        confirmationInstitution.setOpeningHours(null);
        confirmationInstitutionService.delete(confirmationInstitution);
        Institution savedInstitution = institutionService.saveInstitution(createdInstitution);
        List<String> emailList = confirmationInstitution.getProviderEmails();
        if (emailList != null && !emailList.isEmpty()) {
            emailList.stream()
                    .map(s -> (ProviderAccount) accountService.findByEmail(s))
                    .map(user -> new AccountInstitutionConnector(user, savedInstitution))
                    .forEach(accountInstitutionConnectorRepository::save);
        }
    }

    //todo revision
    public List<AccountInstitutionListData> getInstitutionsByAccountType(ProviderType providerType) {
        return institutionService.findInstitutionByProviderType(providerType).stream().map(AccountInstitutionListData::new).collect(Collectors.toList());
    }

    public void attachInstitutionToProvider(AccountInstitutionAttachData accountInstitutionAttachData) {
        ProviderAccount providerAccount = (ProviderAccount) accountService.findByEmail(accountInstitutionAttachData.getProviderEmail());
        Institution institution = institutionService.findById(accountInstitutionAttachData.getInstitutionId());

        AccountInstitutionConnector connection = accountInstitutionConnectorRepository.findByAccounts(providerAccount, institution);
        if (connection == null) {
            AccountInstitutionConnector accountInstitutionConnector = new AccountInstitutionConnector(providerAccount, institution);
            accountInstitutionConnectorRepository.save(accountInstitutionConnector);
        }
    }
}
