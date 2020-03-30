package com.progmasters.mars.account_institution.institution.dto;

import com.progmasters.mars.account_institution.institution.domain.Institution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class InstitutionListData {

    private Long id;
    private String name;
    private String description;
    private String email;
    private String website;
    private String phone;
    private Integer zipcode;
    private String city;
    private String address;
    private Double longitude;
    private Double latitude;

    public InstitutionListData(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
        this.description = institution.getDescription();
        this.email = institution.getEmail();
        this.website = institution.getWebsite();
        this.phone = institution.getPhone();
        this.zipcode = institution.getZipcode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
        this.longitude = institution.getLongitude();
        this.latitude = institution.getLatitude();
    }
}
