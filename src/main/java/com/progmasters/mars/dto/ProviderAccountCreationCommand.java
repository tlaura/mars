package com.progmasters.mars.dto;

import java.util.List;

public class ProviderAccountCreationCommand {
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String zipcode;
    private String city;
    private String address;
    private String type; //TODO: enum
    private List<OpeningHoursCreationCommand> openingHours;
    private Integer ageGroupMin;
    private Integer ageGroupMax;
    private List<InstitutionDetails> institutions;
    private Boolean newsletter;

    public ProviderAccountCreationCommand() {
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getZipcode() {
        return zipcode;
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

    public Integer getAgeGroupMin() {
        return ageGroupMin;
    }

    public Integer getAgeGroupMax() {
        return ageGroupMax;
    }

    public List<InstitutionDetails> getInstitutions() {
        return institutions;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public List<OpeningHoursCreationCommand> getOpeningHours() {
        return openingHours;
    }
}
