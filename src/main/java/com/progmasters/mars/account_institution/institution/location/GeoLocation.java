package com.progmasters.mars.account_institution.institution.location;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeoLocation {

    private Double longitude;
    private Double latitude;

    public GeoLocation(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
