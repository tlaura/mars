package com.progmasters.auti.dto;

import com.progmasters.auti.domain.Institution;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AddInstitutionForm {
    private String name;

    @NotBlank(message = "{zipcode.empty}")
    private String zipCode;

    @NotBlank(message = "{city.empty}")
    private String city;

    @NotBlank(message = "{address.empty}")
    private String address;

    @Column(unique = true)
    @NotBlank(message = "{email.empty}")
    @Email(message = "{email.format}")
    private String email;

    @Lob
    @NotBlank(message = "{description.empty}")
    @Length(min = 30, message = "{description.short}")
    private String description;

    private Long creatorId;

    public AddInstitutionForm() {
    }

    public AddInstitutionForm(Institution institution) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Institution toInstitution() {
        Institution institution = new Institution();
        institution.setName(name);
        institution.setEmail(email);
        institution.setZipCode(zipCode);
        institution.setCity(city);
        institution.setAddress(address);
        institution.setDescription(description);
        return institution;
    }
}
