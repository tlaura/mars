package com.progmasters.mars.dto;

import com.progmasters.mars.domain.Institution;

public class InstitutionDetails {

    private Long id;
    private String name;
    private String zipcode;
    private String city;
    private String address;
    private String email;
    private String description;
    private String creatorName;

    public InstitutionDetails() {
    }

    public InstitutionDetails(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
        this.zipcode = institution.getZipCode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
        this.email = institution.getEmail();
        this.description = institution.getDescription();
        this.creatorName = institution.getCreator().getFirstName() + " " + institution.getCreator().getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

}
