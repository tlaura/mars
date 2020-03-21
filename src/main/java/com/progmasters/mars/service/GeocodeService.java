package com.progmasters.mars.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
import com.progmasters.mars.dto.GeoLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GeocodeService {

    private final Logger logger = LoggerFactory.getLogger(GeocodeService.class);
    @Value("${google.apikey}")
    private String key;
    private GeoApiContext context;

    private GeoApiContext getContext() {
        // https://github.com/googlemaps/google-maps-services-java#usage
        // context is designed to be a singleton
        if (this.context == null) {
            this.context = new GeoApiContext.Builder()
                .apiKey(key)
                .build();
        }
        return this.context;
    }

    public GeoLocation getGeoLocation(String address) throws NotFoundException {
        GeoApiContext context = this.getContext();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context,
                    address).await();
        } catch (ApiException | InterruptedException | IOException e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Geocoding failed");
        }
        double latitude = results[0].geometry.location.lat;
        double longitude = results[0].geometry.location.lng;
        return new GeoLocation(longitude, latitude);
    }
}
