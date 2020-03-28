package com.progmasters.mars.account_institution.institution.dto;

import com.progmasters.mars.account_institution.account.domain.ProviderType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InstitutionTypeData {

    private String name;
    private String displayName;

    public InstitutionTypeData(ProviderType providerType) {
        this.name = providerType.toString();
        this.displayName = providerType.getHungarianName();
    }
}
