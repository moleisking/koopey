package com.koopey.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.koopey.model.base.Base;


import org.json.JSONObject;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Location extends Base  {

    public static final String LOCATION_FILE_NAME = "location.dat";

    @Builder.Default
    private Double altitude = 0.0d;
    @Builder.Default
    private Double latitude = 0.0d;
    @Builder.Default
    private Double longitude = 0.0d;
    @Builder.Default
    private Double distance = 0.0d;

    public String address;
    public String place ;

    public static String convertLatLngToPosition(Double longitude, Double latitude) {
        if (longitude != 0.0d && latitude != 0.0d) {
            return "{ 'type': 'Point', 'coordinates': [" + String.valueOf(longitude) + "," + String.valueOf(latitude) + "]}"; //longitude, latitude
        } else {
            return "{}";
        }
    }

    public boolean isEmpty() {
        if ((this.address != null && this.address.length() > 5) || (this.latitude != 0.0d) || (this.longitude != 0.0d)) {
            return false;
        } else {
            return true;
        }
    }

    /*public static String convertDistanceToMiles(Double distance) {
        //5280ft in a mile
        if (distance <= 5280) {
            return distance + " ft";
        } else {
            return String.valueOf(Math.round(distance / 5280)) + " mi";
        }
    }

    public static String convertDistanceToKilometers(Double distance) {
        if (distance <= 1000) {
            return String.valueOf(Math.round(distance)) + " m";
        } else {
            return String.valueOf(Math.round(distance / 1000)) + " km";
        }
    }*/

    public LatLng getLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }

}
