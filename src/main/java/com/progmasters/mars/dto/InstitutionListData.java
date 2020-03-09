package com.progmasters.mars.dto;

import com.progmasters.mars.domain.Institution;

public class InstitutionListData {

    private Long id;
    private String name;
    private Integer zipCode;
    private String city;
    private String address;


    public InstitutionListData() {
    }

    public InstitutionListData(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
        this.zipCode = institution.getZipCode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
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
}
