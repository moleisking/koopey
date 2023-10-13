package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.adapter.LocationAdapter;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Assets;
import com.koopey.model.Location;
import com.koopey.model.Locations;

public class LocationListFragment extends ListFragment implements View.OnClickListener {

    protected Locations locations = new Locations();
    protected FloatingActionButton btnCreate;

    @Override
    public void onClick(View v) {
        Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_location_edit);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getLocations();
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
            getActivity().getIntent().putExtra("location", location);
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_location_view);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.btnCreate = (FloatingActionButton) getActivity().findViewById(R.id.btnCreate);
        this.btnCreate.setVisibility(View.VISIBLE);
    }

    protected void edit(Location location) {
        Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_location_edit);
    }

    protected void populateLocations() {
        if (this.locations != null && this.locations.size() > 0 ) {
            LocationAdapter locationAdapter = new LocationAdapter(this.getActivity(), this.locations);
            this.setListAdapter(locationAdapter);
        }
    }

    protected void getLocations() {
        if (getActivity().getIntent().hasExtra("locations")) {
            this.locations = (Locations)getActivity().getIntent().getSerializableExtra("locations");
            this.populateLocations();
        } else if (SerializeHelper.hasFile(this.getActivity(), Assets.ASSET_SEARCH_RESULTS_FILE_NAME)) {
            this.locations = (Locations) SerializeHelper.loadObject(this.getActivity(), Assets.ASSET_SEARCH_RESULTS_FILE_NAME);
            this.populateLocations();
        } else {
            this.locations =  new Locations();
        }
    }

    protected void view(Location location) {
        Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_location_view);
    }

}
