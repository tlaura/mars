package com.progmasters.mars.account_institution.institution.dto;

import com.progmasters.mars.account_institution.account.domain.ProviderType;

public class InstitutionTypeData {

    private String name;
    private String displayName;

    public InstitutionTypeData() {
    }

    public InstitutionTypeData(ProviderType providerType) {
        this.name = providerType.toString();
        this.displayName = providerType.getHungarianName();
    }

    //---------Getters------------


    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}
