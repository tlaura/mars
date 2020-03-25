package com.progmasters.mars.account_institution;

import com.progmasters.mars.account_institution.account.AccountType;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionDetailsData;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountInstitutionListData {

    private AccountType accountType;
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


    //institution info

    private String description;

    private List<OpeningHoursData> openingHours;

    private String website;

    public AccountInstitutionListData() {
    }

    public AccountInstitutionListData(Institution institution) {
        this.accountType = AccountType.INSTITUTION;

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
    }

    public AccountInstitutionListData(ProviderAccount providerAccount) {
        this.accountType = AccountType.PROVIDER;

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
    }

    public AccountInstitutionListData(ProviderAccount providerAccount, List<Institution> institutions) {
        this(providerAccount);

        this.accountType = AccountType.PROVIDER_WITH_INSTITUTION;

        this.institutions = institutions.stream().map(InstitutionDetailsData::new).collect(Collectors.toList());

    }


    public AccountType getAccountType() {
        return accountType;
    }

    public Long getId() {
        return id;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Integer getAgeGroupMin() {
        return ageGroupMin;
    }

    public Integer getAgeGroupMax() {
        return ageGroupMax;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public List<InstitutionDetailsData> getInstitutions() {
        return institutions;
    }

    public String getDescription() {
        return description;
    }

    public List<OpeningHoursData> getOpeningHours() {
        return openingHours;
    }

    public String getWebsite() {
        return website;
    }
}
