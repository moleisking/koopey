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

public class LocationService {

    public interface LocationEditListener {
        void onLocationCreate(int code, String message, Location location);

        void onLocationDelete(int code, String message, Location location);

        void onLocationUpdate(int code, String message, Location location);
    }

    public interface LocationSearchListener {
        void onLocationSearch(int code, String message, Locations locations);
    }

    public interface LocationSearchByBuyerOrSellerListener {
        void onLocationSearchByBuyerAndDestination(int code, String message, Locations locations);

        void onLocationSearchByBuyerAndSource(int code, String message, Locations locations);

        void onLocationSearchByDestinationAndSeller(int code, String message, Locations locations);

        void onLocationSearchBySellerAndSource(int code, String message, Locations locations);
    }

    public interface LocationSearchByGeocodeListener {
        void onLocationSearchByGeocode(int code, String message, Location location);
    }

    public interface LocationSearchByOwnerListener {
        void onLocationSearchByOwner(int code, String message, Locations locations);
    }

    public interface LocationSearchByPlaceListener {
        void onLocationSearchByPlace(int code, String message, Location location);
    }

    public interface LocationSearchByRangeListener {
        void onLocationSearchByPlace(int code, String message, Location location);

        void onLocationSearchByRangeInKilometers(int code, String message, Locations locations);

        void onLocationSearchByRangeInMiles(int code, String message, Locations locations);
    }

    public interface LocationViewListener {
        void onLocationRead(int code, String message, Location location);
    }

    private AuthenticationUser authenticationUser;
    private AuthenticationService authenticationService;
    private Context context;

    private List<LocationService.LocationEditListener> locationEditListeners = new ArrayList<>();
    private List<LocationService.LocationViewListener> locationViewListeners = new ArrayList<>();
    private List<LocationService.LocationSearchListener> locationSearchListeners = new ArrayList<>();
    private List<LocationService.LocationSearchByPlaceListener> locationSearchByPlaceListeners = new ArrayList<>();
    private List<LocationService.LocationSearchByGeocodeListener> locationSearchByGeocodeListeners = new ArrayList<>();
    private List<LocationService.LocationSearchByOwnerListener> locationSearchByOwnerListeners = new ArrayList<>();
    private List<LocationService.LocationSearchByBuyerOrSellerListener> locationSearchByBuyerAndSellerListeners = new ArrayList<>();
    private List<LocationService.LocationSearchByRangeListener> locationSearchByRangeListeners = new ArrayList<>();
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

