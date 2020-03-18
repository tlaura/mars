package com.progmasters.mars.dto;

import com.progmasters.mars.domain.Institution;

public class InstitutionListData {

    private Long id;
    private String name;
    private Integer zipcode;
    private String city;
    private String address;
    private Double longitude;
    private Double latitude;


    public InstitutionListData() {
    }

    public InstitutionListData(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
        this.zipcode = institution.getZipcode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
        this.longitude = institution.getLongitude();
        this.latitude = institution.getLatitude();
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

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

}
