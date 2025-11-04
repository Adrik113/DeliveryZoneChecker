package com.example.deliveryzone.service;
import com.example.deliveryzone.model.DeliveryZone;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;

@Service

public class DeliveryService {

    private List<DeliveryZone> deliveryZones;

    @PostConstruct
    public void loadZones() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/zones.json");
        deliveryZones = mapper.readValue(inputStream, new TypeReference<List<DeliveryZone>>() {});
    }

    public boolean isWithinDeliveryZone(double latitude, double longitude) {
        for (DeliveryZone zone : deliveryZones) {
            double distance = distance(latitude, longitude, zone.getLatitude(), zone.getLongitude());
            // You may want to check if distance is within a certain threshold here
            if (distance <= zone.getRadius()) {
                return true;
            }
        }
        return false;
    }

    // Haversine formula to calculate distance between two lat/lon points in kilometers
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
    
}
