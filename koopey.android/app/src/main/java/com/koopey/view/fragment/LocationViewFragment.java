package com.koopey.view.fragment;

import com.koopey.R;
import com.koopey.model.Location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LocationViewFragment extends Fragment {

    private TextView txtDescription, txtLatitude, txtLongitude, txtName;
private Location location;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent().hasExtra("location")) {
            location = (Location) getActivity().getIntent().getSerializableExtra("location");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.location_view, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().getIntent().hasExtra("location")) {
            getActivity().getIntent().removeExtra("location");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!location.isEmpty()) {
            txtName.setText(location.getName());
            txtDescription.setText(location.getDescription());
            txtLatitude.setText(location.getLatitude().toString());
            txtLongitude.setText(location.getLongitude().toString());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtName = (TextView) getActivity().findViewById(R.id.txtName);
        txtDescription = (TextView) getActivity().findViewById(R.id.txtDescription);
        txtLatitude = (TextView) getActivity().findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) getActivity().findViewById(R.id.txtLongitude);
    }
}
