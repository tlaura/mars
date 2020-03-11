package com.progmasters.mars.domain;

import com.progmasters.mars.dto.GeoLocation;
import com.progmasters.mars.dto.InstitutionCreationForm;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "name")
    @NotBlank
    private String name;

    @PositiveOrZero
    @Column(name = "zip_code")
    private Integer zipCode;

    @NotBlank
    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "address")
    private String address;

    @Column(unique = true, name = "email")
    @NotBlank
    @Email
    private String email;

    @Lob
    @NotBlank
    @Length(min = 30)
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @ManyToOne
    private InstitutionalUser creator;

    public Institution() {
    }

    public Institution(InstitutionCreationForm institutionCreationForm, GeoLocation geoLocation) {
        this.name = institutionCreationForm.getName();
        this.zipCode = institutionCreationForm.getZipCode();
        this.city = institutionCreationForm.getCity();
        this.address = institutionCreationForm.getAddress();
        this.email = institutionCreationForm.getEmail();
        this.description = institutionCreationForm.getDescription();
        String institutionType = institutionCreationForm.getInstitutionType();
        this.institutionType = InstitutionType.getTypeByName(institutionType);
        this.longitude = geoLocation.getLongitude();
        this.latitude = geoLocation.getLatitude();

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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
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

    public InstitutionalUser getCreator() {
        return creator;
    }

    public void setCreator(InstitutionalUser creator) {
        this.creator = creator;
    }

    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
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
}
