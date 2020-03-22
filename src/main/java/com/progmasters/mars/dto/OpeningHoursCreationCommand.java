package com.progmasters.mars.dto;

import java.time.LocalTime;

public class OpeningHoursCreationCommand {
    private String weekDay;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public OpeningHoursCreationCommand(String weekDay, LocalTime openingTime, LocalTime closingTime) {
        this.weekDay = weekDay;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
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
