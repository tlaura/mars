package com.progmasters.mars.domain;

import com.progmasters.mars.dto.OpeningHoursCreationCommand;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
public class OpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String weekDay; //TODO: enum
    @NotNull
    private LocalTime openingTime;
    @NotNull
    private LocalTime closingTime;

    public OpeningHours() {
    }

    public OpeningHours(OpeningHoursCreationCommand openingHoursCreationCommand) {
        this.weekDay = openingHoursCreationCommand.getWeekDay();
        this.openingTime = openingHoursCreationCommand.getOpeningTime();
        this.closingTime = openingHoursCreationCommand.getClosingTime();
    }

    public Long getId() {
        return id;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
}
