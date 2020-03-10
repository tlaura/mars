package com.progmasters.mars.dto;

import java.time.LocalDateTime;

public class OpeningHour {
    private String weekDay; //TODO: enum
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
}
