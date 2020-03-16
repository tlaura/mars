package com.progmasters.mars.dto;

import com.progmasters.mars.domain.Institution;

public class InstitutionListData {

    private Long id;
    private String name;
    private Integer zipCode;
    private String city;
    private String address;
    private String type;
    private Double longitude;
    private Double latitude;


    public InstitutionListData() {
    }

    public InstitutionListData(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
        this.zipCode = institution.getZipCode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
        if (institution.getInstitutionType() != null) {
            this.type = institution.getInstitutionType().getHungarianName();
        }
        this.longitude = institution.getLongitude();
        this.latitude = institution.getLatitude();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    //todo remove

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
