package com.progmasters.mars.domain;

import com.progmasters.mars.dto.ProviderAccountCreationCommand;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProviderAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String zipCode;
    private String city;
    private String address;
    private String type; //TODO: enum
    @OneToMany
    private List<OpeningHours> openingHours;
    private Integer ageGroupMin;
    private Integer ageGroupMax;
    @OneToMany
    private List<Institution> institutions;
    private Boolean newsletter;

    public ProviderAccount(ProviderAccountCreationCommand providerAccountCreationCommand) {
        this.username = providerAccountCreationCommand.getUsername();
        this.name = providerAccountCreationCommand.getName();
        this.zipCode = providerAccountCreationCommand.getZipcode();
        this.city = providerAccountCreationCommand.getCity();
        this.address = providerAccountCreationCommand.getAddress();
        this.email = providerAccountCreationCommand.getEmail();
        this.phone = providerAccountCreationCommand.getPhone();
        this.ageGroupMin = providerAccountCreationCommand.getAgeGroupMin();
        this.ageGroupMax = providerAccountCreationCommand.getAgeGroupMax();
        this.type = providerAccountCreationCommand.getType();
        this.newsletter = providerAccountCreationCommand.getNewsletter();
    }

    public ProviderAccount() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOpeningHours(List<OpeningHours> openingHours) {
        this.openingHours = openingHours;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public List<OpeningHours> getOpeningHours() {
        return openingHours;
    }

    public Integer getAgeGroupMin() {
        return ageGroupMin;
    }

    public Integer getAgeGroupMax() {
        return ageGroupMax;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }
}
