package com.progmasters.mars.dto;

import com.progmasters.mars.domain.ProviderAccount;

public class ProviderUserDetailsEdit {

    private String email;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String zipcode;
    private String city;
    private String address;
    private Boolean newsletter;

    public ProviderUserDetailsEdit(ProviderAccount providerAccount) {
        this.email = providerAccount.getEmail();
        this.name = providerAccount.getName();
        this.username = providerAccount.getName();
        this.password = providerAccount.getPassword();
        this.phone = providerAccount.getPhone();
        this.zipcode = providerAccount.getZipcode();
        this.city = providerAccount.getCity();
        this.address = providerAccount.getAddress();
        this.newsletter = providerAccount.getNewsletter();
    }

    public String getEmail() {
        return email;
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

    public String getPhone() {
        return phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }
}
