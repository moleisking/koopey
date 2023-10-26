package com.koopey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koopey.R;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.DistanceHelper;
import com.koopey.helper.ImageHelper;
import com.koopey.model.*;

public class AssetAdapter extends ArrayAdapter<Asset>
{

    public AssetAdapter(Context context, Assets assets) {
        super(context, 0, assets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try {
            // Get the data item for this position
            Asset asset = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_asset, parent, false);
            }
            // Lookup view for data population
            //TagTokenAutoCompleteView lstTags= (TagTokenAutoCompleteView) convertView.findViewById(R.id.lstTags);
            TextView txtName = convertView.findViewById(R.id.txtName);
            TextView txtDistance = convertView.findViewById(R.id.txtDistance);
            TextView txtCurrency = convertView.findViewById(R.id.txtCurrency);
            TextView txtValue = convertView.findViewById(R.id.txtValue);
            ImageView imgFirst = convertView.findViewById(R.id.imgFirst);

            // Populate the data into the template view using the data object
            //lstTags.allowDuplicates(false);
            //lstTags.setFocusable(false) ;
            //lstTags.setClickable(false);
            //lstTags.clear();
            //Add existing selected tags to control
            //for(Tag t : product.tags.getList()) {
            //    lstTags.addObject(t);
            //}

            txtName.setText(asset.getName());
            txtDistance.setText( DistanceHelper.DistanceToKilometers(asset.getDistance()));
            txtValue.setText( asset.getValueAsString());
           // txtCurrency.setText(CurrencyHelper.currencyCodeToSymbol( asset.getCurrency()));
            try {
                if (!asset.getFirstImage().equals("")) {
                    imgFirst.setImageBitmap(ImageHelper.parseImageUri(asset.getFirstImage()) );
                }
                else
                {
                    imgFirst.setImageDrawable(getContext().getResources().getDrawable(R.drawable.default_product));
                }
            }catch (Exception iex){
                Log.d(AssetAdapter.class.getName(),"Image not loaded");
            }

        }catch (Exception ex){
            Log.d(AssetAdapter.class.getName(),ex.getMessage());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
