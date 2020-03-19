package com.progmasters.mars.domain;

import com.progmasters.mars.dto.GeoLocation;
import com.progmasters.mars.dto.InstitutionCreationCommand;
import com.progmasters.mars.util.ExcelFileLoader;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Table(name = "institution")
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
    @Length(min = 30)
    @Length(max = 200)
    @Column(name = "description")
    private String description;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "institution")
    private List<OpeningHours> openingHours;

    @ManyToOne
    @JoinColumn(name = "provider_account_id")
    private ProviderAccount providerAccount;

    @Column(name = "website")
    private String website;

    @Column(name = "phone")
    private String phone;

    public Institution() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<OpeningHours> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHours> openingHours) {
        this.openingHours = openingHours;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public ProviderAccount getProviderAccount() {
        return providerAccount;
    }

    public void setProviderAccount(ProviderAccount providerAccount) {
        this.providerAccount = providerAccount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
