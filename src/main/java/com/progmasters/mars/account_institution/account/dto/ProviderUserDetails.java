package com.progmasters.mars.account_institution.account.dto;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.connector.AccountInstitutionConnector;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
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
        if (!providerAccount.getTypes().isEmpty()) {
            this.types = providerAccount.getTypes().stream().map(ProviderType::getHungarianName).collect(Collectors.toList());
        }
        if (providerAccount.getAccountInstitutionConnectors() != null) {
            this.institutionList = providerAccount.getAccountInstitutionConnectors().stream().map(AccountInstitutionConnector::getInstitution).map(InstitutionListData::new).collect(Collectors.toList());
        }
        this.longitude = providerAccount.getLongitude();
        this.latitude = providerAccount.getLatitude();
    }

}
