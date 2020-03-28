package com.progmasters.mars.account_institution.institution;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursCreationCommand;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Setter
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
