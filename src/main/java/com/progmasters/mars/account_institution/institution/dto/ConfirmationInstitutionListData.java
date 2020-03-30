package com.progmasters.mars.account_institution.institution.dto;

import com.progmasters.mars.account_institution.institution.domain.ConfirmationInstitution;

public class ConfirmationInstitutionListData {


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

    public ConfirmationInstitutionListData(ConfirmationInstitution confirmationInstitution) {
        this.id = confirmationInstitution.getId();
        this.name = confirmationInstitution.getName();
        this.description = confirmationInstitution.getDescription();
        this.email = confirmationInstitution.getEmail();
        this.website = confirmationInstitution.getWebsite();
        this.phone = confirmationInstitution.getPhone();
        this.zipcode = confirmationInstitution.getZipcode();
        this.city = confirmationInstitution.getCity();
        this.address = confirmationInstitution.getAddress();
        this.longitude = confirmationInstitution.getLongitude();
        this.latitude = confirmationInstitution.getLatitude();
    }
}
