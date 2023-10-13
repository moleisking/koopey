package com.koopey.view.fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.DateTimeHelper;
import com.koopey.helper.ImageHelper;
import com.koopey.model.type.CurrencyType;
import com.koopey.R;
import com.koopey.model.Location;
import com.koopey.model.Wallet;
import com.koopey.model.authentication.RegisterUser;
import com.koopey.model.type.ImageType;
import com.koopey.service.AuthenticationService;
import com.koopey.service.GalleryService;
import com.koopey.service.PositionService;
import com.koopey.view.MainActivity;

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
import java.util.Calendar;
import java.util.Date;

public class RegisterFragment extends Fragment implements AuthenticationService.RegisterListener,
        GalleryService.GalleryListener, PositionService.PositionListener, View.OnClickListener {

    private AuthenticationService authenticationService;
    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private DatePicker txtBirthday;
    private EditText txtAddress, txtAlias, txtEmail, txtDescription, txtEducation, txtMobile, txtName, txtPassword;
    private FloatingActionButton btnRegister;
    private GalleryService galleryService;
    private ImageView imgAvatar;
    private PositionService positionService;
    private RegisterUser registerUser;
    private Spinner lstCurrency;


    private boolean checkForm() {
        if (registerUser.getAvatar() == null || registerUser.getAvatar().isBlank()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_avatar) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtAlias.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_alias) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtBirthday.getYear() <= 1900 || this.txtBirthday.getMonth() < 0 || this.txtBirthday.getDayOfMonth() < 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_birthday) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtBirthday.getYear() < (new Date().getYear() - 16)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_birthday) + ". " +
                    getResources().getString(R.string.error_under_age), Toast.LENGTH_LONG).show();
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
        } else if (txtPassword.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_password) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(RegisterFragment.class.getSimpleName(), "onRegister");
        if (v.getId() == btnRegister.getId() && checkForm()) {
            registerUser.setAlias(txtAlias.getText().toString());
            registerUser.setDevice(Settings.Secure.ANDROID_ID);
            registerUser.setName(txtName.getText().toString());
            registerUser.setPassword(txtPassword.getText().toString());
            registerUser.setEmail(txtEmail.getText().toString().toLowerCase());
            registerUser.setMobile(txtMobile.getText().toString());
            registerUser.setDescription(txtDescription.getText().toString());
            registerUser.setBirthday(DateTimeHelper.dateToEpoch(txtBirthday.getYear(), txtBirthday.getMonth(), txtBirthday.getDayOfMonth()));
            registerUser.setCurrency(CurrencyHelper.currencySymbolToCode(lstCurrency.getSelectedItem().toString()));
            authenticationService = new AuthenticationService(getActivity());
            authenticationService.register(registerUser);
        } else if (v.getId() == imgAvatar.getId()) {
            galleryService.selectImage(ImageType.USER_REGISTER);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerUser = new RegisterUser();

        galleryService = new GalleryService(requireActivity().getActivityResultRegistry(), getActivity());
        positionService = new PositionService(getActivity());

        Wallet wallet = Wallet.builder()
                .currency(CurrencyType.TOK)
                .type("primary")
                .value(Double.valueOf(getResources().getString(R.string.default_credit))).build();
        registerUser.getWallets().add(wallet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    public void onImageLoadFromGallery(Bitmap bitmap, String imageType) {
        Log.d(RegisterFragment.class.getSimpleName() + ".onImageLoadFromGallery()", "");
        imgAvatar.setImageBitmap(bitmap);
        registerUser.setAvatar(ImageHelper.BitmapToSmallUri(bitmap));
    }

    @Override
    public void onImageGalleryError(String error) {
        Log.d(RegisterFragment.class.getSimpleName() + ".onImageLoadFromGallery()", error);
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude) {
        registerUser.setLocation(Location.builder().altitude(altitude).latitude(latitude).longitude(longitude).build());
    }

    @Override
    public void onPositionRequestFail(String errorMessage) {
        Log.d(RegisterFragment.class.getSimpleName() + ".onPositionRequestFail()", errorMessage);
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPositionRequestPermission() {
        Log.d(RegisterFragment.class.getSimpleName() + ".onPositionRequestPermission()", "");
        Toast.makeText(getActivity(), getResources().getString(R.string.error_permission), Toast.LENGTH_LONG).show();
        ((MainActivity) getActivity()).requestPermissions();
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

        getLifecycle().addObserver(galleryService);
        btnRegister.setOnClickListener(this);
        galleryService.setGalleryListener(this);
        imgAvatar.setOnClickListener(this);
        positionService.setPositionListeners(this);
        positionService.startPositionRequest();
        populateCurrencies();

        txtAddress.setText("my address");
        txtAlias.setText("martin");
        txtName.setText("martin");
        txtEmail.setText("martin@koopey.com");
        txtMobile.setText("555 12345");
        txtPassword.setText("12345");
        txtDescription.setText("my description");
        txtEducation.setText("Bsc MBA");
        txtBirthday.init(1990, 12, 12, null);
    }

    private void populateCurrencies() {
        currencyCodeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        currencySymbolAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(registerUser.getCurrency()));
    }

}
