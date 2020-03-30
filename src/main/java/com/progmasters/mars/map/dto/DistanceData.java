package com.progmasters.mars.map.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DistanceData {

    private String travelTimeByDriving;

    private String travelTimeByWalking;

    private String travelTimeByTransit;

    private Long distance;
}
