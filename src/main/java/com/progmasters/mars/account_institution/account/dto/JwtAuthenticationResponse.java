package com.progmasters.mars.account_institution.account.dto;

import lombok.Getter;

@Getter
public class JwtAuthenticationResponse {
    private String accessToken;

    public JwtAuthenticationResponse(String jwt) {
        this.accessToken = jwt;
    }
}
