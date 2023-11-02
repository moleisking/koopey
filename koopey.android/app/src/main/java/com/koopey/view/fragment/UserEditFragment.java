package com.koopey.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.provider.Settings;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.koopey.R;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.DateTimeHelper;
import com.koopey.helper.ImageHelper;

import com.koopey.model.Bitcoin;
import com.koopey.model.Ethereum;

import com.koopey.model.Location;
import com.koopey.model.User;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.type.ImageType;
import com.koopey.service.AuthenticationService;
import com.koopey.service.GalleryService;
import com.koopey.service.PositionService;
import com.koopey.service.UserService;
import com.koopey.view.MainActivity;

import java.net.HttpURLConnection;
import java.util.Date;


public class UserEditFragment extends Fragment implements GalleryService.GalleryListener,
         PositionService.PositionListener, AuthenticationService.UserSaveListener, View.OnClickListener {

    private AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser;
    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private EditText  txtAddress , txtDescription, txtEducation, txtEmail, txtMobile, txtName ;
       private FloatingActionButton btnSave;
    private GalleryService galleryService;
    private ImageView imgAvatar;
    private PositionService positionService;
    private Spinner lstCurrency;
    private User user;

    private boolean checkForm() {
        if (user.getAvatar() == null || user.getAvatar().isBlank()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_avatar) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtDescription.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_description) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtEducation.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_education) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtEmail.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_email) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtName.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_name) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtMobile.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_mobile) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(UserEditFragment.class.getSimpleName(), "onSave");
            if (v.getId() == btnSave.getId() && checkForm()) {
                user.setName(txtName.getText().toString());
                user.setMobile(txtMobile.getText().toString());
                user.setDescription(txtDescription.getText().toString());
                user.setCurrency(CurrencyHelper.currencySymbolToCode(lstCurrency.getSelectedItem().toString()));
                authenticationService = new AuthenticationService(getActivity());
                authenticationService.update(user);
            } else if (v.getId() == imgAvatar.getId()) {
                galleryService.selectImage("edit_user_image");
            }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        galleryService = new GalleryService(requireActivity().getActivityResultRegistry(), getActivity());
        positionService = new PositionService(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.user_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSave = (FloatingActionButton) getActivity().findViewById(R.id.btnUpdate);
        imgAvatar = (ImageView) getActivity().findViewById(R.id.imgUser);
        txtName = (EditText) getActivity().findViewById(R.id.txtName);
        txtEmail = (EditText) getActivity().findViewById(R.id.txtEmail);
        txtMobile = (EditText) getActivity().findViewById(R.id.txtMobile);
        txtEducation = (EditText) getActivity().findViewById(R.id.txtEducation);
        txtDescription = (EditText) getActivity().findViewById(R.id.txtDescription);

        btnSave.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
        getLifecycle().addObserver(galleryService);
        galleryService.setGalleryListener(this);

        positionService.setPositionListeners(this);
        positionService.startPositionRequest();

        populateCurrencies();
        populateUser();
    }

    private void populateCurrencies() {
        currencyCodeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        currencySymbolAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(user.getCurrency()));
    }

    private void populateUser() {
        if (this.authenticationUser != null) {
            this.txtName.setText(authenticationUser.getName());
            this.txtEmail.setText(authenticationUser.getEmail());
            this.txtMobile.setText(authenticationUser.getMobile());
            this.txtDescription.setText(authenticationUser.getDescription());
            this.txtAddress.setText(authenticationUser.getLocation().getDescription());
            if (ImageHelper.isImageUri(authenticationUser.getAvatar())){
                this.imgAvatar.setImageBitmap(ImageHelper.UriToBitmap( authenticationUser.getAvatar()));
            }
        }
    }

    @Override
    public void onImageLoadFromGallery(Bitmap bitmap, String imageType) {
        Log.d(UserEditFragment.class.getSimpleName() + ".onImageLoadFromGallery()", "");
        imgAvatar.setImageBitmap(bitmap);
        user.setAvatar(ImageHelper.BitmapToSmallUri(bitmap));
    }

    @Override
    public void onImageGalleryError(String error) {
        Log.d(UserEditFragment.class.getSimpleName() + ".onImageLoadFromGallery()", error);
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude) {
        user.setLocation(Location.builder().altitude(altitude).latitude(latitude).longitude(longitude).build());
    }

    @Override
    public void onPositionRequestFail(String errorMessage) {
        Log.d(UserEditFragment.class.getSimpleName() + ".onPositionRequestFail()", errorMessage);
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPositionRequestPermission() {
        Log.d(UserEditFragment.class.getSimpleName() + ".onPositionRequestPermission()", "");
        Toast.makeText(getActivity(), getResources().getString(R.string.error_permission), Toast.LENGTH_LONG).show();
        ((MainActivity) getActivity()).requestPermissions();
    }


    @Override
    public void onUserUpdate(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }
}