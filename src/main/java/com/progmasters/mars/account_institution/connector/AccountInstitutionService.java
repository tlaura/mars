package com.progmasters.mars.account_institution.connector;

import com.google.maps.errors.NotFoundException;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.account_institution.institution.domain.ConfirmationInstitution;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import com.progmasters.mars.account_institution.institution.openinghours.service.OpeningHoursService;
import com.progmasters.mars.account_institution.institution.service.ConfirmationInstitutionService;
import com.progmasters.mars.account_institution.institution.service.InstitutionService;
import com.progmasters.mars.map.MapService;
import com.progmasters.mars.map.dto.DistanceData;
import com.progmasters.mars.map.dto.GeoLocationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AccountInstitutionService {

    private static final Integer M_TO_KM = 1000;

    private final AccountService accountService;
    private final InstitutionService institutionService;
    private final ConfirmationInstitutionService confirmationInstitutionService;
    private final MapService mapService;
    private final AccountInstitutionConnectorRepository accountInstitutionConnectorRepository;
    private final OpeningHoursService openingHoursService;

    @Autowired
    public AccountInstitutionService(AccountService accountService,
                                     InstitutionService institutionService,
                                     AccountInstitutionConnectorRepository accountInstitutionConnectorRepository,
                                     MapService mapService,
                                     ConfirmationInstitutionService confirmationInstitutionService, OpeningHoursService openingHoursService) {
        this.accountService = accountService;
        this.institutionService = institutionService;
        this.accountInstitutionConnectorRepository = accountInstitutionConnectorRepository;
        this.mapService = mapService;
        this.confirmationInstitutionService = confirmationInstitutionService;
        this.openingHoursService = openingHoursService;
    }

    //todo read about spring boot exception handling
    public void save(ProviderAccountCreationCommand providerAccountCreationCommand) throws NotFoundException {
        ProviderAccount savedAccount = (ProviderAccount) accountService.save(providerAccountCreationCommand);
        saveProviderLocation(providerAccountCreationCommand, savedAccount);
        List<InstitutionCreationCommand> institutions = providerAccountCreationCommand.getInstitutions();
        if (!institutions.isEmpty()) {
            for (InstitutionCreationCommand institutionCreationCommand : institutions) {
                confirmationInstitutionService.save(institutionCreationCommand, providerAccountCreationCommand.getEmail());
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

    public void deleteAccountById(Long accountId) {
        ProviderAccount foundAccount = accountService.findById(accountId);
        List<Institution> institutions = institutionService.getInstitutionsByAccount(foundAccount);
        institutions.forEach(institution -> accountInstitutionConnectorRepository.removeConnection(foundAccount, institution));
        accountService.removeById(foundAccount.getId());
    }

    public List<AccountInstitutionListData> getAllListItems() {
        List<AccountInstitutionListData> allAccounts = new ArrayList<>();
        List<ProviderAccount> providerAccounts = accountService.findAllAccountsWithoutInstitution();
        List<Institution> institutions = institutionService.findInstitutionsWithoutProvider();
        List<Institution> institutionsWithAccount = institutionService.findInstitutionsWithProvider();
        providerAccounts.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
        institutions.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
        institutionsWithAccount.stream().map(AccountInstitutionListData::new).forEach(allAccounts::add);
        return allAccounts;
    }

    public List<AccountInstitutionListData> getListItemsByDistance(Double originLng, Double originLat, Long maxDistance) {
        List<AccountInstitutionListData> allAccounts = getAllListItems();

        return allAccounts.stream().filter(account -> {
            try {
                return isAccountWithinRange(originLng, originLat, maxDistance, account).get();
            } catch (InterruptedException | ExecutionException e) {
                log.info(e.getMessage());
                return false;
            }
        }).collect(Collectors.toList());

    }

    @Async
    CompletableFuture<Boolean> isAccountWithinRange(Double originLng, Double originLat, Long maxDistance, AccountInstitutionListData account) {
        boolean withinRange = false;
        if (account.getZipcode() != null && account.getCity() != null && account.getAddress() != null) {
            String destination = account.getZipcode() + " " + account.getCity() + " " + account.getAddress();
            List<TravelMode> travelModes = List.of(TravelMode.DRIVING, TravelMode.WALKING, TravelMode.TRANSIT);
            List<CompletableFuture<Boolean>> isWithinRangeList = travelModes.stream().map(travelMode -> getDistanceByTravelMode(originLng, originLat, destination, travelMode).thenApplyAsync(distanceData -> (distanceData != null) && (distanceData.getDistance() < maxDistance))).collect(Collectors.toList());
            withinRange = isWithinRangeList.stream().anyMatch(CompletableFuture::join);
        }
        return CompletableFuture.completedFuture(withinRange);
    }

    @Async
    CompletableFuture<DistanceData> getDistanceByTravelMode(Double originLng, Double originLat, String destination, TravelMode travelMode) throws DistanceCalculationException {
        return mapService.calculateDistanceByGivenTravelModeConcurrently(originLng, originLat, destination, travelMode).thenApplyAsync(matrix -> createDistanceData(matrix, travelMode));
    }

    private DistanceData createDistanceData(DistanceMatrix matrix, TravelMode travelMode) {
        Distance rawDistance = matrix.rows[0].elements[0].distance;
        Duration duration = matrix.rows[0].elements[0].duration;
        if (rawDistance != null && duration != null) {
            Long distance = rawDistance.inMeters / M_TO_KM;
            return new DistanceData(travelMode, distance, duration);
        }
        return null;
    }

    public void evaluateInstitution(Long id, Boolean accepted) throws NotFoundException {
        ConfirmationInstitution confirmationInstitution = confirmationInstitutionService.findById(id);
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
        if (confirmationInstitution.getProviderEmail() != null) {
            ProviderAccount savedAccount = (ProviderAccount) accountService.findByEmail(confirmationInstitution.getProviderEmail());
            AccountInstitutionConnector accountInstitutionConnector = new AccountInstitutionConnector(savedAccount, savedInstitution);
            accountInstitutionConnectorRepository.save(accountInstitutionConnector);
        }
    }


    //todo revision

    public List<AccountInstitutionListData> getInstitutionsByAccountType(ProviderType providerType) {
        return institutionService.findInstitutionByProviderType(providerType).stream().map(AccountInstitutionListData::new).collect(Collectors.toList());
    }
}
