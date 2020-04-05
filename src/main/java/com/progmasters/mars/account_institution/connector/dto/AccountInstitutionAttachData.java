package com.progmasters.mars.account_institution.connector.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountInstitutionAttachData {

    private String providerEmail;

    private Long institutionId;
}
