package com.progmasters.mars.dto;

import com.progmasters.mars.domain.ProviderAccount;

public class ProviderUserDetails {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String zipcode;
    private String city;
    private String address;

    public ProviderUserDetails(ProviderAccount providerAccount) {
        this.id = providerAccount.getId();
        this.name = providerAccount.getName();
        this.username = providerAccount.getUsername();
        this.password = providerAccount.getPassword();
        this.email = providerAccount.getEmail();
        this.phone = providerAccount.getPhone();
        this.zipcode = providerAccount.getZipCode();
        this.city = providerAccount.getCity();
        this.address = providerAccount.getAddress();
    }

    public String getPassword() {
        return password;
    }

    public ProviderUserDetails() {
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

    public String getEmail() {
        return email;
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
}
