package com.progmasters.mars.map.dto;

import com.google.maps.model.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class DistanceData {

    private Duration travelTimeByDriving;

    private Long distanceByDriving;

    private Duration travelTimeByWalking;

    private Long distanceByWalking;

    private Duration travelTimeByTransit;

    private Long distanceByTransit;

}
