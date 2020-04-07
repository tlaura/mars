package com.progmasters.mars.config;

import com.google.maps.GeoApiContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class GoogleMapsConfig {

    @Value("${google.apikey}")
    private String key;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GeoApiContext getContext() {
        return new GeoApiContext.Builder()
                .apiKey(key)
                .build();
    }
}
