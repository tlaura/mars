package com.progmasters.mars.account_institution.institution.openinghours.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class OpeningHoursCreationCommand {
    private String weekDay;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public OpeningHoursCreationCommand(String weekDay, LocalTime openingTime, LocalTime closingTime) {
        this.weekDay = weekDay;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }
}
