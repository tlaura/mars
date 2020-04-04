package com.progmasters.mars.map.dto;

import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistanceData {

    private TravelMode travelMode;

    private Long distance;

    private Duration travelTime;
}
