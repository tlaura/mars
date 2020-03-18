package com.progmasters.mars.dto;

import java.util.List;

public class InstitutionCreationCommand {

    private Long id;
    private String name;
    private Integer zipcode;
    private String city;
    private String address;
    private String email;
    private String description;
    private String website;
    private String phone;
    private List<OpeningHoursCreationCommand> openingHours;

    public InstitutionCreationCommand() {
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

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

}
