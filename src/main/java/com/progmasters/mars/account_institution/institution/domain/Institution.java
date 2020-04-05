package com.progmasters.mars.account_institution.institution.domain;

import com.progmasters.mars.account_institution.connector.domain.AccountInstitutionConnector;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import com.progmasters.mars.map.dto.GeoLocationData;
import com.progmasters.mars.util.ExcelFileLoader;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "institution")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Institution extends TempInstitution {


    @OneToMany(mappedBy = "institution")
    private List<OpeningHours> openingHours;

    @OneToMany(mappedBy = "institution")
    private List<AccountInstitutionConnector> accountInstitutionConnectors;

    @OneToOne(mappedBy = "institution")
    InstitutionPetition institutionPetition;


    public Institution(InstitutionCreationCommand institutionCreationCommand) {
        super.setName(institutionCreationCommand.getName());
        super.setZipcode(institutionCreationCommand.getZipcode());
        super.setCity(institutionCreationCommand.getCity());
        super.setEmail(institutionCreationCommand.getEmail());
        super.setAddress(institutionCreationCommand.getAddress());
        super.setDescription(institutionCreationCommand.getDescription());
        super.setPhone(institutionCreationCommand.getPhone());
        super.setWebsite(institutionCreationCommand.getWebsite());
    }

    public Institution(InstitutionCreationCommand institutionCreationCommand, GeoLocationData geoLocationData) {
        this(institutionCreationCommand);
        super.setLongitude(geoLocationData.getLongitude());
        super.setLatitude(geoLocationData.getLatitude());

    }

    public Institution(ConfirmationInstitution confirmationInstitution) {
        super.setName(confirmationInstitution.getName());
        super.setZipcode(confirmationInstitution.getZipcode());
        super.setCity(confirmationInstitution.getCity());
        super.setEmail(confirmationInstitution.getEmail());
        super.setAddress(confirmationInstitution.getAddress());
        super.setDescription(confirmationInstitution.getDescription());
        super.setPhone(confirmationInstitution.getPhone());
        super.setWebsite(confirmationInstitution.getWebsite());
        this.openingHours = confirmationInstitution.getOpeningHours();
    }

    public Institution(ExcelFileLoader parsedFile, GeoLocationData geoLocationData) {
        super.setName(parsedFile.getName());
        super.setZipcode(parsedFile.getZipcode());
        super.setCity(parsedFile.getCity());
        super.setEmail(parsedFile.getEmail());
        super.setAddress(parsedFile.getAddress());
        super.setDescription(parsedFile.getDescription());
        super.setPhone(parsedFile.getPhone());
        super.setWebsite(parsedFile.getWebsite());
        super.setLongitude(geoLocationData.getLongitude());
        super.setLatitude(geoLocationData.getLatitude());
    }
}
