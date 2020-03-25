package com.progmasters.mars.account_institution.institution;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursCreationCommand;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public class InstitutionBuilder {

    @NotBlank
    @NotEmpty
    private String name;

    @PositiveOrZero
    private Integer zipcode;

    @NotBlank
    @NotEmpty
    private String city;

    @NotBlank
    @NotEmpty
    private String address;

    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @Lob
    @NotBlank
    @Length(min = 30)
    private String description;
    private String website;
    private String phone;
    private List<OpeningHoursCreationCommand> openingHoursCreationCommands;
    private List<OpeningHours> openingHours;

    public InstitutionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public InstitutionBuilder setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public InstitutionBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public InstitutionBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public InstitutionBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public InstitutionBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public InstitutionBuilder setWebsite(String website) {
        this.website = website;
        return this;
    }

    public InstitutionBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public InstitutionBuilder setOpeningHours(List<OpeningHours> openingHours) {
        this.openingHours = openingHours;
        return this;
    }

    public InstitutionBuilder setOpeningHoursCreationCommands(List<OpeningHoursCreationCommand> openingHours) {
        this.openingHoursCreationCommands = openingHours;
        return this;
    }


    public InstitutionCreationCommand buildInstitutionCreationCommand() {
        InstitutionCreationCommand institutionCreationCommand = new InstitutionCreationCommand();
        institutionCreationCommand.setName(name);
        institutionCreationCommand.setEmail(email);
        institutionCreationCommand.setZipcode(zipcode);
        institutionCreationCommand.setCity(city);
        institutionCreationCommand.setAddress(address);
        institutionCreationCommand.setPhone(phone);
        institutionCreationCommand.setWebsite(website);
        institutionCreationCommand.setOpeningHours(openingHoursCreationCommands);
        institutionCreationCommand.setDescription(description);
        return institutionCreationCommand;
    }
}
