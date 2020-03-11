package com.progmasters.mars.domain;

import com.progmasters.mars.dto.OpeningHoursCreationCommand;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String weekDay; //TODO: enum
    private String openingTime;
    private String closingTime;

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

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
}
