package com.koopey.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;


import com.google.android.gms.maps.model.LatLng;

import com.koopey.R;
import com.koopey.helper.HashHelper;
import com.koopey.helper.ImageHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.GetJSON;
import com.koopey.controller.PostJSON;
import com.koopey.controller.GPSReceiver;
import com.koopey.model.Alert;
import com.koopey.model.Bitcoin;
import com.koopey.model.Ethereum;
import com.koopey.model.Image;
import com.koopey.model.Tags;

import com.koopey.model.User;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.view.PrivateActivity;


public class UserUpdateFragment extends Fragment implements
        GPSReceiver.OnGPSReceiverListener, PlaceSelectionListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private static final int DEFAULT_IMAGE_SIZE = 256;
    public static final int REQUEST_GALLERY_IMAGE = 197;
    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private Bitcoin bitcoin;
    private Ethereum ethereum;
    private final String LOG_HEADER = "USER:UPDATE";
    private final int USER_UPDATE_FRAGMENT = 102;
    private EditText  txtAddress , txtDescription, txtEducation, txtEmail, txtMobile, txtName ;
       private FloatingActionButton btnUpdate;
    private GPSReceiver gps;
    private ImageView imgAvatar;
    private AuthenticationUser authenticationUser;
    private  AutocompleteSupportFragment placeFragment;
    private PopupMenu imagePopupMenu;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((PrivateActivity) getActivity()).setTitle(getResources().getString(R.string.label_update));
        ((PrivateActivity) getActivity()).hideKeyboard();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Define place fragment
        try {
            this.placeFragment = (AutocompleteSupportFragment)
                    getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
            this.placeFragment.setOnPlaceSelectedListener(this);

           /* AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();
            placeFragment.setFilter(typeFilter);*/

           // this.txtAddress = ((EditText) placeFragment.getView().findViewById(R.id.place_autocomplete_search_input));
            this.txtAddress.setHint(R.string.label_address);
        } catch (Exception aex) {
            Log.d(LOG_HEADER + ":ER", aex.getMessage());
        }

        //Define views
        this.btnUpdate = (FloatingActionButton) getActivity().findViewById(R.id.btnUpdate);
        this.imgAvatar = (ImageView) getActivity().findViewById(R.id.imgUser);
        this.txtName = (EditText) getActivity().findViewById(R.id.txtName);
        this.txtEmail = (EditText) getActivity().findViewById(R.id.txtEmail);
        this.txtMobile = (EditText) getActivity().findViewById(R.id.txtMobile);
        this.txtEducation = (EditText) getActivity().findViewById(R.id.txtEducation);
        this.txtDescription = (EditText) getActivity().findViewById(R.id.txtDescription);

        //Set listeners
        this.btnUpdate.setOnClickListener(this);
        this.imgAvatar.setOnClickListener(this);

        //Populate controls
        this.populateUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_IMAGE) {
                this.imgAvatar.setImageBitmap(ImageHelper.onGalleryImageResult(data));
                this.authenticationUser.setAvatar(ImageHelper.BitmapToSmallUri(((BitmapDrawable) imgAvatar.getDrawable()).getBitmap()));
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == btnUpdate.getId()) {
                if (!txtName.getText().equals("")) {
                    this.authenticationUser.setName(txtName.getText().toString());
                }
                if (!txtEmail.getText().equals("")) {
                    authenticationUser.setEmail( txtEmail.getText().toString().toLowerCase());
                }
                if (!txtMobile.getText().equals("")) {
                    authenticationUser.setMobile(txtMobile.getText().toString());
                }
                if (!txtDescription.getText().equals("")) {
                    authenticationUser.setDescription( txtDescription.getText().toString());
                }
                if (!txtEducation.getText().equals("")) {
                    authenticationUser.setEducation( txtEducation.getText().toString());
                }

             //   this.postUserUpdate();
            } else if (v.getId() == imgAvatar.getId()) {
this.showImagePopupMenu(v);
            }
        } catch (Exception ex) {
            Log.d(LOG_HEADER + ":ER", ex.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define myUser
        this.authenticationUser = ((PrivateActivity) getActivity()).getAuthenticationUser();

        //Start GPS
        gps = new GPSReceiver(getActivity());
        gps.delegate = this;
        gps.Start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_user_update, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.placeFragment != null){
            getChildFragmentManager().beginTransaction().remove(placeFragment).commit();
        }
    }



    @Override
    public void onGPSConnectionResolutionRequest(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this.getActivity(), GPSReceiver.OnGPSReceiverListener.CONNECTION_FAILURE_RESOLUTION_REQUEST);
        } catch (Exception ex) {
            Log.d(LOG_HEADER + ":ER", ex.getMessage());
        }
    }

    @Override
    public void onGPSWarning(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGPSPositionResult(LatLng position) {
        try {
            this.authenticationUser.getLocation().setLatitude(  position.latitude);
            this.authenticationUser.getLocation().setLongitude(  position.longitude);
            gps.Stop();
        } catch (Exception ex) {
            Log.d(LOG_HEADER + ":ER", ex.getMessage());
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.nav_image_gallery:
                this.startGalleryRequest();
                return true;
            case R.id.nav_image_cancel:
                imagePopupMenu.dismiss();
                return true;*/
            default:
                return false;
        }
    }

    @Override
    public void onPlaceSelected(Place place) {
        this.authenticationUser.getLocation().setLatitude(  place.getLatLng().latitude);
        this.authenticationUser.getLocation().setLongitude(   place.getLatLng().longitude);
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    private void populateUser() {
        if (this.authenticationUser != null) {
            this.txtName.setText(authenticationUser.getName());
            this.txtEmail.setText(authenticationUser.getEmail());
            this.txtMobile.setText(authenticationUser.getMobile());
            this.txtDescription.setText(authenticationUser.getDescription());
            this.txtAddress.setText(authenticationUser.getLocation().getAddress());
            if (ImageHelper.isImageUri(authenticationUser.getAvatar())){
                this.imgAvatar.setImageBitmap(ImageHelper.UriToBitmap( authenticationUser.getAvatar()));
            }
        }
    }



    public void showImagePopupMenu(View v) {
        this.imagePopupMenu = new PopupMenu(this.getActivity(), v, Gravity.BOTTOM);
        this.imagePopupMenu.setOnMenuItemClickListener( this);
        this.imagePopupMenu.inflate(R.menu.menu_image);
        this.imagePopupMenu.show();
    }

    public void startGalleryRequest() {
        //Note* return-data = true to return a Bitmap, false to directly save the cropped image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", DEFAULT_IMAGE_SIZE);
        intent.putExtra("outputY", DEFAULT_IMAGE_SIZE);
        intent.putExtra("return-data", true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_IMAGE);
    }

    @Override
    public void onError(@NonNull Status status) {

    }


}