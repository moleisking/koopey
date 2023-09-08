package com.koopey.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;


import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.koopey.R;
import com.koopey.helper.ImageHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.GPSReceiver;
import com.koopey.controller.GetJSON;
import com.koopey.controller.PostJSON;

import com.koopey.model.Alert;
import com.koopey.model.AuthUser;
import com.koopey.model.Image;
import com.koopey.model.Tag;
import com.koopey.model.Tags;

import com.koopey.model.Wallet;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

//http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
//http://blog.teamtreehouse.com/beginners-guide-location-android

//https://stackoverflow.com/questions/55290786/how-to-add-material-design-chips-to-input-field-using-autocomplete-in-android

public class RegisterActivity extends AppCompatActivity implements GetJSON.GetResponseListener, PostJSON.PostResponseListener, GPSReceiver.OnGPSReceiverListener, PlaceSelectionListener, View.OnClickListener {

    private static final int PERMISSION_REQUEST = 1003;
    private static final int DEFAULT_IMAGE_SIZE = 256;
    public static final int REQUEST_GALLERY_IMAGE = 197;
    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private FloatingActionButton btnCreate, btnLogin;
    private EditText txtAlias, txtName, txtPassword, txtEmail, txtDescription, txtMobile;
    private ImageView imgAvatar;
    private MultiAutoCompleteTextView lstTags;
    private DatePicker txtBirthday;
    private Spinner lstCurrency;
    //private PendingResult<LocationSettingsResult> mLocationSettingRequestResult;
    // private String selectedImagePath;
    private static final String LOG_HEADER = "USR:REG";
    private static final int REQUEST_LOCATION = 199;
    private static final int REQUEST_PLACE = 198;
    private Tags tags = new Tags();
    private GPSReceiver gps;


    private AuthUser authUser = new AuthUser();

    private boolean imageChanged = false;

    @TargetApi(23)
    private boolean hasPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((this.checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(INTERNET) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                return true;
            } else {
                return false;
            }
       /* } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                return true;
            } else {
                return false;
            }*/
        } else {
            return false;
        }
    }

    @TargetApi(23)
    private void requestPermissions() {
        this.requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, INTERNET,
                READ_EXTERNAL_STORAGE, READ_PHONE_STATE, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_register);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Register has own FloatingActionButton as is own activity
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnCreate);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterClick(view);
            }
        });*/

        //Start GPS
        gps = new GPSReceiver(this);
        gps.delegate = this;
        gps.Start();

        //Define tags
        if (SerializeHelper.hasFile(this, tags.TAGS_FILE_NAME)) {
            tags = (Tags) SerializeHelper.loadObject(this, Tags.TAGS_FILE_NAME);
        } else {
            Log.w(LOG_HEADER + "TAGS:ERR", "Tags not found");
            this.getTagsFromHTTPCall();
        }
