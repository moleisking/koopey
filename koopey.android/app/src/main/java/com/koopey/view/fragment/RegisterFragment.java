package com.koopey.view.fragment;


import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.helper.HashHelper;
import com.koopey.helper.ImageHelper;
import com.koopey.controller.GPSReceiver;

import com.koopey.model.Location;
import com.koopey.model.Wallet;
import com.koopey.model.authentication.LoginUser;
import com.koopey.model.authentication.RegisterUser;
import com.koopey.service.AuthenticationService;
import com.koopey.view.PublicActivity;
import com.koopey.view.component.PrivateFragment;

import java.net.HttpURLConnection;

/**
 * Created by Scott on 14/02/2017.
 */
public class RegisterFragment extends PrivateFragment implements AuthenticationService.RegisterListener, /*GPSReceiver.OnGPSReceiverListener,*/ PlaceSelectionListener,
        PopupMenu.OnMenuItemClickListener, View.OnClickListener {

  //  private AuthenticationService authenticationService;

    public static final int REQUEST_GALLERY_IMAGE = 197;
    private static final int DEFAULT_IMAGE_SIZE = 256;
    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private DatePicker txtBirthday;
    private EditText txtAddress, txtAlias, txtEmail, txtDescription, txtMobile, txtName, txtPassword;
    private FloatingActionButton btnCreate, btnLogin;
    private GPSReceiver gps;
    private ImageView imgAvatar;
    private RegisterUser registerUser ;
    private Spinner lstCurrency;
    private PopupMenu imagePopupMenu;
    private AutocompleteSupportFragment placeFragment;
    private boolean imageChanged = false;

    private Location location ;



    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {

      //  ((PublicActivity) getActivity()).hideKeyboard();
       // authenticationService = new AuthenticationService(this.getContext());
       // authenticationService.setOnRegisterListener(this);

        try {
            this.placeFragment = (AutocompleteSupportFragment)
                    getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
            this.placeFragment.setOnPlaceSelectedListener(this);

          /*  AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();
            this.placeFragment.setFilter(typeFilter);*/

            //this.txtAddress = ((EditText) placeFragment.getView().findViewById(R.id.place_autocomplete_fragment));
            this.txtAddress.setHint(R.string.label_address);
        } catch (Exception aex) {
            Log.d(RegisterFragment.class.getName(), aex.getMessage());
        }

        //Define controls
        this.btnCreate = (FloatingActionButton) getActivity().findViewById(R.id.btnCreate);
        this.btnLogin = (FloatingActionButton) getActivity().findViewById(R.id.btnLogin);
        this.imgAvatar = (ImageView) getActivity().findViewById(R.id.imgAvatar);
        this.txtAlias = (EditText) getActivity().findViewById(R.id.txtAlias);
        this.txtName = (EditText) getActivity().findViewById(R.id.txtName);
        this.txtEmail = (EditText) getActivity().findViewById(R.id.txtEmail);
        this.txtMobile = (EditText) getActivity().findViewById(R.id.txtMobile);
        this.txtPassword = (EditText) getActivity().findViewById(R.id.txtPassword);
        this.txtDescription = (EditText) getActivity().findViewById(R.id.txtDescription);
        this.txtBirthday = (DatePicker) getActivity().findViewById(R.id.txtBirthday);
        this.lstCurrency = (Spinner) getActivity().findViewById(R.id.lstCurrency);

        //Set listeners
        this.btnCreate.setOnClickListener(this);
        this.imgAvatar.setOnClickListener(this);

        //Populate controls
        this.txtBirthday.updateDate(1979, 1, 1);
        this.populateCurrencies();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_LOCATION) {
                // Toast.makeText(this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                this.imgAvatar.setImageBitmap(ImageHelper.onGalleryImageResult(data));
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_LOCATION) {
                Toast.makeText(this.getActivity(), "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                Toast.makeText(this.getActivity(), "Gallery upload cancelled.", Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start GPS
        gps = new GPSReceiver(this.getActivity());
        gps.delegate = this;
        gps.Start();

        //Check Permissions
        if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCreate.getId()) {
            this.registerUser.device = Settings.Secure.ANDROID_ID ;
            //current and registered locations are set in overload methods
            if (!this.txtAlias.getText().equals("")) {
                this.registerUser.alias = this.txtAlias.getText().toString();
            }
            if (!txtName.getText().equals("")) {
                this.registerUser.setName( this.txtName.getText().toString());
            }
            if (!txtPassword.getText().equals("")) {
                this.registerUser.setPassword(this.txtPassword.getText().toString());
            }
            if (!this.txtEmail.getText().equals("")) {
                this.registerUser.setEmail( this.txtEmail.getText().toString().toLowerCase());
            }
            if (!this.txtMobile.getText().equals("")) {
                this.registerUser.setMobile( this.txtMobile.getText().toString());
            }
            if (!this.txtDescription.getText().equals("")) {
                this.registerUser.setDescription( this.txtDescription.getText().toString());
            }

        //    this.authUser.birthday = new Date(txtBirthday.getYear(), txtBirthday.getMonth(), txtBirthday.getDayOfMonth()).getTime();
            //Create wallet
            Wallet wallet = new Wallet();
            wallet.value = Double.valueOf(getResources().getString(R.string.default_credit));
            wallet.type = "primary";
            wallet.currency = "tok";
            this.registerUser.getWallets().add(wallet);
            //Create hash

            //Post new data
           // if (this.authUser.isCreate() && imageChanged) {
                LoginUser loginUser = new LoginUser();
                loginUser.email = txtEmail.getText().toString().trim();
                loginUser.password = txtPassword.getText().toString().trim();
                authenticationService.register(registerUser);
           // } else {
                // txtError.setText(R.string.error_field_required);
           // }
        } else if (v.getId() == btnLogin.getId()) {
            this.showLoginActivity();
        } else if (v.getId() == imgAvatar.getId()) {
            this.showImagePopupMenu(imgAvatar);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.placeFragment != null) {
            getChildFragmentManager().beginTransaction().remove(placeFragment).commit();
        }
    }

    @Override
    public void onError(Status status) {
        Log.i(RegisterFragment.class.getName(), status.toString());
    }

    @Override
    public void onGPSConnectionResolutionRequest(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this.getActivity(), GPSReceiver.OnGPSReceiverListener.CONNECTION_FAILURE_RESOLUTION_REQUEST);
        } catch (Exception ex) {
            Log.d(RegisterFragment.class.getName(), ex.getMessage());
        }
    }
