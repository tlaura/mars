package com.progmasters.mars.map;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.*;
import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionListData;
import com.progmasters.mars.map.dto.GeoLocationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MapService {

    private static final Integer M_TO_KM = 1000;


    private final ObjectFactory<GeoApiContext> context;

    public MapService(ObjectFactory<GeoApiContext> context) {
        this.context = context;
    }


    public GeoLocationData getGeoLocation(String address) throws NotFoundException {
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context.getObject(),
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

    @Async
    CompletableFuture<DistanceMatrix> getDistanceMatrix(Double originLng, Double originLat, List<AccountInstitutionListData> allAccounts) {

        List<String> destinations = allAccounts.stream().map(AccountInstitutionListData::toString).collect(Collectors.toList());
        String[] destinationArray = new String[destinations.size()];
        destinationArray = destinations.toArray(destinationArray);
        DistanceMatrix matrix = null;
        try {
            matrix = DistanceMatrixApi.newRequest(context.getObject()).origins(new LatLng(originLat, originLng))
                    .destinations(destinationArray)
                    .units(Unit.METRIC)
                    .mode(TravelMode.DRIVING)
                    .await();


        } catch (ApiException | InterruptedException | IOException e) {
            log.info(e.getMessage());
            //  e.printStackTrace();
        }
        return CompletableFuture.completedFuture(matrix);
    }

    @Async
    public CompletableFuture<List<AccountInstitutionListData>> findAccountsWithinRange(Double originLng, Double originLat, List<AccountInstitutionListData> allAccounts, Long maxDistance) {
        CompletableFuture<DistanceMatrix> futureMatrix = getDistanceMatrix(originLng, originLat, allAccounts);
        return futureMatrix.thenApplyAsync(matrix -> {
            List<AccountInstitutionListData> results = new ArrayList<>();
            for (int j = 0; j < matrix.rows[0].elements.length; j++) {
                DistanceMatrixElement element = matrix.rows[0].elements[j];
                Distance rawDistance = element.distance;
                if (rawDistance != null) {
                    if ((rawDistance.inMeters / M_TO_KM) <= maxDistance) {
                        results.add(allAccounts.get(j));
                    }
                }
            }
            return results;
        });

    }
}
