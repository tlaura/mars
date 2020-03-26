package com.progmasters.mars.account_institution.account.dto;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.connector.AccountInstitutionConnector;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;

import java.util.List;
import java.util.stream.Collectors;

public class ProviderUserDetails {

    private Long id;
    private String providerServiceName;
    private String name;
    private String email;
    private String phone;
    private Integer zipcode;
    private String city;
    private String address;
    private Integer ageGroupMin;
    private Integer ageGroupMax;
    private List<String> types;
    private List<InstitutionListData> institutionList;
    private Boolean newsletter;
    private Double longitude;
    private Double latitude;

    public ProviderUserDetails(ProviderAccount providerAccount) {
        this.id = providerAccount.getId();
        this.providerServiceName = providerAccount.getProviderServiceName();
        this.name = providerAccount.getName();
        this.email = providerAccount.getEmail();
        this.phone = providerAccount.getPhone();
        this.zipcode = providerAccount.getZipcode();
        this.city = providerAccount.getCity();
        this.address = providerAccount.getAddress();
        this.ageGroupMin = providerAccount.getAgeGroupMin();
        this.ageGroupMax = providerAccount.getAgeGroupMax();
        this.newsletter = providerAccount.getNewsletter();
        this.types = providerAccount.getTypes().stream().map(ProviderType::getHungarianName).collect(Collectors.toList());
        this.institutionList = providerAccount.getAccountInstitutionConnectors().stream().map(AccountInstitutionConnector::getInstitution).map(InstitutionListData::new).collect(Collectors.toList());
        this.longitude = providerAccount.getLongitude();
        this.latitude = providerAccount.getLatitude();
    }

    public List<InstitutionListData> getInstitutionList() {
        return institutionList;
    }

    public void setInstitutionList(List<InstitutionListData> institutionList) {
        this.institutionList = institutionList;
    }

    public ProviderUserDetails() {
    }

    public ProviderUserDetails(String email, String name, String providerServiceName, String password, String phone, Integer zipcode, String city, String address, Boolean newsletter) {

        this.email = email;
        this.name = name;
        this.providerServiceName = providerServiceName;
        this.phone = phone;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.newsletter = newsletter;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public String getEmail() {
        return email;
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

    public List<String> getTypes() {
        return types;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
