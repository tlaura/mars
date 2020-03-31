package com.progmasters.mars.account_institution.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationCommand {

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    private String password;

    @NotBlank
    @NotEmpty
    private String email;

    private String phone;
    private Integer zipcode;
    private String city;
    private String address;

    private Boolean newsletter;

    public void setPassword(String password) {
        this.password = password;
    }
}
