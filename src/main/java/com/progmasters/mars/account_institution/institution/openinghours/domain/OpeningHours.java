package com.progmasters.mars.account_institution.institution.openinghours.domain;

import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.openinghours.dto.OpeningHoursCreationCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Table(name = "opening_hours")
@Getter
@Setter
@NoArgsConstructor
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

    public OpeningHours(OpeningHoursCreationCommand openingHoursCreationCommand) {
        this.weekDay = WeekDays.getWeekDayByHungarianName(openingHoursCreationCommand.getWeekDay());
        this.openingTime = openingHoursCreationCommand.getOpeningTime();
        this.closingTime = openingHoursCreationCommand.getClosingTime();
    }
}
