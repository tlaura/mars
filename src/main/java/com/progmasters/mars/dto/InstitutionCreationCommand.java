package com.progmasters.mars.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

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
    private List<OpeningHoursCreationCommand> openingHours;

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

    public List<OpeningHoursCreationCommand> getOpeningHours() {
        return openingHours;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
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

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOpeningHours(List<OpeningHoursCreationCommand> openingHours) {
        this.openingHours = openingHours;
    }
}
