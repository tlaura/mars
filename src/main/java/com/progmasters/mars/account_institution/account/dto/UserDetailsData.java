package com.progmasters.mars.account_institution.account.dto;

import com.progmasters.mars.account_institution.account.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class UserDetailsData {

    private String name;

    private String password;

    private String email;

    private String phone;

    private String role;

    private Integer zipcode;

    private String city;

    private String address;

    private Boolean newsletter;

    public UserDetailsData(User user) {
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole().toString();
        this.zipcode = user.getZipcode();
        this.city = user.getCity();
        this.address = user.getAddress();
        this.newsletter = user.getNewsletter();
    }
}
