package com.progmasters.mars.util;

import com.progmasters.mars.dto.InstitutionCreationCommand;
import com.progmasters.mars.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.dto.ProviderUserDetailsEdit;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProviderAccountBuilder {

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

    public ProviderAccountBuilder setProviderServiceName(String providerServiceName) {
        this.providerServiceName = providerServiceName;
        return this;
    }

    public ProviderAccountBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProviderAccountBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public ProviderAccountBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public ProviderAccountBuilder setTypes(List<String> types) {
        this.types = types;
        return this;
    }

    public ProviderAccountBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public ProviderAccountBuilder setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public ProviderAccountBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public ProviderAccountBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public ProviderAccountBuilder setAgeGroupMin(Integer ageGroupMin) {
        this.ageGroupMin = ageGroupMin;
        return this;
    }

    public ProviderAccountBuilder setAgeGroupMax(Integer ageGroupMax) {
        this.ageGroupMax = ageGroupMax;
        return this;
    }

    public ProviderAccountBuilder setInstitutions(List<InstitutionCreationCommand> institutions) {
        this.institutions = institutions;
        return this;
    }

    public ProviderAccountBuilder setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

    public ProviderAccountCreationCommand buildCreationCommand() {
        return new ProviderAccountCreationCommand(providerServiceName, name, password, email, types, phone, zipcode, city, address, ageGroupMin, ageGroupMax, institutions, newsletter);
    }

    public ProviderUserDetailsEdit buildDetailsEdit() {
        return new ProviderUserDetailsEdit(email, name, providerServiceName, password, phone, zipcode, city, address, newsletter);
    }
}
