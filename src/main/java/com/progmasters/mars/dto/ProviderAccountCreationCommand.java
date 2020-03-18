package com.progmasters.mars.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProviderAccountCreationCommand {

    @NotBlank
    @NotEmpty
    private String providerServiceName;

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    private String password;

    @NotBlank
    @NotEmpty
    private String email;

    @NotNull
    @Size(min = 1)
    private List<String> types;

    private String phone;
    private Integer zipcode;
    private String city;
    private String address;
    private Integer ageGroupMin;
    private Integer ageGroupMax;
    private List<InstitutionCreationCommand> institutions;
    private Boolean newsletter;

    public ProviderAccountCreationCommand() {
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

    public Integer getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getTypes() {
        return types;
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

    public String getProviderServiceName() {
        return providerServiceName;
    }
}
