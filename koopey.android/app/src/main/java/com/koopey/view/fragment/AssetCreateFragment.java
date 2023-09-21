package com.koopey.view.fragment;


import android.app.Activity;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;

import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.HashHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.GPSReceiver;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Image;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AssetService;
import com.koopey.service.AuthenticationService;
import com.koopey.view.PrivateActivity;
import com.koopey.view.PublicActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;

public class AssetCreateFragment extends Fragment implements
        ImageListFragment.OnImageListFragmentListener,         View.OnClickListener, AssetService.AssetCrudListener {

    private EditText txtTitle, txtDescription, txtValue;
    private ImageView img;
    private Asset asset ;
    private Assets assets = new Assets();

    public AuthenticationUser authenticationUser;

    public Tags tags;
    private TagTokenAutoCompleteView lstTags;

    private Spinner lstCurrency;
    private FloatingActionButton btnCreate;
    private TagAdapter tagAdapter;
    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == btnCreate.getId()) {
                //Create asset object
             //   this.asset.user = authUser.getUserBasicWithAvatar();
                this.asset.setName(txtTitle.getText().toString());
                this.asset.setDescription(txtDescription.getText().toString());
                this.asset.setValue(Double.valueOf(txtValue.getText().toString()));
                                //Check asset object
                if (this.asset.isValid()) {
                    //Post new asset to server
                  AssetService assetService =  new AssetService(getContext());
                  assetService.createAsset(this.asset);
                    //Add asset to local file
                    this.assets.add(asset);
                    SerializeHelper.saveObject(this.getActivity(), assets);
                    ((PrivateActivity) getActivity()).showMyAssetListFragment();
                }
                Toast.makeText(this.getActivity(), getResources().getString(R.string.label_create), Toast.LENGTH_LONG).show();
            } else if (v.getId() == img.getId()) {
                ((PrivateActivity) getActivity()).showImageListFragment(this.asset.images);
            }
        } catch (Exception ex) {
            Log.d(AssetCreateFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize objects
        this.img = (ImageView) getActivity().findViewById(R.id.img);
        this.txtTitle = (EditText) getActivity().findViewById(R.id.txtTitle);
        this.txtDescription = (EditText) getActivity().findViewById(R.id.txtDescription);
        this.txtValue = (EditText) getActivity().findViewById(R.id.txtValue);
        // this.lstTags = (TagTokenAutoCompleteView) getActivity().findViewById(R.id.lstTags);
        this.btnCreate = (FloatingActionButton) getActivity().findViewById(R.id.btnCreate);
        this.authenticationUser = ((PrivateActivity) getActivity()).getAuthenticationUser();
        this.tags = ((PrivateActivity) getActivity()).getTags();
        //Populate controls
        this.populateTags();
        this.populateCurrencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset_create, container, false);
    }





   /* @Override
    public void onGPSPositionResult(LatLng position) {
        try {
            this.asset.location.latitude = position.latitude;
            this.asset.location.longitude = position.longitude;
            gps.Stop();
        } catch (Exception ex) {
            Log.d(AssetCreateFragment.class.getName(), ex.getMessage());
        }
    }*/

    public void createImageListFragmentEvent(Image image) {
        this.asset.images.add(image);
        // this.postImageCreate(image);
    }

    public void updateImageListFragmentEvent(Image image) {
        this.asset.images.set(image);
        //this.postImageUpdate(image);
    }

    public void deleteImageListFragmentEvent(Image image) {
        this.asset.images.remove(image);
        //this.postImageDelete(image);
    }

    private void populateTags() {
        this.tagAdapter = new TagAdapter(this.getActivity(), this.tags, this.asset.tags, this.authenticationUser.getLanguage());
     //   this.lstTags.allowDuplicates(false);
      //  this.lstTags.setAdapter(tagAdapter);
     //   this.lstTags.setTokenLimit(15);
    }

    private void populateCurrencies() {
        this.currencyCodeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(currencySymbolAdapter);
    }



    @Override
    public void onAssetRead(int code, String message, String assetId) {
    }

    @Override
    public void onAssetUpdateAvailable(int code, String message) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_update), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAssetCreate(int code, String message, String assetId) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_create), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAssetDelete(int code, String message) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_delete), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAssetUpdate(int code, String message) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_update), Toast.LENGTH_LONG).show();
    }
}