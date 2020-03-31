package com.progmasters.mars.map;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.*;
import com.progmasters.mars.map.dto.GeoLocationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MapService {

    private final GeoApiContext context;

    public MapService(GeoApiContext context) {
        this.context = context;
    }

    public GeoLocationData getGeoLocation(String address) throws NotFoundException {
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context,
                    address).await();
        } catch (ApiException | InterruptedException | IOException e) {
            log.error(e.getMessage());
            throw new NotFoundException("Geocoding failed");
        }
        if (results.length == 0) {
            throw new NotFoundException("Location not found.");
        }
        double latitude = results[0].geometry.location.lat;
        double longitude = results[0].geometry.location.lng;
        return new GeoLocationData(longitude, latitude);
    }

    public DistanceMatrix calculateDistanceByGivenTravelMode(Double originLng, Double originLat, String destination, TravelMode travelMode) {
        DistanceMatrix matrix = null;
        try {
            matrix = DistanceMatrixApi.newRequest(context).origins(new LatLng(originLat, originLng))
                    .destinations(destination)
                    .units(Unit.METRIC)
                    .mode(travelMode)
                    .await();
        } catch (ApiException | InterruptedException | IOException e) {
            log.info(e.getMessage());
        }
        return matrix;
    }
}
