package com.progmasters.mars.account_institution.institution.openinghours.dto;

import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;

import java.time.LocalTime;

public class OpeningHoursData {

    private String weekDay;

    private LocalTime openingTime;

    private LocalTime closingTime;

    public OpeningHoursData() {
    }

    public OpeningHoursData(OpeningHours openingHours) {
        this.weekDay = openingHours.getWeekDay().getHungarianName();
        this.openingTime = openingHours.getOpeningTime();
        this.closingTime = openingHours.getClosingTime();
    }

    public String getWeekDay() {
        return weekDay;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }
}
