package com.koopey.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.koopey.model.Location;


import java.util.ArrayList;
import java.util.List;

public class GpsReceiver extends BroadcastReceiver {

    public interface GPSListener {
        void onLocation(Location location);
    }


    private List<GpsReceiver.GPSListener> gpsListeners = new ArrayList<>();

    public void start(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Location location = Location.builder()
                .latitude(intent.getDoubleExtra("latitude", 0.0d ))
                .longitude(intent.getDoubleExtra("longitude", 0.0d))
                .altitude(intent.getDoubleExtra("altitude", 0.0d)).build();
        Log.i(GpsReceiver.class.getName(), location.toString());
        for (GpsReceiver.GPSListener listener : gpsListeners) {
            listener.onLocation(location);
        }

    }

    public void setGpsReceiverListeners(GpsReceiver.GPSListener gpsReceiverListener){
        gpsListeners.add(gpsReceiverListener);
    }





}
