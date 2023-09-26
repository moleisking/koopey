package com.koopey.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;

import com.koopey.R;
import com.koopey.helper.MapHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Location;
import com.koopey.model.Locations;
import com.koopey.model.Search;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.ILocationService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationService /*extends IntentService implements GPSReceiver.OnGPSReceiverListener */{

    public interface LocationCrudListener {
        void onLocationCreate(int code, String message, Location location);

        void onLocationDelete(int code, String message, Location location);

        void onLocationUpdate(int code, String message, Location location);

        void onLocationRead(int code, String message, Location location);
    }

    public interface LocationSearchListener {
        void onLocationSearchByBuyerAndDestination(Locations locations);

        void onLocationSearchByBuyerAndSource(Locations locations);

        void onLocationSearchByDestinationAndSeller(Locations locations);

        void onLocationSearchBySellerAndSource(Locations locations);

        void onLocationSearch(Locations locations);

        void onLocationSearchByGeocode(Location location);

        void onLocationSearchByPlace(Location location);

        void onLocationSearchByRangeInKilometers(Locations locations);

        void onLocationSearchByRangeInMiles(Locations locations);

    }

    AuthenticationUser authenticationUser;
    AuthenticationService authenticationService;
    private Context context;

    private List<LocationService.LocationCrudListener> locationCrudListeners = new ArrayList<>();
    private List<LocationService.LocationSearchListener> locationSearchListeners = new ArrayList<>();


    private static final int LOCATION_NOTIFICATION = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
   // private GPSReceiver gps;
    // public ResponseMSG messageDelegate = null;

    public LocationService(Context context) {
       // super(LocationService.class.getSimpleName());
        this.context = context;
        authenticationService = new AuthenticationService(context);
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Log.d(LocationService.class.getName(), "start");
        Intent intent = new Intent(context, MessageService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Log.d(LocationService.class.getName(), "delete");
        Intent intent = new Intent(context, MessageService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    public Locations getLocalLocationsFromFile() {
        Locations locations = new Locations();
        if (SerializeHelper.hasFile(context, Locations.LOCATIONS_FILE_NAME)) {
            locations = (Locations) SerializeHelper.loadObject(context, Locations.LOCATIONS_FILE_NAME);
        }
        return locations;
    }

    public void readLocation(String locationId) {

        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .readLocation(locationId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Location> call, Response<Location> response) {
                        Location location = response.body();
                        if (location == null || location.isEmpty()) {
                            for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                                listener.onLocationRead(HttpURLConnection.HTTP_NO_CONTENT, "", location);
                            }
                        } else {
                            for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                                listener.onLocationRead(HttpURLConnection.HTTP_OK, "", location);
                            }
                            SerializeHelper.saveObject(context, location);
                            Log.i(LocationService.class.getName(), location.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Location> call, Throwable throwable) {
                        for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                            listener.onLocationRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchLocationByBuyerAndDestination() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchLocationByBuyerAndDestination().enqueue(new Callback<Locations>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            Log.i(LocationService.class.getName(), "location is null");
                        } else {
                            for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                                listener.onLocationSearchByBuyerAndDestination(locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), locations.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                            listener.onLocationSearchByBuyerAndDestination(null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchLocationByBuyerAndSource() {
        ILocationService service
                = HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url),authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Locations> callAsync = service.searchLocationByBuyerAndDestination();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onLocationSearchByBuyerAndSource(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), locations.toString());
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onLocationSearchByBuyerAndSource(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void createLocation(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .createLocation(location).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String locationId = response.body();
                        location.setId(locationId);
                        if (locationId == null || locationId.isEmpty()) {
                            for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                                listener.onLocationCreate(HttpURLConnection.HTTP_NO_CONTENT, "", null);
                            }
                        } else {
                            for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                                listener.onLocationCreate(HttpURLConnection.HTTP_CREATED, "", location);
                            }
                            SerializeHelper.saveObject(context, location);
                            Log.i(LocationService.class.getName(), location.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                            listener.onLocationCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void deleteLocation(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .deleteLocation(location).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                            listener.onLocationDelete(HttpURLConnection.HTTP_OK, "", location);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                            listener.onLocationDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchLocation(Search search) {
       HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
               .searchLocation(search).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onLocationSearch(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), locations.toString());
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onLocationSearch(null);
                }
                Log.e(LocationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchLocationByGeocode(Location location) {
       HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
               .searchLocationByGeocode(location).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location = response.body();
                if (location == null || location.isEmpty()) {
                    Log.i(LocationService.class.getName(), "locations is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onLocationSearchByGeocode(location);
                    }
                    SerializeHelper.saveObject(context, location);
                    Log.i(LocationService.class.getName(), location.toString());
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onLocationSearchByGeocode(null);
                }
                Log.e(LocationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchLocationByDestinationAndSeller() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchLocationByDestinationAndSeller().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "locations is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onLocationSearchByDestinationAndSeller(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onLocationSearchByDestinationAndSeller(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchLocationBySellerAndSource() {
         HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                 .searchLocationBySellerAndSource().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onLocationSearchBySellerAndSource(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onLocationSearchBySellerAndSource(null);
                }
                Log.e(LocationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchLocationByPlace(Location location) {
       HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
               .searchLocationByPlace(location).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location = response.body();
                if (location == null || location.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onLocationSearchByPlace(location);
                    }
                    SerializeHelper.saveObject(context, location);
                    Log.i(LocationService.class.getName(), location.toString());
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onLocationSearchByPlace(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchLocationByRangeInKilometers(Search search) {

        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url),authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchLocationByRangeInKilometers(search).enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onLocationSearchByRangeInKilometers(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onLocationSearchByRangeInKilometers(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchLocationByRangeInMiles(Search search) {
      HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
              .searchLocationByRangeInMiles(search).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "locations is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onLocationSearchByRangeInMiles(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                }
            }
            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onLocationSearchByRangeInMiles(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void updateLocation(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateLocation(location).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                            listener.onLocationUpdate(HttpURLConnection.HTTP_OK, "", location);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                            listener.onLocationUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    private void updateLocation(LatLng position) {

        if (!this.authenticationUser.isEmpty()) {
            Location currentLocation = authenticationUser.getLocation();
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            //Don't post new location if user is in the same spot
            if (MapHelper.calculateDistanceMeters(currentLatLng, position) > 50) {
                currentLocation.setLatitude(position.latitude);
                currentLocation.setLongitude ( position.longitude);
                this.updateLocation(currentLocation);
            }
        }
    }

    public boolean hasLocationsFile() {
        Locations locations = getLocalLocationsFromFile();
        return locations.size() <= 0 ? false : true;
    }

    public void setLocationCrudListeners(LocationCrudListener locationCrudListener){
        locationCrudListeners.add(locationCrudListener);
    }

    public void setLocationSearchListeners(LocationSearchListener locationSearchListener){
        locationSearchListeners.add(locationSearchListener);
    }

   /* @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LocationService.class.getName(), "handle");
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                // processStartNotification();
                gps = new GPSReceiver(this);
                gps.delegate = this;
                gps.Start();
            }
            if (ACTION_DELETE.equals(action)) {
                //processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    @Override
    public void onGPSConnectionResolutionRequest(ConnectionResult connectionResult) {
        Log.d(LocationService.class.getName(), "CONNECTION_FAILURE_RESOLUTION_REQUEST");
    }

    @Override
    public void onGPSWarning(String message) {
        Log.d(LocationService.class.getName(), message);
    }

    @Override
    public void onGPSPositionResult(LatLng position) {
        gps.Stop();
        updateLocation(position);
        Log.d(LocationService.class.getName(), position.toString());
    }*/


}
