package com.progmasters.mars.dto;

import java.time.LocalTime;

public class OpeningHoursCreationCommand {
    private String weekDay;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public OpeningHoursCreationCommand() {
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
