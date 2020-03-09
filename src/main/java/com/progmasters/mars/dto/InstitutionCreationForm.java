package com.progmasters.mars.dto;

import com.progmasters.mars.domain.Institution;
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

    private Long creatorId;

    public InstitutionCreationForm() {
    }

    public InstitutionCreationForm(Institution institution) {
        this.name = institution.getName();
        this.zipCode = institution.getZipCode();
        this.city = institution.getCity();
        this.address = institution.getAddress();
        this.email = institution.getEmail();
        this.description = institution.getDescription();
        this.creatorId = institution.getCreator().getId();
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

}
