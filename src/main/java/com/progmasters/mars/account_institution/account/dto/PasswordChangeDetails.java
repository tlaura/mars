package com.progmasters.mars.account_institution.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class PasswordChangeDetails {
    @NotBlank
    @NotEmpty
    private String email;
    @NotBlank
    @NotEmpty
    private String oldPassword;
    @NotBlank
    @NotEmpty
    private String password;
    @NotBlank
    @NotEmpty
    private String confirmPassword;

    public PasswordChangeDetails(@NotBlank @NotEmpty String email, @NotBlank @NotEmpty String oldPassword, @NotBlank @NotEmpty String password, @NotBlank @NotEmpty String confirmPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
