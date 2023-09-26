package com.koopey.view.fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.helper.ImageHelper;
import com.koopey.model.type.CurrencyType;
import com.koopey.R;
import com.koopey.model.Location;
import com.koopey.model.Wallet;
import com.koopey.model.authentication.RegisterUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.GalleryService;
import com.koopey.service.PositionService;
import com.koopey.view.PublicActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Date;

public class RegisterFragment extends Fragment implements AuthenticationService.RegisterListener, PlaceSelectionListener,
        PositionService.PositionListener, View.OnClickListener, GalleryService.GalleryListener {

    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private DatePicker txtBirthday;
    private EditText txtAddress, txtAlias, txtEmail, txtDescription, txtEducation, txtMobile, txtName, txtPassword;
    private FloatingActionButton btnRegister;
    private GalleryService galleryService;
    private ImageView imgAvatar;
    private RegisterUser registerUser;
    private Spinner lstCurrency;
    private AutocompleteSupportFragment placeFragment;

    private boolean checkForm() {
        if (!this.txtAlias.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_alias + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (!txtName.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_name + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (!txtPassword.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_password + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (!this.txtEmail.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_email + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (!this.txtMobile.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_mobile + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (!this.txtDescription.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_description + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (!this.txtEducation.getText().equals("")) {
            Toast.makeText(this.getActivity(), R.string.label_education + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else if (this.txtBirthday.getYear() >= 1900 && this.txtBirthday.getMonth() > 0 && this.txtBirthday.getDayOfMonth() > 0) {
            Toast.makeText(this.getActivity(), R.string.label_birthday + ". " + R.string.error_field_required, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerUser = new RegisterUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onClick(View v) {
        Log.i(RegisterFragment.class.getSimpleName(), "onRegister");
        if (v.getId() == btnRegister.getId() && checkForm()) {
            this.registerUser.setDevice(Settings.Secure.ANDROID_ID);
            this.registerUser.setAlias(this.txtAlias.getText().toString());
            this.registerUser.setName(this.txtName.getText().toString());
            this.registerUser.setPassword(this.txtPassword.getText().toString());
            this.registerUser.setEmail(this.txtEmail.getText().toString().toLowerCase());
            this.registerUser.setMobile(this.txtMobile.getText().toString());
            this.registerUser.setDescription(this.txtDescription.getText().toString());
            this.registerUser.setDescription(this.txtDescription.getText().toString());
            this.registerUser.setBirthday(new Date(this.txtBirthday.getYear(), this.txtBirthday.getMonth(), this.txtBirthday.getDayOfMonth()));
            this.registerUser.setCurrency(lstCurrency.getSelectedItem().toString());

            Wallet wallet = Wallet.builder()
                    .currency(CurrencyType.TOK)
                    .type("primary")
                    .value(Double.valueOf(getResources().getString(R.string.default_credit))).build();
            this.registerUser.getWallets().add(wallet);

            ((PublicActivity) getActivity()).authenticationService.register(registerUser);

        } else if (v.getId() == imgAvatar.getId()) {
            galleryService.selectImage();
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
    public void onPlaceSelected(Place place) {
        this.registerUser.setLocation(Location.builder()
                .longitude(place.getLatLng().longitude)
                .latitude(place.getLatLng().latitude)
                .address(place.getAddress()).build());

        this.txtAddress.setText(place.getAddress());
    }

    @Override
    public void onUserRegister(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {

        PositionService positionService = new PositionService(this.getActivity());
        positionService.setPositionListeners(this);

        galleryService = new GalleryService(requireActivity().getActivityResultRegistry(), this.getActivity());
        getLifecycle().addObserver(galleryService);

        imgAvatar = (ImageView) getActivity().findViewById(R.id.imgAvatar);
        txtAddress = (EditText) getActivity().findViewById(R.id.txtAddress);
        txtAlias = (EditText) getActivity().findViewById(R.id.txtAlias);
        txtName = (EditText) getActivity().findViewById(R.id.txtName);
        txtEmail = (EditText) getActivity().findViewById(R.id.txtEmail);
        txtMobile = (EditText) getActivity().findViewById(R.id.txtMobile);
        txtPassword = (EditText) getActivity().findViewById(R.id.txtPassword);
        txtDescription = (EditText) getActivity().findViewById(R.id.txtDescription);
        txtEducation = (EditText) getActivity().findViewById(R.id.txtEducation);
        txtBirthday = (DatePicker) getActivity().findViewById(R.id.txtBirthday);
        lstCurrency = (Spinner) getActivity().findViewById(R.id.lstCurrency);
        btnRegister = (FloatingActionButton) getActivity().findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
        galleryService.setGalleryListener(this);
        imgAvatar.setOnClickListener(this);
        populateCurrencies();

        try {
            this.placeFragment = (AutocompleteSupportFragment) getChildFragmentManager()
                    .findFragmentById(R.id.fragmentPlace);
            placeFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG));
            this.placeFragment.setOnPlaceSelectedListener(this);
        } catch (Exception aex) {
            Log.d(RegisterFragment.class.getSimpleName(), aex.getMessage());
        }

    }

    private void populateAddress() {
     /*   AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        this.placeFragment.setFilter(typeFilter);*/

    }

    private void populateCurrencies() {
        this.currencyCodeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(registerUser.getCurrency()));
    }

    @Override
    public void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude) {
        registerUser.setLocation(Location.builder().altitude(altitude).latitude(latitude).longitude(longitude).build());
    }

    @Override
    public void onPositionRequestFail(String errorMessage) {

    }

    @Override
    public void onPositionRequestPermission() {

    }

    @Override
    public void onImageLoadFromGallery(Bitmap bitmap) {
        Log.d(RegisterFragment.class.getSimpleName() + ".onImageLoadFromGallery()", "");
        imgAvatar.setImageBitmap(bitmap);
        registerUser.setAvatar(ImageHelper.BitmapToSmallUri(bitmap));
    }

    @Override
    public void onImageGalleryError(String error) {
        Log.d(RegisterFragment.class.getSimpleName() + ".onImageLoadFromGallery()", error);
        Toast.makeText(this.getActivity(), error, Toast.LENGTH_LONG).show();
    }


}
