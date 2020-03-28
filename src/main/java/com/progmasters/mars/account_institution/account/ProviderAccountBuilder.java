package com.progmasters.mars.account_institution.account;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetails;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
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
    private List<ProviderType> providerTypes;

    private String phone;
    private Integer zipcode;
    private String city;
    private String address;
    private Integer ageGroupMin;
    private Integer ageGroupMax;
    private List<InstitutionCreationCommand> institutions;
    private Boolean newsletter;

    public ProviderAccountCreationCommand buildCreationCommand() {
        return new ProviderAccountCreationCommand(providerServiceName, name, password, email, types, phone, zipcode, city, address, ageGroupMin, ageGroupMax, institutions, newsletter);
    }

    public ProviderUserDetails buildDetailsEdit() {
        return new ProviderUserDetails(email, name, providerServiceName, password, phone, zipcode, city, address, newsletter);
    }

    public ProviderAccount buildProviderAccount() {
        ProviderAccount providerAccount = new ProviderAccount();
        providerAccount.setProviderServiceName(providerServiceName);
        providerAccount.setName(name);
        providerAccount.setPassword(password);
        providerAccount.setEmail(email);
        providerAccount.setTypes(providerTypes);
        providerAccount.setPhone(phone);
        providerAccount.setZipcode(zipcode);
        providerAccount.setCity(city);
        providerAccount.setAddress(address);
        providerAccount.setAgeGroupMin(ageGroupMin);
        providerAccount.setAgeGroupMax(ageGroupMax);
        providerAccount.setNewsletter(newsletter);
        return providerAccount;
    }
}
