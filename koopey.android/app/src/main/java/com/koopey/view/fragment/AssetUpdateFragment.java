package com.koopey.view.fragment;


import android.app.AlertDialog;

import android.content.DialogInterface;
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
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;

import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.DistanceHelper;
import com.koopey.helper.HashHelper;
import com.koopey.helper.SerializeHelper;



import com.koopey.model.Asset;
import com.koopey.model.Tag;
import com.koopey.model.Tags;
import com.koopey.model.Image;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AssetService;
import com.koopey.service.AuthenticationService;
import com.koopey.view.PrivateActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;

/**
 * Created by Scott on 18/01/2017.
 */
public class AssetUpdateFragment extends Fragment implements
        ImageListFragment.OnImageListFragmentListener,  View.OnClickListener, AssetService.AssetCrudListener {

private AssetService assetService;

    private Asset asset;
    private AuthenticationUser authenticationUser;
    private EditText txtTitle, txtDescription, txtValue;
    private FloatingActionButton btnUpdate, btnDelete;

    private ImageView img;
    private Tags tags = new Tags();
    private TagAdapter tagAdapter;
    private TagTokenAutoCompleteView lstTags;
    private Spinner lstCurrency;
    private Switch btnSold;

       @Override
    public void onClick(View v) {
        try {
            if (v.getId() == this.btnDelete.getId()) {
                this.showDeleteDialog();
            } else if (v.getId() == btnUpdate.getId()) {
                this.asset.setCurrency( CurrencyHelper.currencySymbolToCode(this.lstCurrency.getSelectedItem().toString()));
                if (!this.txtTitle.getText().equals("")) {
                    this.asset.setName(this.txtTitle.getText().toString());
                }
                if (!this.txtDescription.getText().equals("")) {
                    this.asset.setDescription(this.txtDescription.getText().toString());
                }
                if (!this.txtValue.getText().equals("")) {
                    this.asset.setValue( Double.valueOf(txtValue.getText().toString()));
                }
             //   if (this.asset.tags.compareTo(this.lstTags.getSelectedTags()) != 0) {
              //      this.asset.tags.setTagList(this.lstTags.getObjects());
               // }
                this.asset.location = authenticationUser.getLocation();
                this.asset.setAvailable( btnSold.isChecked());

                //Post data to server for update
                assetService.updateAsset(this.asset);

            } else if (v.getId() == this.img.getId()) {
                ((PrivateActivity) getActivity()).showImageListFragment(this.asset.images);
            }
        } catch (Exception ex) {
            Log.d(AssetUpdateFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Initialize objects
        this.btnDelete = (FloatingActionButton) getActivity().findViewById(R.id.btnDelete);
        this.btnSold = (Switch) getActivity().findViewById(R.id.btnSold);
        this.btnUpdate = (FloatingActionButton) getActivity().findViewById(R.id.btnUpdate);
        this.img = (ImageView) getActivity().findViewById(R.id.img);
        this.lstCurrency = (Spinner) getActivity().findViewById(R.id.lstCurrency);
        // this.lstTags = (TagTokenAutoCompleteView) getActivity().findViewById(R.id.lstTags);
        this.txtTitle = (EditText) getActivity().findViewById(R.id.txtTitle);
        this.txtDescription = (EditText) getActivity().findViewById(R.id.txtDescription);
        this.txtValue = (EditText) getActivity().findViewById(R.id.txtValue);

        this.btnUpdate.setOnClickListener(this);
        this.btnDelete.setOnClickListener(this);
        this.img.setOnClickListener(this);
        // this.lstTags.setLanguage(this.authUser.language);
        // this.lstTags.allowDuplicates(false);
        // this.lstTags.setAdapter(tagAdapter);

        this.populateCurrencies();
        this.populateTags();
        this.populateAsset();

        AuthenticationService authenticationService = new AuthenticationService(getContext());
        this.authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();




        //Define tags

            this.tags = (Tags) SerializeHelper.loadObject(this.getActivity(), Tags.TAGS_FILE_NAME);




        //Try to define asset object, which is passed from MyProductsFragment
        if (getActivity().getIntent().hasExtra("asset")) {
            this.asset = (Asset) getActivity().getIntent().getSerializableExtra("asset");
            this.tagAdapter = new TagAdapter(this.getActivity(), tags, this.asset.tags, this.authenticationUser.getLanguage());
        }

         assetService = new AssetService(this.getContext());
        ((PrivateActivity) getActivity()).hideKeyboard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset_update, container, false);
    }





    @Override
    public void onStart() {
        super.onStart();
        this.setVisibility();
    }

    private void setVisibility() {
        if (this.getResources().getBoolean(R.bool.transactions)) {
            this.txtValue.setVisibility(View.VISIBLE);
            this.lstCurrency.setVisibility(View.VISIBLE);
        } else {
            this.txtValue.setVisibility(View.GONE);
            this.lstCurrency.setVisibility(View.GONE);
        }
    }

    private void populateTags() {
        this.tagAdapter = new TagAdapter(this.getActivity(), this.tags, this.asset.tags, this.authenticationUser.getLanguage());
      //  this.lstTags.allowDuplicates(false);
      //  this.lstTags.setAdapter(tagAdapter);
       //this.lstTags.setTokenLimit(15);
    }

    private void populateCurrencies() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(adapter);
        this.lstCurrency.setSelection(adapter.getPosition(this.asset.getCurrency()));
    }

    public void populateAsset() {
        if (this.asset != null) {
            this.txtTitle.setText(this.asset.getName());
            this.txtDescription.setText(this.asset.getDescription());
            this.txtValue.setText(this.asset.getValueAsString());
            for (Tag t : this.asset.tags) {
               // this.lstTags.addObject(t);
            }
            try {
                this.img.setImageBitmap(asset.images.get(0).getBitmap());
            } catch (Exception e) {
            }
        }
    }

    /*  public void createImageListFragmentEvent(Image image) {
        this.asset.images.add(image);
        this.postImageCreate(image);
    }

    public void updateImageListFragmentEvent(Image image) {
        this.asset.images.set(image);
        this.postImageUpdate(image);
    }

    public void deleteImageListFragmentEvent(Image image) {
        this.asset.images.remove(image);
        this.postImageDelete(image);
    }

  private void postImageCreate(Image image) {
        PostJSON asyncTask = new PostJSON(this.getActivity());
    //    asyncTask.delegate = this;
        asyncTask.execute(getResources().getString(R.string.post_asset_create_image), image.toString(), authUser.getToken());
    }

    private void postImageUpdate(Image image) {
        PostJSON asyncTask = new PostJSON(this.getActivity());
        //asyncTask.delegate = this;
        asyncTask.execute(getResources().getString(R.string.post_asset_update_image), image.toString(), authUser.getToken());
    }

    private void postImageDelete(Image image) {
        PostJSON asyncTask = new PostJSON(this.getActivity());
        //asyncTask.delegate = this;
        asyncTask.execute(getResources().getString(R.string.post_asset_delete_image), image.toString(), authUser.getToken());
    }*/






    private void showDeleteDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.label_delete))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                      //  postAssetDelete();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onAssetRead(int code, String message, String assetId) {      }

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

    }

    @Override
    public void createImageListFragmentEvent(Image image) {

    }

    @Override
    public void updateImageListFragmentEvent(Image image) {

    }

    @Override
    public void deleteImageListFragmentEvent(Image image) {

    }
}
