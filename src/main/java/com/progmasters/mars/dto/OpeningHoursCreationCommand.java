package com.progmasters.mars.dto;

import java.time.LocalDateTime;

public class OpeningHoursCreationCommand {
    private String weekDay; //TODO: enum
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;

    public String getWeekDay() {
        return weekDay;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }
}
