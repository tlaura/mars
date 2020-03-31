package com.progmasters.mars.account_institution.account.dto;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
