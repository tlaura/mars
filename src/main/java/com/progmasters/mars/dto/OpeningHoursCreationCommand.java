package com.progmasters.mars.dto;

public class OpeningHoursCreationCommand {
    private String weekDay; //TODO: enum
    private String openingTime;
    private String closingTime;

    public OpeningHoursCreationCommand() {
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }
}