/*
    @Override
    public void onGPSWarning(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGPSPositionResult(LatLng position) {
        try {
            this.authUser.location.longitude = position.longitude;
            this.authUser.location.latitude = position.latitude;
            this.authUser.location.position = Location.convertLatLngToPosition(position.latitude, position.longitude);
            gps.Stop();
        } catch (Exception ex) {
            Log.d(RegisterFragment.class.getName(), ex.getMessage());
        }
    }*/

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.nav_image_gallery:
                this.startGalleryRequest(this.imgAvatar);
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
        this.registerUser.setLocation( Location.builder()
                .longitude(place.getLatLng().longitude)
                .latitude(place.getLatLng().latitude)
                .position(Location.convertLatLngToPosition(place.getLatLng().latitude, place.getLatLng().longitude)).build());
    }
    private void populateCurrencies() {
        this.currencyCodeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(registerUser.currency));
    }

    private void showLoginActivity() {
        Intent intent = new Intent(this.getActivity(), PublicActivity.class);
        startActivity(intent);
        this.getActivity().finish();
    }

    public void showImagePopupMenu(View v) {
        this.imagePopupMenu = new PopupMenu(this.getActivity(), v, Gravity.BOTTOM);
        imagePopupMenu.setOnMenuItemClickListener(this);
        imagePopupMenu.inflate(R.menu.menu_image);
        imagePopupMenu.show();
    }

    public void startGalleryRequest(View image) {
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
    public void onUserRegister(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK){
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }
}
