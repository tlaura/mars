package com.progmasters.mars.account_institution.account.dto;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProviderAccountCreationCommand extends UserCreationCommand {

    @NotBlank
    @NotEmpty
    private String providerServiceName;

    @NotNull
    @Size(min = 1)
    private List<String> types;

    private Integer ageGroupMin;
    private Integer ageGroupMax;
    private List<InstitutionCreationCommand> institutions;

    @Builder
    public ProviderAccountCreationCommand(@NotBlank @NotEmpty String name, @NotBlank @NotEmpty String password, @NotBlank @NotEmpty String email, String website, String phone, Integer zipcode, String city, String address, Boolean newsletter, @NotBlank @NotEmpty String providerServiceName, @NotNull @Size(min = 1) List<String> types, Integer ageGroupMin, Integer ageGroupMax, List<InstitutionCreationCommand> institutions) {
        super(name, password, email, website, phone, zipcode, city, address, newsletter);
        this.providerServiceName = providerServiceName;
        this.types = types;
        this.ageGroupMin = ageGroupMin;
        this.ageGroupMax = ageGroupMax;
        this.institutions = institutions;
    }
}
