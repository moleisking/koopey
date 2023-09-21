package com.koopey.helper;

import com.koopey.model.Location;

/**
 * Created by Scott on 21/02/2018.
 */

public class DistanceHelper {
    public static String DistanceToMiles(Double distance) {
        //5280ft in a mile
        if (distance <= 5280) {
            return distance + " ft";
        } else {
            return String.valueOf(Math.round(distance / 5280)) + " mi";
        }
    }

    public static String DistanceToKilometers(int distance) {
        return DistanceToKilometers((double)distance);
    }

    public static String DistanceToKilometers(Double distance) {
        if (distance <= 1000) {
            return String.valueOf(Math.round(distance)) + " m";
        } else {
            return String.valueOf(Math.round(distance / 1000)) + " km";
        }
    }

    public static String LatLngToPosition(Location location) {
        return LatLngToPosition(location.getLongitude(), location.getLatitude());
    }
    public static String LatLngToPosition(Double longitude, Double latitude) {
        if (longitude != 0.0d && latitude != 0.0d) {
            return "{ 'type': 'Point', 'coordinates': [" + String.valueOf(longitude) + "," + String.valueOf(latitude) + "]}"; //longitude, latitude
        } else {
            return "{}";
        }
    }
}
