package com.progmasters.mars.account_institution.account.dto;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
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

    public ProviderAccountCreationCommand(@NotBlank @NotEmpty String providerServiceName, @NotBlank @NotEmpty String name, @NotBlank @NotEmpty String password, @NotBlank @NotEmpty String email, @NotNull @Size(min = 1) List<String> types, String phone, Integer zipcode, String city, String address, Integer ageGroupMin, Integer ageGroupMax, List<InstitutionCreationCommand> institutions, Boolean newsletter) {
        this.providerServiceName = providerServiceName;
        this.name = name;
        this.password = password;
        this.email = email;
        this.types = types;
        this.phone = phone;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.ageGroupMin = ageGroupMin;
        this.ageGroupMax = ageGroupMax;
        this.institutions = institutions;
        this.newsletter = newsletter;
    }
}
