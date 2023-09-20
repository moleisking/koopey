package com.koopey.view.component;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;
import com.koopey.controller.GPSReceiver;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.TagService;

public abstract class PrivateFragment extends Fragment implements  GPSReceiver.OnGPSReceiverListener {

    public AuthenticationService authenticationService;
    public AuthenticationUser authenticationUser;
    private GPSReceiver gps;
    public LatLng currentLatLng = new LatLng(0.0d, 0.0d);
    public Tags tags;
    public TagService tagService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
        tagService = new TagService(getContext());
        tags = new Tags();
        hideKeyboard();

    }

    @Override
    public void onGPSConnectionResolutionRequest(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this.getActivity(), GPSReceiver.OnGPSReceiverListener.CONNECTION_FAILURE_RESOLUTION_REQUEST);
        } catch (Exception ex) {
            Log.d(PrivateFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onGPSWarning(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGPSPositionResult(LatLng position) {
        this.currentLatLng = position;
        gps.Stop();
        Log.d(PrivateFragment.class.getName(), position.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        gps = new GPSReceiver(this.getActivity());
        gps.delegate = this;
        gps.Start();
    }

    @Override
    public void onStop() {
        super.onStop();
        gps.Stop();
    }

    public void hideKeyboard() {

       // ((PrivateActivity) getActivity()).hideKeyboard();
      /*  View currentView = this.getCurrentFocus();
        if (currentView != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }*/
    }
}
