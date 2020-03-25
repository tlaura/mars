package com.progmasters.mars.account_institution.account.dto;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;

public class ProviderUserDetailsEdit {

    private String email;
    private String name;
    private String providerServiceName;
    private String password;
    private String phone;
    private Integer zipcode;
    private String city;
    private String address;
    private Boolean newsletter;

    public ProviderUserDetailsEdit(ProviderAccount providerAccount) {
        this.email = providerAccount.getEmail();
        this.name = providerAccount.getName();
        this.providerServiceName = providerAccount.getProviderServiceName();
        this.password = providerAccount.getPassword();
        this.phone = providerAccount.getPhone();
        this.zipcode = providerAccount.getZipcode();
        this.city = providerAccount.getCity();
        this.address = providerAccount.getAddress();
        this.newsletter = providerAccount.getNewsletter();
    }

    public ProviderUserDetailsEdit() {
    }

    public ProviderUserDetailsEdit(String email, String name, String providerServiceName, String password, String phone, Integer zipcode, String city, String address, Boolean newsletter) {
        this.email = email;
        this.name = name;
        this.providerServiceName = providerServiceName;
        //   this.password = password;
        this.phone = phone;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.newsletter = newsletter;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
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

    public Boolean getNewsletter() {
        return newsletter;
    }
}
