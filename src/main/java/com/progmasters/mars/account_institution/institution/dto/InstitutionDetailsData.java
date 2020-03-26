package com.progmasters.mars.account_institution.institution.dto;

import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursData;

import java.util.List;
import java.util.stream.Collectors;

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

    private List<OpeningHoursData> openingHours;

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
        this.openingHours = institution.getOpeningHours().stream().map(OpeningHoursData::new).collect(Collectors.toList());

    }

    public InstitutionDetailsData(Long id, String name, Integer zipcode, String city, String address, String email, String description, Double longitude, Double latitude, String website, String phone) {
        this.id = id;
        this.name = name;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.email = email;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.website = website;
        this.phone = phone;
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

    public List<OpeningHoursData> getOpeningHours() {
        return openingHours;
    }
}