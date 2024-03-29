package com.koopey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.koopey.R;
import com.koopey.helper.DistanceHelper;
import com.koopey.helper.ImageHelper;
import com.koopey.model.*;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    public UserAdapter(Context context, Users users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            // Get the data item for this position
            User user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_row, parent, false);
            }
            // Lookup view for data population
            //TagTokenAutoCompleteView lstTags= (TagTokenAutoCompleteView) convertView.findViewById(R.id.lstTags);
            TextView txtDistance = (TextView) convertView.findViewById(R.id.txtDistanceItem);
            TextView txtAlias = (TextView) convertView.findViewById(R.id.txtAliasItem);
            TextView txtName = (TextView) convertView.findViewById(R.id.txtNameItem);
            ImageView img = (ImageView) convertView.findViewById(R.id.imgAvatarItem);
            // Populate the data into the template view using the data object
            //lstTags.allowDuplicates(false);
            //lstTags.setFocusable(false) ;
            //lstTags.setClickable(false);
            //lstTags.clear();
            //Add existing selected tags to control
            //for(Tag t : user.tags.getList()) {
            //    lstTags.addObject(t);
            //}
            txtDistance.setText(DistanceHelper.DistanceToKilometers(user.getDistance()));
            txtAlias.setText(user.getAlias());
            txtName.setText(user.getName());
            try {
                if (user.getAvatar() != null && user.getAvatar().length() > 0 ) {
                    img.setImageBitmap(ImageHelper.UriToBitmap( user.getAvatar()));
                } else {
                    img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.default_user));
                }
            } catch (Exception iex) {
                Log.d(UserAdapter.class.getName(), "Image not loaded");
            }
            if (this.getContext().getResources().getBoolean(R.bool.alias)) {
                txtAlias.setVisibility(View.VISIBLE);
            } else {
                txtAlias.setVisibility(View.INVISIBLE);
            }
            if (this.getContext().getResources().getBoolean(R.bool.name)) {
                txtName.setVisibility(View.VISIBLE);
            } else {
                txtName.setVisibility(View.INVISIBLE);
            }
        } catch (Exception ex) {
            Log.d(UserAdapter.class.getName(), ex.getMessage());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