//Define fragments
        try {
            AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
            autocompleteFragment.setOnPlaceSelectedListener(this);
           /* AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();
            autocompleteFragment.setFilter(typeFilter);*/
            EditText txtAddress = ((EditText) autocompleteFragment.getView().findViewById(R.id.btnLogin));
            txtAddress.setHint(R.string.label_address);
        } catch (Exception aex) {
            Log.d(LOG_HEADER + ":ER", aex.getMessage());
        }


        //Define Controls
        btnCreate = (FloatingActionButton) findViewById(R.id.btnCreate);
        btnLogin = (FloatingActionButton) findViewById(R.id.btnLogin);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        lstCurrency = (Spinner) findViewById(R.id.lstCurrency);
       // lstTags = (MultiAutoCompleteTextView) findViewById(R.id.lstTags);
        txtAlias = (EditText) findViewById(R.id.txtAlias);
        txtName = (EditText) findViewById(R.id.txtName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtMobile = (EditText) findViewById(R.id.txtMobile);
        txtBirthday = (DatePicker) findViewById(R.id.txtBirthday);
        txtBirthday.updateDate(1979, 1, 1);
        //Set listeners
        btnCreate.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //Test
        isNetworkConnected();

        //Request permissions
        if (!this.hasPermissions()) {
            this.requestPermissions();
        }


        //Scott added
Tag t1 = new Tag();
        t1.en = "one";

        Tag t2 = new Tag();
        t1.en = "two";
        List<Tag> contacts = new ArrayList<Tag>() {{
            add(t1);
            add(t2);
        }};
//https://stackoverflow.com/questions/55290786/how-to-add-material-design-chips-to-input-field-using-autocomplete-in-android
      /*  lstTags.setAdapter(new TagAdapter(this,
                R.layout.fragment_tag_create, contacts));
        lstTags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
// Minimum number of characters the user has to type before the drop-down list is shown
        lstTags.setThreshold(1);
        lstTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tag selectedContact = (Tag) adapterView.getItemAtPosition(i);
                createRecipientChip(selectedContact);
            }
        });*/

        //Check Permissions
       /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }*/
        //Populate controls
        this.populateCurrencies();
        // this.populateTags();

        //Set visibility
        this.setVisibility();
    }

    private void createRecipientChip(Tag selectedContact) {
     /*   ChipDrawable chip = ChipDrawable.createFromResource(this, R.xml.standalone_chip);
        CenteredImageSpan span = new CenteredImageSpan(chip, 40f, 40f);
        int cursorPosition = contactAutoCompleteTextView.getSelectionStart();
        int spanLength = selectedContact.getName().length() + 2;
        Editable text = contactAutoCompleteTextView.getText();
        chip.setChipIcon(ContextCompat.getDrawable(RegisterActivity.this,
                selectedContact.getAvatarResource()));
        chip.setText(selectedContact.getName());
        chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
        text.setSpan(span, cursorPosition - spanLength, cursorPosition, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);*/
    }

    @Override
    public void onPlaceSelected(Place place) {
        //Sets registered address from google place
        this.authUser.location.latitude = place.getLatLng().latitude;
        this.authUser.location.longitude = place.getLatLng().longitude;
    }

    @Override
    public void onError(Status status) {
        Log.i(LOG_HEADER + ":LOC:ERR", status.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_LOCATION) {
                // Toast.makeText(this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                this.imgAvatar.setImageBitmap(ImageHelper.onGalleryImageResult(data));
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_LOCATION) {
                Toast.makeText(this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                Toast.makeText(this, "Gallery upload cancelled.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == this.btnCreate.getId()) {
                this.onRegisterClick();
            } else if (v.getId() == this.btnLogin.getId()) {
                this.showLoginActivity();
            } else if (v.getId() == this.imgAvatar.getId()) {
                this.startGalleryRequest(this.imgAvatar);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onGPSConnectionResolutionRequest(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this, GPSReceiver.OnGPSReceiverListener.CONNECTION_FAILURE_RESOLUTION_REQUEST);
        } catch (Exception ex) {
            Log.d("REG:onGPSConnectionFail", ex.getMessage());
        }
    }

    @Override
    public void onGPSWarning(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGPSPositionResult(LatLng position) {
        this.authUser.location.latitude = position.latitude;
        this.authUser.location.longitude = position.longitude;
        gps.Stop();
        Log.d("Register:GPSPosRes", position.toString());
    }

    @Override
    public void onGetResponse(String output) {
        try {
            String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("tags")) {
                Tags tags = new Tags();
                tags.parseJSON(output);
                SerializeHelper.saveObject(this, tags);
            } else if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isError()) {
                    Toast.makeText(this, getResources().getString(R.string.error_create), Toast.LENGTH_SHORT).show();
                } else if (alert.isSuccess()) {
                    Toast.makeText(this, getResources().getString(R.string.info_create), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Log.w(LOG_HEADER + ":ER", ex.getMessage());
        }
    }

    @Override
    public void onPostResponse(String output) {
        try {
            String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isError()) {
                    Toast.makeText(this, getResources().getString(R.string.error_create), Toast.LENGTH_SHORT).show();
                } else if (alert.isSuccess()) {
                    Toast.makeText(this, getResources().getString(R.string.info_create), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Log.w(LOG_HEADER + ":ER", ex.getMessage());
        }
    }

    private void populateCurrencies() {
        this.currencyCodeAdapter = ArrayAdapter.createFromResource(this,
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter = ArrayAdapter.createFromResource(this,
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(authUser.currency));
    }

    /*private void populateTags() {
        TagAdapter tagAdapter = new TagAdapter(this, tags, authUser.tags, this.getString(R.string.default_language));
        lstTags.setAdapter(tagAdapter);
        lstTags.allowDuplicates(false);
    }*/

    private void setVisibility() {
        if (this.getResources().getBoolean(R.bool.transactions)) {
            lstCurrency.setVisibility(View.VISIBLE);
        } else {
            lstCurrency.setVisibility(View.GONE);
        }
    }

    public void getTagsFromHTTPCall() {
        GetJSON asyncTask = new GetJSON(this);
        asyncTask.delegate = this;
        asyncTask.execute(this.getString(R.string.get_tags_read), "", "");
    }

    public boolean isNetworkConnected() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.d("isNetworkConnected", "NETWORK UNAVAILABLE");
            showNoNetworkAlert();
            return false;
        } else if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("isNetworkConnected", "GPS UNAVAILABLE");
            showNoGpsAlert();
            return false;
        } else {
            return true;
        }
    }

    private void showNoGpsAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void showNoNetworkAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Network seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onRegisterClick() {
        //Note: Create location is done in overload method
        this.setDevice();
        if (!txtAlias.getText().equals("")) {
            authUser.alias = txtAlias.getText().toString();
        }
        if (!txtName.getText().equals("")) {
            authUser.name = txtName.getText().toString();
        }
        if (!txtPassword.getText().equals("")) {
            authUser.password = txtPassword.getText().toString();
        }
        if (!txtEmail.getText().equals("")) {
            authUser.email = txtEmail.getText().toString().toLowerCase();
        }
        if (!txtMobile.getText().equals("")) {
            authUser.mobile = txtMobile.getText().toString();
        }
        if (!txtDescription.getText().equals("")) {
            authUser.description = txtDescription.getText().toString();
        }
        if (imageChanged == true) {
            Image image = new Image();
            image.setUri(((BitmapDrawable) imgAvatar.getDrawable()).getBitmap());
            authUser.avatar = ImageHelper.UriToSmallUri(image.uri);
        }
       /* if (authUser.tags.compareTo(lstTags.getSelectedTags()) != 0) {
            authUser.tags.setTagList(lstTags.getObjects());
        }*/
        authUser.birthday = new Date(txtBirthday.getYear(), txtBirthday.getMonth(), txtBirthday.getDayOfMonth()).getTime();

        //Create wallet
        Wallet wallet = new Wallet();
        wallet.value = Double.valueOf(getResources().getString(R.string.default_credit));
        wallet.type = "primary";
        wallet.currency = "tok";
        authUser.wallets.add(wallet);
        //Post new data
        Log.d("JSON", authUser.toString());
        if (authUser.isCreate() && imageChanged) {
            postUserCreate();
        } else {
            Toast.makeText(this, R.string.error_field_required, Toast.LENGTH_LONG).show();
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                imageChanged = true;
                bm = data.getExtras().getParcelable("data");
                // bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (Exception e) {
                Log.d(LOG_HEADER + ":ON:IMG:RS", e.getMessage());
                e.printStackTrace();
            }
        }
        //String temp = Utility.buildImageUri(bm);//this
        imgAvatar.setImageBitmap(bm);
    }

    /*public String buildImageUri(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.NO_WRAP );
    }*/


    private void postUserCreate() {
        PostJSON asyncTask = new PostJSON(this);
        asyncTask.delegate = this;
        asyncTask.execute(getResources().getString(R.string.post_user_create), authUser.toString(), "");
    }

    private void showLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void setDevice() {
         if (this.authUser.device == null || this.authUser.device.equals("")) {
            this.authUser.device = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else  {
            this.authUser.device = "0000000000";
        }
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
}
