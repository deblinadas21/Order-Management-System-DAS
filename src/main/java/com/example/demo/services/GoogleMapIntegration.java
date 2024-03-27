package com.example.demo.services;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleMapIntegration {

    @Value("${google.maps.api.key}")
    private String API_KEY;
    public int findDistance(String originLat, String originLng, String destLat, String destLng) throws Exception {
        Long distanceInMeters = 0L;

            GeoApiContext context = new GeoApiContext.Builder().apiKey(API_KEY).build();

            LatLng origin = new LatLng(Double.valueOf(originLat), Double.valueOf(originLng));
            LatLng destination = new LatLng(Double.valueOf(destLat), Double.valueOf(destLng));

            DistanceMatrix distanceMatrix = DistanceMatrixApi.newRequest(context)
                    .origins(origin)
                    .destinations(destination)
                    .await();

            if (distanceMatrix.rows.length > 0 && distanceMatrix.rows[0].elements.length > 0) {
                DistanceMatrixRow row = distanceMatrix.rows[0];
                if (row.elements[0].status == DistanceMatrixElementStatus.OK) {
                    distanceInMeters = row.elements[0].distance.inMeters;
                    System.out.println("Distance between coordinates: " + distanceInMeters + " meters");
                } else {
                    System.out.println("Failed to calculate distance");
                    throw new Exception("Failed to calculate distance");
                }
            } else {
                System.out.println("Failed to calculate distance");
                throw new Exception("Failed to calculate distance");
            }


        return distanceInMeters.intValue();
    }
}
