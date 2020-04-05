package com.progmasters.mars.account_institution.institution.domain;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "confirmation_institution")
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationInstitution extends TempInstitution {

    @OneToMany(mappedBy = "institution")
    private List<OpeningHours> openingHours;

    @Column(name = "provider_email")
    @ElementCollection
    private List<String> providerEmails;


    public ConfirmationInstitution(InstitutionCreationCommand institutionCreationCommand) {
        super.setName(institutionCreationCommand.getName());
        super.setZipcode(institutionCreationCommand.getZipcode());
        super.setCity(institutionCreationCommand.getCity());
        super.setEmail(institutionCreationCommand.getEmail());
        super.setAddress(institutionCreationCommand.getAddress());
        super.setDescription(institutionCreationCommand.getDescription());
        super.setPhone(institutionCreationCommand.getPhone());
        super.setWebsite(institutionCreationCommand.getWebsite());
        if (institutionCreationCommand.getOpeningHours() != null && !institutionCreationCommand.getOpeningHours().isEmpty()) {
            this.openingHours = institutionCreationCommand.getOpeningHours().stream().map(OpeningHours::new).collect(Collectors.toList());
        }
    }

    public ConfirmationInstitution(InstitutionCreationCommand institutionCreationCommand, String email) {
        this(institutionCreationCommand);
        providerEmails.add(email);
    }
}
