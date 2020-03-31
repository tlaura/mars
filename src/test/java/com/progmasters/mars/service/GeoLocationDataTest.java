package com.progmasters.mars.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.map.MapService;
import com.progmasters.mars.map.dto.GeoLocationData;
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
public class GeoLocationDataTest {

    @Autowired
    private MapService mapService;


    @Test
    public void getGeoLocationTest() throws NotFoundException {
        String address = "1089 Budapest Orczy út 43";
        GeoLocationData geoLocationData = mapService.getGeoLocation(address);

        Assertions.assertNotNull(geoLocationData);
    }
}
