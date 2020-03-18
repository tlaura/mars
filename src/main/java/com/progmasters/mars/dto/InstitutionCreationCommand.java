package com.progmasters.mars.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

public class InstitutionCreationCommand {

    private Long id;

    @NotBlank
    @NotEmpty
    private String name;

    @PositiveOrZero
    private Integer zipcode;

    @NotBlank
    @NotEmpty
    private String city;

    @NotBlank
    @NotEmpty
    private String address;

    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @Lob
    @NotBlank
    @Length(min = 30)
    private String description;
    private String website;
    private String phone;

    public InstitutionCreationCommand() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getZipcode() {
        return zipcode;
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

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

}
