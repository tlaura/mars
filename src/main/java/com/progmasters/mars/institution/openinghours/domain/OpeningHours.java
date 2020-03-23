package com.progmasters.mars.institution.openinghours.domain;

import com.progmasters.mars.institution.domain.Institution;
import com.progmasters.mars.institution.openinghours.dto.OpeningHoursCreationCommand;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Table(name = "opening_hours")
public class OpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "week_day")
    private WeekDays weekDay;

    @NotNull
    @Column(name = "opening_time")
    private LocalTime openingTime;

    @NotNull
    @Column(name = "closing_time")
    private LocalTime closingTime;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
}
