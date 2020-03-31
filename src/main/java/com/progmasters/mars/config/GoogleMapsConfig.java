package com.progmasters.mars.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsConfig {

    @Value("${google.apikey}")
    private String key;

    @Bean
    public GeoApiContext getContext() {
        return new GeoApiContext.Builder()
                .apiKey(key)
                .build();
    }
}
