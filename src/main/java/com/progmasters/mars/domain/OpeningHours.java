package com.progmasters.mars.domain;

import com.progmasters.mars.dto.OpeningHoursCreationCommand;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
public class OpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private WeekDays weekDay;
    @NotNull
    private LocalTime openingTime;
    @NotNull
    private LocalTime closingTime;

    public OpeningHours() {
    }

    public OpeningHours(OpeningHoursCreationCommand openingHoursCreationCommand) {
        this.weekDay = WeekDays.getWeekDayByHungarianName(openingHoursCreationCommand.getWeekDay());
        this.openingTime = openingHoursCreationCommand.getOpeningTime();
        this.closingTime = openingHoursCreationCommand.getClosingTime();
    }

    public Long getId() {
        return id;
    }

    public WeekDays getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDays weekDay) {
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
