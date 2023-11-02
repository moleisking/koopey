package com.koopey.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.helper.CurrencyHelper;
import com.koopey.model.Location;
import com.koopey.model.Wallet;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.LocationService;
import com.koopey.service.PositionService;
import com.koopey.view.MainActivity;

import java.net.HttpURLConnection;
import java.util.Arrays;

public class LocationEditFragment extends Fragment implements LocationService.LocationEditListener, PlaceSelectionListener,
        PositionService.PositionListener, View.OnClickListener {
    ArrayAdapter<CharSequence> locationAdapter;
    private AuthenticationUser authenticationUser;
    private AutocompleteSupportFragment placeFragment;
    private EditText txtDescription, txtName;
    private FloatingActionButton btnDelete, btnSave;
    private Spinner lstType;
    private Location location;
    private LocationService locationService;

    private void bindTypes() {
        locationAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.location_types, android.R.layout.simple_spinner_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstType.setAdapter(locationAdapter);
        lstType.setSelection(locationAdapter.getPosition(location.getType()));
    }

    private void bindPlaces() {
        try {
            placeFragment = (AutocompleteSupportFragment) getChildFragmentManager()
                    .findFragmentById(R.id.fragmentPlace);
            placeFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG));
            placeFragment.setOnPlaceSelectedListener(this);
        } catch (Exception aex) {
            Log.d(LocationEditFragment.class.getSimpleName(), aex.getMessage());
        }
    }

    private boolean checkForm() {
        if (txtName.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_name + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (this.txtDescription.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_address + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();

        locationService = new LocationService(getContext());
        locationService.setLocationEditListeners(this);

        PositionService positionService = new PositionService(this.getActivity());
        positionService.setPositionListeners(this);

        if (this.getActivity().getIntent().hasExtra("location")) {
            location = (Location) this.getActivity().getIntent().getSerializableExtra("location");
        } else {
            location = Location.builder()
                    .type("create")
                    .latitude(authenticationUser.getLatitude())
                    .longitude(authenticationUser.getLongitude())
                    .ownerId(authenticationUser.getId())
                    .build();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.location_edit, container, false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnSave.getId() && checkForm()) {
            Log.d(LocationEditFragment.class.getSimpleName(), "onSave");
            location.setName(this.txtName.getText().toString());
            location.setDescription(this.txtDescription.getText().toString());
            location.setType(lstType.getSelectedItem().toString());
            locationService.create(location);
        } else if (v.getId() == btnDelete.getId() && checkForm()) {
            locationService.delete(location);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity().getIntent().hasExtra("location")) {
            getActivity().getIntent().removeExtra("location");
        }
        if (this.placeFragment != null) {
            getChildFragmentManager().beginTransaction().remove(placeFragment).commit();
        }
    }

    @Override
    public void onError(@NonNull Status status) {
        Toast.makeText(this.getActivity(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationCreate(int code, String message, Location location) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationDelete(int code, String message, Location location) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationUpdate(int code, String message, Location location) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPlaceSelected(Place place) {
        location.setLatitude(place.getLatLng().latitude);
        location.setLongitude(place.getLatLng().longitude);
        location.setDescription(place.getAddress());
    }

    @Override
    public void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude) {
        location.setAltitude(altitude);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
    }

    @Override
    public void onPositionRequestFail(String errorMessage) {

    }

    @Override
    public void onPositionRequestPermission() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (location != null) {
            txtDescription.setText(location.getDescription());
            txtName.setText(location.getName());
            lstType.setSelection(locationAdapter.getPosition(location.getType()));
        }
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        btnDelete = (FloatingActionButton) getActivity().findViewById(R.id.btnDelete);
        btnSave = (FloatingActionButton) getActivity().findViewById(R.id.btnSave);
        lstType = (Spinner) getActivity().findViewById(R.id.lstType);
        txtName = (EditText) getActivity().findViewById(R.id.txtName);
        txtDescription = (EditText) getActivity().findViewById(R.id.txtDescription);

        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        if (location.getType().equals("create")) {
            btnDelete.setVisibility(View.INVISIBLE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }
        bindTypes();
        bindPlaces();
    }



    private void populateAddress() {
     /*   AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        this.placeFragment.setFilter(typeFilter);*/

    }
}
