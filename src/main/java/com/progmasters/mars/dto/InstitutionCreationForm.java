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

    public void setName(String name) {
        this.name = name;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
