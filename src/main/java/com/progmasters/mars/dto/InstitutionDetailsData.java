package com.progmasters.mars.dto;

import com.progmasters.mars.domain.Institution;

public class InstitutionDetailsData {

    private Long id;
    private String name;
    private String zipCode;
    private String city;
    private String address;
    private String email;
    private String description;
    private String institutionType;
    private String creatorName;

    public InstitutionDetailsData() {
    }

    public InstitutionDetailsData(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
        this.zipCode = institution.getZipCode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
        this.email = institution.getEmail();
        this.description = institution.getDescription();
        this.institutionType = institution.getInstitutionType().getHungarianName();
        this.creatorName = institution.getCreator().getFirstName() + " " + institution.getCreator().getLastName();
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


    public String getEmail() {
        return email;
    }


    public String getDescription() {
        return description;
    }


    public String getCreatorName() {
        return creatorName;
    }

    public String getInstitutionType() {
        return institutionType;
    }

}
