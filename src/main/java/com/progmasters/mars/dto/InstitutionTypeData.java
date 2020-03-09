package com.progmasters.mars.dto;

import com.progmasters.mars.domain.InstitutionType;

public class InstitutionTypeData {

    private String name;
    private String displayName;

    public InstitutionTypeData() {
    }

    public InstitutionTypeData(InstitutionType institutionType) {
        this.name = institutionType.toString();
        this.displayName = institutionType.getHungarianName();
    }

    //---------Getters------------


    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}