    public void read(String locationId) {

        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .read(locationId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Location> call, Response<Location> response) {
                        Location location = response.body();
                        if (location == null || location.isEmpty()) {
                            for (LocationService.LocationViewListener listener : locationViewListeners) {
                                listener.onLocationRead(HttpURLConnection.HTTP_NO_CONTENT, "", location);
                            }
                        } else {
                            for (LocationService.LocationViewListener listener : locationViewListeners) {
                                listener.onLocationRead(HttpURLConnection.HTTP_OK, "", location);
                            }
                            SerializeHelper.saveObject(context, location);
                            Log.i(LocationService.class.getName(), location.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Location> call, Throwable throwable) {
                        for (LocationService.LocationViewListener listener : locationViewListeners) {
                            listener.onLocationRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByBuyerAndDestination() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByBuyerAndDestination().enqueue(new Callback<Locations>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                                listener.onLocationSearchByBuyerAndDestination(HttpURLConnection.HTTP_NO_CONTENT, "", new Locations());
                            }
                            Log.i(LocationService.class.getName(), "location is null");
                        } else {
                            for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                                listener.onLocationSearchByBuyerAndDestination(HttpURLConnection.HTTP_OK, "", locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), locations.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                            listener.onLocationSearchByBuyerAndDestination(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Locations());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByBuyerAndSource() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByBuyerAndDestination().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                                listener.onLocationSearchByBuyerAndSource(HttpURLConnection.HTTP_NO_CONTENT, "", new Locations());
                            }
                            Log.i(LocationService.class.getName(), "location is null");
                        } else {
                            for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                                listener.onLocationSearchByBuyerAndSource(HttpURLConnection.HTTP_OK, "", locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), locations.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                            listener.onLocationSearchByBuyerAndSource(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Locations());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void create(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .create(location).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String locationId = response.body();
                        location.setId(locationId);
                        if (locationId == null || locationId.isEmpty()) {
                            for (LocationService.LocationEditListener listener : locationEditListeners) {
                                listener.onLocationCreate(HttpURLConnection.HTTP_NO_CONTENT, "", null);
                            }
                        } else {
                            for (LocationService.LocationEditListener listener : locationEditListeners) {
                                listener.onLocationCreate(HttpURLConnection.HTTP_CREATED, "", location);
                            }
                            SerializeHelper.saveObject(context, location);
                            Log.i(LocationService.class.getName(), location.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        for (LocationService.LocationEditListener listener : locationEditListeners) {
                            listener.onLocationCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void delete(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .delete(location).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (LocationService.LocationEditListener listener : locationEditListeners) {
                            listener.onLocationDelete(HttpURLConnection.HTTP_OK, "", location);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (LocationService.LocationEditListener listener : locationEditListeners) {
                            listener.onLocationDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void search(Search search) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .search(search).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                                listener.onLocationSearch(HttpURLConnection.HTTP_NO_CONTENT, "", new Locations());
                            }
                            Log.i(LocationService.class.getName(), "location is null");
                        } else {
                            for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                                listener.onLocationSearch(HttpURLConnection.HTTP_OK, "", locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), locations.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchListener listener : locationSearchListeners) {
                            listener.onLocationSearch(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Locations());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByGeocode(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByGeocode(location).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Location> call, Response<Location> response) {
                        Location location = response.body();
                        if (location == null || location.isEmpty()) {
                            for (LocationService.LocationSearchByGeocodeListener listener : locationSearchByGeocodeListeners) {
                                listener.onLocationSearchByGeocode(HttpURLConnection.HTTP_NO_CONTENT, "", new Location());
                            }
                            Log.i(LocationService.class.getName(), "locations is null");
                        } else {
                            for (LocationService.LocationSearchByGeocodeListener listener : locationSearchByGeocodeListeners) {
                                listener.onLocationSearchByGeocode(HttpURLConnection.HTTP_OK, "", location);
                            }
                            SerializeHelper.saveObject(context, location);
                            Log.i(LocationService.class.getName(), location.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Location> call, Throwable throwable) {
                        for (LocationService.LocationSearchByGeocodeListener listener : locationSearchByGeocodeListeners) {
                            listener.onLocationSearchByGeocode(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Location());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByDestinationAndSeller() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByDestinationAndSeller().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                                listener.onLocationSearchByDestinationAndSeller(HttpURLConnection.HTTP_NO_CONTENT, "", new Locations());
                            }
                            Log.i(LocationService.class.getName(), "locations is null");
                        } else {
                            for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                                listener.onLocationSearchByDestinationAndSeller(HttpURLConnection.HTTP_OK, "", locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                            listener.onLocationSearchByDestinationAndSeller(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Locations());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByOwner() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByOwner().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            for (LocationService.LocationSearchByOwnerListener listener : locationSearchByOwnerListeners) {
                                listener.onLocationSearchByOwner(HttpURLConnection.HTTP_NO_CONTENT, "", new Locations());
                            }
                            Log.i(LocationService.class.getName(), "locations is null");
                        } else {
                            for (LocationService.LocationSearchByOwnerListener listener : locationSearchByOwnerListeners) {
                                listener.onLocationSearchByOwner(HttpURLConnection.HTTP_OK, "", locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchByOwnerListener listener : locationSearchByOwnerListeners) {
                            listener.onLocationSearchByOwner(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Locations());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchBySellerAndSource() {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchBySellerAndSource().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                                listener.onLocationSearchBySellerAndSource(HttpURLConnection.HTTP_NO_CONTENT, "", new Locations());
                            }
                            Log.i(LocationService.class.getName(), "location is null");
                        } else {
                            for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                                listener.onLocationSearchBySellerAndSource(HttpURLConnection.HTTP_OK, "", locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchByBuyerOrSellerListener listener : locationSearchByBuyerAndSellerListeners) {
                            listener.onLocationSearchBySellerAndSource(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Locations());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByPlace(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByPlace(location).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Location> call, Response<Location> response) {
                        Location location = response.body();
                        if (location == null || location.isEmpty()) {
                            for (LocationService.LocationSearchByPlaceListener listener : locationSearchByPlaceListeners) {
                                listener.onLocationSearchByPlace(HttpURLConnection.HTTP_NO_CONTENT, "", new Location());
                            }
                            Log.i(LocationService.class.getName(), "location is null");
                        } else {
                            for (LocationService.LocationSearchByPlaceListener listener : locationSearchByPlaceListeners) {
                                listener.onLocationSearchByPlace(HttpURLConnection.HTTP_OK, "", location);
                            }
                            SerializeHelper.saveObject(context, location);
                            Log.i(LocationService.class.getName(), location.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Location> call, Throwable throwable) {
                        for (LocationService.LocationSearchByPlaceListener listener : locationSearchByPlaceListeners) {
                            listener.onLocationSearchByPlace(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Location());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByRangeInKilometers(Search search) {

        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByRangeInKilometers(search).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            for (LocationService.LocationSearchByRangeListener listener : locationSearchByRangeListeners) {
                                listener.onLocationSearchByRangeInKilometers(HttpURLConnection.HTTP_NO_CONTENT, "", new Locations());
                            }
                            Log.i(LocationService.class.getName(), "location is null");
                        } else {
                            for (LocationService.LocationSearchByRangeListener listener : locationSearchByRangeListeners) {
                                listener.onLocationSearchByRangeInKilometers(HttpURLConnection.HTTP_OK, "", locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchByRangeListener listener : locationSearchByRangeListeners) {
                            listener.onLocationSearchByRangeInKilometers(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Locations());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByRangeInMiles(Search search) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByRangeInMiles(search).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Locations> call, Response<Locations> response) {
                        Locations locations = response.body();
                        if (locations == null || locations.isEmpty()) {
                            for (LocationService.LocationSearchByRangeListener listener : locationSearchByRangeListeners) {
                                listener.onLocationSearchByRangeInMiles(HttpURLConnection.HTTP_NO_CONTENT, response.message(), new Locations());
                            }
                            Log.i(LocationService.class.getName(), "locations is null");
                        } else {
                            for (LocationService.LocationSearchByRangeListener listener : locationSearchByRangeListeners) {
                                listener.onLocationSearchByRangeInMiles(HttpURLConnection.HTTP_OK, response.message(), locations);
                            }
                            SerializeHelper.saveObject(context, locations);
                            Log.i(LocationService.class.getName(), String.valueOf(locations.size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Locations> call, Throwable throwable) {
                        for (LocationService.LocationSearchByRangeListener listener : locationSearchByRangeListeners) {
                            listener.onLocationSearchByRangeInMiles(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Locations());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void update(Location location) {
        HttpServiceGenerator.createService(ILocationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .update(location).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (LocationService.LocationEditListener listener : locationEditListeners) {
                            listener.onLocationUpdate(HttpURLConnection.HTTP_OK, "", location);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (LocationService.LocationEditListener listener : locationEditListeners) {
                            listener.onLocationUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Location());
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    private void update(LatLng position) {

        if (!this.authenticationUser.isEmpty()) {
            Location currentLocation = authenticationUser.getLocation();
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            //Don't post new location if user is in the same spot
            if (MapHelper.calculateDistanceMeters(currentLatLng, position) > 50) {
                currentLocation.setLatitude(position.latitude);
                currentLocation.setLongitude(position.longitude);
                this.update(currentLocation);
            }
        }
    }

    public boolean hasLocationsFile() {
        Locations locations = getLocalLocationsFromFile();
        return locations.size() <= 0 ? false : true;
    }

    public void setLocationEditListeners(LocationEditListener locationEditListener) {
        locationEditListeners.add(locationEditListener);
    }

    public void setLocationSearchListeners(LocationSearchListener locationSearchListener) {
        locationSearchListeners.add(locationSearchListener);
    }

    public void setLocationSearchByBuyerAndSellerListeners(LocationSearchByBuyerOrSellerListener listener) {
        locationSearchByBuyerAndSellerListeners.add(listener);
    }

    public void setLocationSearchByGeocodeListeners(LocationSearchByGeocodeListener listener) {
        locationSearchByGeocodeListeners.add(listener);
    }

    public void setLocationSearchByOwnerListeners(LocationSearchByOwnerListener listener) {
        locationSearchByOwnerListeners.add(listener);
    }

    public void setLocationSearchByPlaceListeners(LocationSearchByPlaceListener listener) {
        locationSearchByPlaceListeners.add(listener);
    }

    public void setLocationViewListeners(LocationViewListener locationViewListener) {
        locationViewListeners.add(locationViewListener);
    }

}
