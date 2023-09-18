package com.koopey.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;

import com.koopey.R;
import com.koopey.controller.GPSReceiver;
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

public class LocationService extends IntentService implements GPSReceiver.OnGPSReceiverListener {


    public interface LocationCrudListener {
        void onLocationCreate(int code, String message, Location location);

        void onLocationDelete(int code, String message, Location location);

        void onLocationUpdate(int code, String message, Location location);

        void onLocationRead(int code, String message, Location location);
    }

    public interface LocationSearchListener {
        void onGetLocationSearchByBuyerAndDestination(Locations locations);

        void onGetLocationSearchByBuyerAndSource(Locations locations);

        void onGetLocationSearchByDestinationAndSeller(Locations locations);

        void onGetLocationSearchBySellerAndSource(Locations locations);

        void onPostLocationSearch(Locations locations);

        void onPostLocationSearchByGeocode(Location location);

        void onPostLocationSearchByPlace(Location location);

        void onPostLocationSearchByRangeInKilometers(Locations locations);

        void onPostLocationSearchByRangeInMiles(Locations locations);

    }

    AuthenticationUser authenticationUser;
    AuthenticationService authenticationService;
    private Context context;

    private List<LocationService.LocationCrudListener> locationCrudListeners = new ArrayList<>();
    private List<LocationService.LocationSearchListener> locationSearchListeners = new ArrayList<>();


    private static final int LOCATION_NOTIFICATION = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    private AuthenticationUser authUser = new AuthenticationUser();
    private GPSReceiver gps;
    // public ResponseMSG messageDelegate = null;

    public LocationService(Context context) {
        super(LocationService.class.getSimpleName());
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

    public void getLocation(String locationId) {

        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .getLocation(locationId).enqueue(new Callback<>() {
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

    public void getLocationSearchByBuyerAndDestination() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .getLocationSearchByBuyerAndDestination().enqueue(new Callback<Locations>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            Log.i(LocationService.class.getName(), "location is null");
                        } else {
                            for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                                listener.onGetLocationSearchByBuyerAndDestination(locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), locations.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                            listener.onGetLocationSearchByBuyerAndDestination(null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void getLocationSearchByBuyerAndSource() {
        ILocationService service
                = HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token);

        Call<Locations> callAsync = service.getLocationSearchByBuyerAndDestination();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onGetLocationSearchByBuyerAndSource(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), locations.toString());
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onGetLocationSearchByBuyerAndSource(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getLocationCreate(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .postLocationCreate(location).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String locationId = response.body();
                        location.id = locationId;
                        if (locationId == null || locationId.isEmpty()) {
                            for (LocationService.LocationCrudListener listener : locationCrudListeners) {
                                listener.onLocationCreate(HttpURLConnection.HTTP_NO_CONTENT, "", new Location());
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

    public void getLocationDelete(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .postLocationDelete(location).enqueue(new Callback<>() {
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

    public void postLocationSearch(Search search) {
       HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
               .postLocationSearch(search).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onPostLocationSearch(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), locations.toString());
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onPostLocationSearch(null);
                }
                Log.e(LocationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getLocationSearchByGeocode(Location location) {
       HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
               .postLocationSearchByGeocode(location).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location = response.body();
                if (location == null || location.isEmpty()) {
                    Log.i(LocationService.class.getName(), "locations is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onPostLocationSearchByGeocode(location);
                    }
                    SerializeHelper.saveObject(context, location);
                    Log.i(LocationService.class.getName(), location.toString());
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onPostLocationSearchByGeocode(null);
                }
                Log.e(LocationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getLocationSearchByDestinationAndSeller() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .getLocationSearchByDestinationAndSeller().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "locations is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onGetLocationSearchByDestinationAndSeller(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onGetLocationSearchByDestinationAndSeller(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getLocationSearchBySellerAndSource() {
         HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                 .getLocationSearchBySellerAndSource().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onGetLocationSearchBySellerAndSource(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onGetLocationSearchBySellerAndSource(null);
                }
                Log.e(LocationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postLocationByPlace(Location location) {
       HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
               .postLocationSearchByPlace(location).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location = response.body();
                if (location == null || location.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onPostLocationSearchByPlace(location);
                    }
                    SerializeHelper.saveObject(context, location);
                    Log.i(LocationService.class.getName(), location.toString());
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onPostLocationSearchByPlace(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postLocationByRangeInKilometers(Search search) {

        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .postLocationSearchByRangeInKilometers(search).enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "location is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onPostLocationSearchByRangeInKilometers(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onPostLocationSearchByRangeInKilometers(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postLocationByRangeInMiles(Search search) {
      HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
              .postLocationSearchByRangeInMiles(search).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(LocationService.class.getName(), "locations is null");
                } else {
                    for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                        listener.onPostLocationSearchByRangeInMiles(locations);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                }
            }
            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                    listener.onPostLocationSearchByRangeInMiles(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postLocationUpdate(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .postLocationUpdate(location).enqueue(new Callback<Void>() {
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

    private void postLocationUpdate(LatLng position) {

        //   this.authUser =  (AuthUser) SerializeHelper.loadObject(getApplicationContext() ,AuthUser.AUTH_USER_FILE_NAME);
        if (!this.authUser.isEmpty()) {
            Location currentLocation = authUser.location;
            LatLng currentLatLng = new LatLng(currentLocation.latitude, currentLocation.longitude);
            //Don't post new location if user is in the same spot
            if (MapHelper.calculateDistanceMeters(currentLatLng, position) > 50) {
                currentLocation.latitude = position.latitude;
                currentLocation.longitude = position.longitude;
                this.postLocationUpdate(currentLocation);
                //Log.d(LocationService.class.getName(),"sendLocation");
                //String url = getResources().getString(R.string.post_user_update_location);

                //PostJSON asyncTask = new PostJSON(this.getApplicationContext());
                // GetJSON asyncTask =new GetJSON(context);
                // asyncTask.delegate = this;
                //asyncTask.execute(url, currentLocation.toString(), authUser.getToken());
            }
        }

    }

    public boolean hasLocationsFile() {
        Locations locations = getLocalLocationsFromFile();
        return locations.size() <= 0 ? false : true;
    }

    @Override
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
        postLocationUpdate(position);
        Log.d(LocationService.class.getName(), position.toString());
    }


}
