package com.progmasters.mars.account_institution.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String token;

    public JwtAuthenticationResponse(String jwt) {
        this.token = jwt;
    }
}
