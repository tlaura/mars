package com.progmasters.mars.account_institution.institution.domain;

import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    private String providerEmail;

    public ConfirmationInstitution(InstitutionCreationCommand institutionCreationCommand) {
        super.setName(institutionCreationCommand.getName());
        super.setZipcode(institutionCreationCommand.getZipcode());
        super.setCity(institutionCreationCommand.getCity());
        super.setEmail(institutionCreationCommand.getEmail());
        super.setAddress(institutionCreationCommand.getAddress());
        super.setDescription(institutionCreationCommand.getDescription());
        super.setPhone(institutionCreationCommand.getPhone());
        super.setWebsite(institutionCreationCommand.getWebsite());
        this.openingHours = institutionCreationCommand.getOpeningHours().stream().map(OpeningHours::new).collect(Collectors.toList());
    }
}
