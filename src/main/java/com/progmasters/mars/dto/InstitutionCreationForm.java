package com.progmasters.mars.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class InstitutionCreationForm {
    private String name;

    @PositiveOrZero()
    private Integer zipCode;

    @NotBlank()
    private String city;

    @NotBlank()
    private String address;

    @Column(unique = true)
    @NotBlank()
    @Email()
    private String email;

    @Lob
    @NotBlank()
    @Length(min = 30)
    private String description;

    private String institutionType;

    private Double longitude;

    private Double latitude;

    private Long creatorId;

    public InstitutionCreationForm() {
    }

    public String getName() {
        return name;
    }


    public Integer getZipCode() {
        return zipCode;
    }


    public String getCity() {
        return city;
    }


    public String getAddress() {
        return address;
    }


    public String getEmail() {
        return email;
    }


    public String getDescription() {
        return description;
    }


    public Long getCreatorId() {
        return creatorId;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
