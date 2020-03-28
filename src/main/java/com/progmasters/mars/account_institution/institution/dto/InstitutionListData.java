package com.progmasters.mars.account_institution.institution.dto;

import com.progmasters.mars.account_institution.institution.domain.Institution;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
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

    public InstitutionListData(Long id, String name, String description, String email, String website, String phone, Integer zipcode, String city, String address, Double longitude, Double latitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
        this.website = website;
        this.phone = phone;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
