package com.progmasters.mars.account_institution.institution.openinghours.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class OpeningHoursData {

    private String weekDay;

    @JsonFormat(pattern = "hh:mm")
    private LocalTime openingTime;

    @JsonFormat(pattern = "hh:mm")
    private LocalTime closingTime;

    public OpeningHoursData(OpeningHours openingHours) {
        this.weekDay = openingHours.getWeekDay().getHungarianName();
        this.openingTime = openingHours.getOpeningTime();
        this.closingTime = openingHours.getClosingTime();
    }
}
