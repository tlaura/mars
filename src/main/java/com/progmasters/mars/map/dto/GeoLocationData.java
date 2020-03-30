package com.progmasters.mars.map.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeoLocationData {

    private Double longitude;
    private Double latitude;

    public GeoLocationData(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
