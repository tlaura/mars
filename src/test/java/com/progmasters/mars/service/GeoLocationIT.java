package com.progmasters.mars.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.dto.GeoLocation;
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
    public void getGeoLocationTest() throws NotFoundException {
        String address = "1089 Budapest Orczy út 43";
        GeoLocation geoLocation = geocodeService.getGeoLocation(address);

        Assertions.assertNotNull(geoLocation);
    }

    // mock GeocodingApi.geocode

    // test 1: mock returns { geometry: {location: { lat: 1, lnng: 1 }}}
    // then function should return Geocoding(1,1)

    // test 2: mock should throw  an exception
    // assert what should happens in the catch block does happen
}
