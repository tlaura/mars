package com.progmasters.mars.dto;

import java.util.List;

public class ProviderAccountCreationCommand {
    private String providerServiceName;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String zipcode;
    private String city;
    private String address;
    private List<String> type;
    private Integer ageGroupMin;
    private Integer ageGroupMax;
    private List<InstitutionCreationCommand> institutions;
    private Boolean newsletter;

    public ProviderAccountCreationCommand() {
    }

    public String getProvidedServiceName() {
        return providerServiceName;
    }

    public String getName() {
        return name;
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

    public List<String> getType() {
        return type;
    }

    public Integer getAgeGroupMin() {
        return ageGroupMin;
    }

    public Integer getAgeGroupMax() {
        return ageGroupMax;
    }

    public List<InstitutionCreationCommand> getInstitutions() {
        return institutions;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }
}
