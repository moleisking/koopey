package com.koopey.service;


import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class PositionService implements LocationListener, OnSuccessListener<Location> {

    public interface PositionListener {
        void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude);

        void onPositionRequestFail(String errorMessage);

        void onPositionRequestPermission();
    }

    public PositionService(@NonNull Context context    ) {
        this.context = context;
        positionListeners = new ArrayList<>();
    }

    private Context context;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private FusedLocationProviderClient fusedLocationClient;
    private List<PositionService.PositionListener> positionListeners;

    public void startPositionRequest() {
        Log.i(PositionService.class.getSimpleName(), "startPositionRequest()");
        executor.execute(() -> getLatitudeAndLongitudeFromDevice());
    }

    public void startPersistentPositionRequest() {
        Timer timer =  new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startPositionRequest();
            }

        }, 0, 5000);
    }

    private void getLatitudeAndLongitudeFromDevice() {
        Log.i(PositionService.class.getSimpleName(), "getLatitudeAndLongitude()");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            for (PositionService.PositionListener listener : positionListeners) {
                listener.onPositionRequestPermission();
            }
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this);
    }

    @Override
    public void onSuccess(Location location) {
        for (PositionService.PositionListener listener : positionListeners) {
            listener.onPositionRequestSuccess(location.getAltitude(), location.getLatitude(), location.getLongitude());
        }
        Log.i(PositionService.class.getName(), "onSuccess()" + location.toString());
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.i(PositionService.class.getSimpleName(), "update()");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(PositionService.class.getSimpleName(), "update()");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(PositionService.class.getSimpleName(), "onProviderDisabled()");
        for (PositionService.PositionListener listener : positionListeners) {
            listener.onPositionRequestFail(provider.toString() + " not available");
        }
    }

    public void setPositionListeners(PositionService.PositionListener gpsListener) {
        positionListeners.add(gpsListener);
    }





    public void permission() {
      /*  ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );*/
    }

}
