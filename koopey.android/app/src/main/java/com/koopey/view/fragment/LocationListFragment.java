package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.adapter.LocationAdapter;
import com.koopey.adapter.WalletAdapter;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Assets;
import com.koopey.model.Location;
import com.koopey.model.Locations;
import com.koopey.model.Wallet;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AssetService;
import com.koopey.service.LocationService;
import com.koopey.view.MainActivity;

import java.net.HttpURLConnection;

public class LocationListFragment extends ListFragment implements View.OnClickListener, LocationService.LocationSearchByOwnerListener {

    private AuthenticationUser authenticationUser;
    private Locations locations = new Locations();
    private LocationService locationService;
    private LocationAdapter locationAdapter;
    private FloatingActionButton btnCreate;

    private void bindAdapter() {
        locationAdapter = new LocationAdapter(this.getActivity(), locations);
        setListAdapter(locationAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCreate.getId()) {
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_location_edit);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        ((MainActivity) getActivity()).hideKeyboard();
        locationService = new LocationService(getContext());
        locationService.setLocationSearchByOwnerListeners(this);

        if (getActivity().getIntent().hasExtra("locations")) {
            locations = (Locations) getActivity().getIntent().getSerializableExtra("locations");
        } else if (SerializeHelper.hasFile(this.getActivity(), Locations.LOCATIONS_FILE_NAME)) {
            locations = (Locations) SerializeHelper.loadObject(this.getActivity(), Locations.LOCATIONS_FILE_NAME);
        } else {
            locations = new Locations();
            locationService.searchByOwner();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.location_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (locations != null && locations.size() > 0) {
            Location location = this.locations.get(position);
            getActivity().getIntent().putExtra("location", location);
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_location_view);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.btnCreate = (FloatingActionButton) getActivity().findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
        bindAdapter();
    }

    @Override
    public void onLocationSearchByOwner(int code, String message, Locations locations) {
        if (code == HttpURLConnection.HTTP_OK) {
            this.locations = locations;
            bindAdapter();
            Toast.makeText(this.getActivity(), getResources().getString(R.string.label_search), Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }
}
