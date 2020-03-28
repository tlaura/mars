package com.progmasters.mars.account_institution.institution.dto;

import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursCreationCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InstitutionCreationCommand {

    private Long id;

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
    private List<OpeningHoursCreationCommand> openingHours;

}
