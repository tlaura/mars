package com.progmasters.mars.account_institution.institution.domain;

import com.progmasters.mars.account_institution.connector.AccountInstitutionConnector;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.location.GeoLocation;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import com.progmasters.mars.util.ExcelFileLoader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Table(name = "institution")
@Getter
@Setter
@NoArgsConstructor
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotEmpty
    @Column(unique = true, name = "name")
    private String name;

    @PositiveOrZero
    @Column(name = "zip_code")
    private Integer zipcode;

    @NotBlank
    @NotEmpty
    @Column(name = "city")
    private String city;

    @NotBlank
    @NotEmpty
    @Column(name = "address")
    private String address;

    @NotBlank
    @NotEmpty
    @Email
    @Column(unique = true, name = "email")
    private String email;

    @Lob
    @NotBlank
    @Length(min = 30, max = 200)
    @Column(name = "description")
    private String description;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "institution")
    private List<OpeningHours> openingHours;

    @Column(name = "website")
    private String website;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "institution")
    private List<AccountInstitutionConnector> accountInstitutionConnectors;

    public Institution(InstitutionCreationCommand institutionCreationCommand) {
        this.name = institutionCreationCommand.getName();
        this.zipcode = institutionCreationCommand.getZipcode();
        this.city = institutionCreationCommand.getCity();
        this.email = institutionCreationCommand.getEmail();
        this.address = institutionCreationCommand.getAddress();
        this.description = institutionCreationCommand.getDescription();
        this.phone = institutionCreationCommand.getPhone();
        this.website = institutionCreationCommand.getWebsite();
    }

    public Institution(InstitutionCreationCommand institutionCreationCommand, GeoLocation geoLocation) {
        this(institutionCreationCommand);
        this.longitude = geoLocation.getLongitude();
        this.latitude = geoLocation.getLatitude();

    }

    public Institution(ExcelFileLoader parsedFile, GeoLocation geoLocation) {
        this.name = parsedFile.getName();
        this.zipcode = parsedFile.getZipcode();
        this.city = parsedFile.getCity();
        this.address = parsedFile.getAddress();
        this.email = parsedFile.getEmail();
        this.phone = parsedFile.getPhone();
        this.website = parsedFile.getWebsite();
        this.description = parsedFile.getDescription();
        this.latitude = geoLocation.getLatitude();
        this.longitude = geoLocation.getLongitude();
    }
}
