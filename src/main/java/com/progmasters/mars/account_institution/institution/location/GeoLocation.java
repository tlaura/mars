package com.progmasters.mars.account_institution.institution.location;

public class GeoLocation {

    private Double longitude;
    private Double latitude;

    public GeoLocation() {
    }

    public GeoLocation(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
