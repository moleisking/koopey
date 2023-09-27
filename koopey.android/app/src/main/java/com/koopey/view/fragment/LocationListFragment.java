package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.adapter.LocationAdapter;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Location;
import com.koopey.model.Locations;

public class LocationListFragment extends ListFragment {

    protected Locations locations = new Locations();
    protected FloatingActionButton btnCreate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.btnCreate = (FloatingActionButton) getActivity().findViewById(R.id.btnCreate);
        this.btnCreate.setVisibility(View.GONE);
        this.syncLocations();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.location_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (!this.locations.isEmpty()) {
            Location location = this.locations.get(position);
            //  ((PrivateActivity) getActivity()).showLocationReadFragment(location);
        }
    }

    protected void editLocation(Location location) {
        getActivity().getIntent().putExtra("location", location);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_main_frame, new LocationEditFragment())
                .addToBackStack("location_edit")
                .commit();
    }

    protected void populateLocations() {
        if (this.locations != null && this.locations.size() > 0 ) {
            LocationAdapter locationAdapter = new LocationAdapter(this.getActivity(), this.locations);
            this.setListAdapter(locationAdapter);
        }
    }

    protected void syncLocations() {
        if (getActivity().getIntent().hasExtra("locations")) {
            this.locations = (Locations)getActivity().getIntent().getSerializableExtra("locations");
            this.populateLocations();
        } else if (SerializeHelper.hasFile(this.getActivity(), Locations.ASSET_SEARCH_RESULTS_FILE_NAME)) {
            this.locations = (Locations) SerializeHelper.loadObject(this.getActivity(), Locations.ASSET_SEARCH_RESULTS_FILE_NAME);
            this.populateLocations();
        } else {
            this.locations =  new Locations();
        }
    }

    protected void viewLocation(Location location) {
        getActivity().getIntent().putExtra("location", location);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_main_frame, new LocationEditFragment())
                .addToBackStack("location_view")
                .commit();
    }
}
