package com.progmasters.mars.account_institution.institution.domain;

import com.progmasters.mars.account_institution.connector.AccountInstitutionConnector;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.location.GeoLocation;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import com.progmasters.mars.util.ExcelFileLoader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "institution")
@Getter
@Setter
@NoArgsConstructor
public class Institution extends TempInstitution {


    @OneToMany(mappedBy = "institution")
    private List<OpeningHours> openingHours;

    @OneToMany(mappedBy = "institution")
    private List<AccountInstitutionConnector> accountInstitutionConnectors;

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

    public Institution(InstitutionCreationCommand institutionCreationCommand, GeoLocation geoLocation) {
        this(institutionCreationCommand);
        super.setLongitude(geoLocation.getLongitude());
        super.setLatitude(geoLocation.getLatitude());

    }

    public Institution(ExcelFileLoader parsedFile, GeoLocation geoLocation) {
        super.setName(parsedFile.getName());
        super.setZipcode(parsedFile.getZipcode());
        super.setCity(parsedFile.getCity());
        super.setEmail(parsedFile.getEmail());
        super.setAddress(parsedFile.getAddress());
        super.setDescription(parsedFile.getDescription());
        super.setPhone(parsedFile.getPhone());
        super.setWebsite(parsedFile.getWebsite());
        super.setLongitude(geoLocation.getLongitude());
        super.setLatitude(geoLocation.getLatitude());
    }
}
