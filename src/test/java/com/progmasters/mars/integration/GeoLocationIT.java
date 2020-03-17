package com.progmasters.mars.integration;

import com.progmasters.mars.dto.GeoLocation;
import com.progmasters.mars.service.GeocodeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class GeoLocationIT {

    @Autowired
    private GeocodeService geocodeService;

    @Test
    public void getGeoLocationTest() {
        String address = "1089 Budapest Orczy út 43";

        GeoLocation geoLocation = geocodeService.getGeoLocation(address);

        Assertions.assertNotNull(geoLocation);
    }
}
