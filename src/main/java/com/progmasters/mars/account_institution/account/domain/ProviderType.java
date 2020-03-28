package com.progmasters.mars.account_institution.account.domain;

import lombok.Getter;

@Getter
public enum ProviderType {

    //todo refactor read values from file
    DIAGNOSTIC_CENTER("Diagnostic Center", "Diagnózis központ"),
    THERAPY("Therapy", "Terápia"),
    DEVELOPMENT_INSTITUTION("Development institution", "Fejlesztő hely"),
    KINDERGARTEN("Kindergarten", "Óvoda"),
    ELEMENTARY_SCHOOL("Elementary school", "Általános iskola"),
    HIGH_SCHOOL("High school", "Középiskola"),
    DORM("Dorm", "Kollégium"),
    WORKPLACE("Workplace", "Munkahely"),
    RESIDENTIAL_ADULT_PROVIDER("Residental adult provider", "Bentlakásos felnőtt ellátó"),
    DAY_CARE("Day car", "Nappali foglalkoztató"),
    ETC("Etc", "Egyéb");

    private String englishName;
    private String hungarianName;

    ProviderType(String englishName, String hungarianName) {
        this.englishName = englishName;
        this.hungarianName = hungarianName;
    }

    public static ProviderType getTypeByHungarianName(String hungarianName) {
        ProviderType foundType = null;
        for (ProviderType value : ProviderType.values()) {
            if (value.hungarianName.equalsIgnoreCase(hungarianName)) {
                foundType = value;
            }
        }
        return foundType;
    }
}
