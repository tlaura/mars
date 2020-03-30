package com.progmasters.mars.account_institution.institution.location;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class GeocodeService {

    @Value("${google.apikey}")
    private String key;

    private final GeoApiContext context;

    public GeocodeService(GeoApiContext context) {
        this.context = context;
    }

    public GeoLocation getGeoLocation(String address) throws NotFoundException {
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
        return new GeoLocation(longitude, latitude);
    }

    public DistanceMatrix calculateDistance(String origin, String destination) {
        DistanceMatrix matrix = null;
        try {
            matrix = DistanceMatrixApi.newRequest(context).origins(origin).destinations(destination).units(Unit.METRIC).await();
        } catch (ApiException | InterruptedException | IOException e) {
            log.info(e.getMessage());
        }
        return matrix;
    }
}
