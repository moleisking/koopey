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
    public Double latitude = 0.0d;
    @Builder.Default
    public Double longitude = 0.0d;
    public String address;
    public String position ;
    //public JSONObject position = new JSONObject();
    @Builder.Default
    public long startTimeStamp = 0;
    @Builder.Default
    public long endTimeStamp = 0;
    @Builder.Default
    public long createTimeStamp = System.currentTimeMillis();
    @Builder.Default
    public long readTimeStamp = 0;
    @Builder.Default
    public long updateTimeStamp = 0;
    @Builder.Default
    public long deleteTimeStamp = 0;


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

  /*  @Override
    public int compare(Location o1, Location o2) {
        if (o1.hashCode() < o2.hashCode()) {
            return -1;
        } else if (o1.hashCode() > o2.hashCode()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(Location o) {
        return compare(this, o);
    }

    @Override
    public String toString() {
        return this.toJSONObject().toString();
    }*/


}
