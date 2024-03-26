package com.example.demo.services;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleMapIntegration {

    public int findDistance(Double originLat, Double originLng, Double destLat, Double destLng) throws Exception {
       /* originLat = 37.7749; // Latitude of origin coordinate
         originLng = -122.4194; // Longitude of origin coordinate
         destLat = 34.0522; // Latitude of destination coordinate
         destLng = -118.2437; // Longitude of destination coordinate*/

        Long distanceInMeters = 0L;

            GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyBxyT2Xl4AAfET-HxXxZZWmkOFm6yxlQ8U").build();

            LatLng origin = new LatLng(originLat, originLng);
            LatLng destination = new LatLng(destLat, destLng);

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
