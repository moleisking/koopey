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
import com.koopey.controller.PostJSON;
import com.koopey.helper.MapHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.AuthUser;
import com.koopey.model.Location;
public class LocationService extends IntentService implements GPSReceiver.OnGPSReceiverListener {

    private static final String LOG_HEADER = "LOCATION:SERVICE";
    private static final int LOCATION_NOTIFICATION = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    private AuthUser authUser = new AuthUser();
    private GPSReceiver gps;
    // public ResponseMSG messageDelegate = null;

    public LocationService() {
        super(LocationService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Log.d(LOG_HEADER,"start");
        Intent intent = new Intent(context, MessageService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Log.d(LOG_HEADER,"delete");
        Intent intent = new Intent(context, MessageService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_HEADER,"handle");
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                // processStartNotification();
                gps = new GPSReceiver (this);
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
    public void onGPSConnectionResolutionRequest(ConnectionResult connectionResult )
    {
        Log.d(LOG_HEADER + ":ER","CONNECTION_FAILURE_RESOLUTION_REQUEST");
    }

    @Override
    public void onGPSWarning(String message)
    {
        Log.d(LOG_HEADER + ":ER",message);
    }

    @Override
    public void onGPSPositionResult(LatLng position)    {
        gps.Stop();
        sendLocation(position);
        Log.d(LOG_HEADER + ":RES",position.toString());
    }

    private void sendLocation(LatLng position)    {
        this.authUser =  (AuthUser) SerializeHelper.loadObject(getApplicationContext() ,AuthUser.AUTH_USER_FILE_NAME);
        if(!this.authUser.isEmpty())  {
            Location currentLocation = authUser.location;
            LatLng currentLatLng = new LatLng(   currentLocation.latitude,currentLocation.longitude);
            //Don't post new location if user is in the same spot
            if (MapHelper.calculateDistanceMeters(currentLatLng,position) > 50)            {
                currentLocation.latitude = position.latitude;
                currentLocation.longitude = position.longitude;

                Log.d(LOG_HEADER,"sendLocation");
                String url = getResources().getString(R.string.post_user_update_location);

                PostJSON asyncTask = new PostJSON(this.getApplicationContext());
                // GetJSON asyncTask =new GetJSON(context);
                // asyncTask.delegate = this;
                asyncTask.execute(url, currentLocation.toString(), authUser.getToken());
            }
        }
    }
}
