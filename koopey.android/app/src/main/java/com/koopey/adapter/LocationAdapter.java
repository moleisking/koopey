package com.koopey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.koopey.R;
import com.koopey.model.Location;
import com.koopey.model.Locations;

public class LocationAdapter extends ArrayAdapter<Location> {


    public LocationAdapter(Context context, Locations locations) {
        super(context, 0, locations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try {
            Location location = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_row, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.txtIdItem)).setText(location.getId());
            ((TextView) convertView.findViewById(R.id.txtNameItem)).setText(location.getName());
            ((TextView) convertView.findViewById(R.id.txtTypeItem)).setText(location.getType());

        }catch (Exception ex){
            Log.d(LocationAdapter.class.getSimpleName(),ex.getMessage());
        }
        return convertView;
    }
}
