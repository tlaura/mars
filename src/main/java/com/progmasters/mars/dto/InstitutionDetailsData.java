package com.progmasters.mars.dto;

import com.progmasters.mars.domain.Institution;

public class InstitutionDetailsData {

    private Long id;

    private String name;

    private Integer zipcode;

    private String city;

    private String address;

    private String email;

    private String description;

    private Double longitude;

    private Double latitude;

    private String website;

    private String phone;

    //todo add opening hours


    public InstitutionDetailsData(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
        this.zipcode = institution.getZipcode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
        this.email = institution.getEmail();
        this.description = institution.getDescription();
        this.longitude = institution.getLongitude();
        this.latitude = institution.getLatitude();
        this.website = institution.getWebsite();
        this.phone = institution.getPhone();

    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }
}
