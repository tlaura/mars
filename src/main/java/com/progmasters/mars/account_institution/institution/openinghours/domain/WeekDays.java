package com.progmasters.mars.account_institution.institution.openinghours.domain;

import lombok.Getter;

@Getter
public enum WeekDays {
    MONDAY("Monday", "Hétfő"),
    TUESDAY("Tuesday", "Kedd"),
    WEDNESDAY("Wednesday", "Szerda"),
    THURSDAY("Thursday", "Csütörtök"),
    FRIDAY("Friday", "Péntek"),
    SATURDAY("Saturday", "Szombat"),
    SUNDAY("Sunday", "Vasárnap");

    private String englishName;
    private String hungarianName;

    WeekDays(String englishName, String hungarianName) {
        this.englishName = englishName;
        this.hungarianName = hungarianName;
    }

    public static WeekDays getWeekDayByHungarianName(String hungarianName) {
        WeekDays foundType = null;
        for (WeekDays value : WeekDays.values()) {
            if (value.hungarianName.equalsIgnoreCase(hungarianName)) {
                foundType = value;
            }
        }
        return foundType;
    }
}
