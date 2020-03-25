package com.progmasters.mars.account_institution.account.domain;

public enum InstitutionType {

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

    InstitutionType(String englishName, String hungarianName) {
        this.englishName = englishName;
        this.hungarianName = hungarianName;
    }

    public static InstitutionType getTypeByName(String name) {
        InstitutionType foundType = null;
        for (InstitutionType value : InstitutionType.values()) {
            if (value.toString().equals(name)) {
                foundType = value;
            }
        }
        return foundType;
    }

    public static InstitutionType getTypeByHungarianName(String hungarianName) {
        InstitutionType foundType = null;
        for (InstitutionType value : InstitutionType.values()) {
            if (value.hungarianName.equalsIgnoreCase(hungarianName)) {
                foundType = value;
            }
        }
        return foundType;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getHungarianName() {
        return hungarianName;
    }
}
