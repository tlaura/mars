package com.progmasters.mars.account_institution.connector;

import com.progmasters.mars.account_institution.account.AccountType;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetails;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionDetailsData;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursData;
import com.progmasters.mars.map.dto.DistanceData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AccountInstitutionListData {

    private String accountType;
    //account info
    private Long id;

    private String providerServiceName;

    private String name;

    private String email;

    private List<String> types;

    private String phone;

    private Integer zipcode;

    private String city;

    private String address;

    private Integer ageGroupMin;

    private Integer ageGroupMax;

    private Boolean newsletter;

    private Double latitude;

    private Double longitude;

    private List<InstitutionDetailsData> institutions = new ArrayList<>();

    private List<ProviderUserDetails> providers = new ArrayList<>();

    //institution info
    private String description;

    private List<OpeningHoursData> openingHours;

    private String website;

    private DistanceData distanceData;

    public AccountInstitutionListData(Institution institution) {

        this.id = institution.getId();
        this.name = institution.getName();
        this.zipcode = institution.getZipcode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
        this.email = institution.getEmail();
        this.description = institution.getDescription();
        this.longitude = institution.getLongitude();
        this.latitude = institution.getLatitude();
        this.openingHours = institution.getOpeningHours().stream().map(OpeningHoursData::new).collect(Collectors.toList());
        this.website = institution.getWebsite();
        this.phone = institution.getPhone();

        List<AccountInstitutionConnector> institutionConnectors = institution.getAccountInstitutionConnectors();
        if (!institutionConnectors.isEmpty()) {
            this.providers = institutionConnectors.stream().map(AccountInstitutionConnector::getProviderAccount).map(ProviderUserDetails::new).collect(Collectors.toList());
            this.accountType = AccountType.INSTITUTION_WITH_PROVIDER.toString();
        } else {
            this.accountType = AccountType.INSTITUTION.toString();
        }

    }

    public AccountInstitutionListData(ProviderAccount providerAccount) {

        this.id = providerAccount.getId();
        this.providerServiceName = providerAccount.getProviderServiceName();
        this.name = providerAccount.getName();
        this.email = providerAccount.getEmail();
        this.types = providerAccount.getTypes().stream().map(ProviderType::getHungarianName).collect(Collectors.toList());
        this.phone = providerAccount.getPhone();
        this.zipcode = providerAccount.getZipcode();
        this.city = providerAccount.getCity();
        this.address = providerAccount.getAddress();
        this.ageGroupMin = providerAccount.getAgeGroupMin();
        this.ageGroupMax = providerAccount.getAgeGroupMax();
        this.newsletter = providerAccount.getNewsletter();
        this.latitude = providerAccount.getLatitude();
        this.longitude = providerAccount.getLongitude();

        List<AccountInstitutionConnector> accountConnectors = providerAccount.getAccountInstitutionConnectors();
        if (accountConnectors != null && !accountConnectors.isEmpty()) {
            this.institutions = accountConnectors.stream().map(AccountInstitutionConnector::getInstitution).map(InstitutionDetailsData::new).collect(Collectors.toList());
            this.accountType = AccountType.PROVIDER_WITH_INSTITUTION.toString();
        } else {
            this.accountType = AccountType.PROVIDER.toString();
        }
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
