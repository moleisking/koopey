package com.koopey.view.fragment;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Assets;
import com.koopey.model.Location;
import com.koopey.model.Locations;
import com.koopey.model.Messages;
import com.koopey.model.Tags;
import com.koopey.model.Transactions;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AssetService;
import com.koopey.service.AuthenticationService;
import com.koopey.service.LocationService;
import com.koopey.service.MessageService;
import com.koopey.service.PositionService;
import com.koopey.service.TagService;
import com.koopey.service.TransactionService;
import com.koopey.service.UserService;
import com.koopey.view.MainActivity;

public class ConfigurationFragment extends PreferenceFragmentCompat
        implements AuthenticationService.UserReadListener, AssetService.AssetSearchSellerListener,
        LocationService.LocationSearchListener, MessageService.MessageSearchListener,
        SharedPreferences.OnSharedPreferenceChangeListener, PositionService.PositionListener,
        Preference.OnPreferenceClickListener, TagService.TagListener, TransactionService.TransactionSearchByBuyerOrSellerListener,
        UserService.UserConfigurationListener {

    private SharedPreferences sharedPreferences;
    private AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser;

    private PositionService positionService;

    private Preference buttonPreferenceAssets, buttonPreferenceLocations, buttonPreferenceMessages, buttonPreferenceTags,
            buttonPreferenceTransactions, buttonPreferenceUser, dialogTermsAndConditions, dialogPrivacyPolicyAndDataProtection, dialogVersion;

    private CheckBoxPreference checkBoxPreferenceTrack, checkBoxPreferenceNotificationByEmail, checkBoxPreferenceNotificationByDevice;

    private ListPreference listPreferenceLanguage, listPreferenceMeasure, listPreferenceCurrency;

    private AssetService assetService;

    private UserService userService;

    private TagService tagService;

    private LocationService locationService;

    private MessageService messageService;

    private TransactionService transactionService;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
        assetService = new AssetService(getContext());
        locationService = new LocationService(getContext());
        messageService = new MessageService(getContext());
        positionService = new PositionService(getContext());
        positionService.setPositionListeners(this);
        userService = new UserService(getContext());
        tagService = new TagService(getContext());
        transactionService = new TransactionService(getContext());

        setPreferencesFromResource(R.xml.configuration, rootKey);

        buttonPreferenceAssets = findPreference("assets");
        buttonPreferenceLocations = findPreference("locations");
        buttonPreferenceMessages = findPreference("messages");
        buttonPreferenceTags = findPreference("tags");
        buttonPreferenceTransactions = findPreference("transactions");
        buttonPreferenceUser = findPreference("user");

        checkBoxPreferenceTrack = findPreference("track");
        checkBoxPreferenceNotificationByDevice = findPreference("notificationByDevice");
        checkBoxPreferenceNotificationByEmail = findPreference("notificationByEmail");
        dialogPrivacyPolicyAndDataProtection = findPreference("privacyPolicyAndDataProtection");
        dialogTermsAndConditions = findPreference("termsAndConditions");
        dialogVersion = findPreference("version");
        listPreferenceCurrency = findPreference("currency");
        listPreferenceLanguage = findPreference("language");
        listPreferenceMeasure = findPreference("measure");

        checkBoxPreferenceTrack.setChecked(authenticationUser.isTrack());
        checkBoxPreferenceNotificationByDevice.setChecked(authenticationUser.isNotifyByDevice());
        checkBoxPreferenceNotificationByEmail.setChecked(authenticationUser.isNotifyByEmail());
        listPreferenceCurrency.setValue(authenticationUser.getCurrency());
        listPreferenceLanguage.setValue(authenticationUser.getLanguage());
        listPreferenceMeasure.setValue(authenticationUser.getMeasure());

        assetService.setOnAssetSearchSellerListener(this);
        buttonPreferenceAssets.setOnPreferenceClickListener(this);
        buttonPreferenceLocations.setOnPreferenceClickListener(this);
        buttonPreferenceMessages.setOnPreferenceClickListener(this);
        buttonPreferenceTags.setOnPreferenceClickListener(this);
        buttonPreferenceTransactions.setOnPreferenceClickListener(this);
        buttonPreferenceUser.setOnPreferenceClickListener(this);
        dialogPrivacyPolicyAndDataProtection.setOnPreferenceClickListener(this);
        dialogTermsAndConditions.setOnPreferenceClickListener(this);
        locationService.setLocationSearchListeners(this);
        messageService.setOnMessageSearchListener(this);
        tagService.setOnTagSearchListener(this);
        transactionService.setOnTransactionSearchByBuyerOrSellerListener(this);
        authenticationService.setOnUserReadListener(this);

        //parentContext = this.getActivity();
        sharedPreferences = getPreferenceScreen().getSharedPreferences();

        try {
            PackageInfo packageInfo = this.getActivity().getPackageManager().getPackageInfo(this.getActivity().getPackageName(), 0);
            String version = packageInfo.versionName;
            dialogVersion.setSummary(version);
        } catch (Exception ex) {
            Log.d(ConfigurationFragment.class.getName(), ex.getMessage());
        }
    }

    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        if (preference.getKey().equals("about")) {
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_configuration);
            Log.d(ConfigurationFragment.class.getSimpleName(), "about");
            return true;
        } else if (preference.getKey().equals("changePassword")) {
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_password_change);
            Log.d(ConfigurationFragment.class.getSimpleName(), "changePassword");
            return true;
        } else if (preference.getKey().equals("exit")) {
            ((MainActivity) getActivity()).exit();
            Log.d(ConfigurationFragment.class.getSimpleName(), "exit");
            return true;
        } else if (preference.getKey().equals("privacyPolicyAndDataProtection")) {
            onPrivacyPolicyAndDataProtection();
            Log.d(ConfigurationFragment.class.getSimpleName(), "privacyPolicyAndDataProtection");
            return true;
        } else if (preference.getKey().equals("termsAndConditions")) {
            onTermsAndConditions();
            Log.d(ConfigurationFragment.class.getSimpleName(), "termsAndConditions");
            return true;
        } else if (preference.getKey().equals("user")) {
            authenticationService.read(authenticationUser);
            Log.d(ConfigurationFragment.class.getSimpleName(), "user");
            return true;
        } else if (preference.getKey().equals("assets")) {
            assetService.searchAssetsByBuyerOrSeller();
            Log.d(ConfigurationFragment.class.getSimpleName(), "assets");
            return true;
        } else if (preference.getKey().equals("transactions")) {
            transactionService.searchTransactionByBuyerOrSeller();
            Log.d(ConfigurationFragment.class.getSimpleName(), "transactions");
            return true;
        } else if (preference.getKey().equals("locations")) {
            locationService.searchLocationBySellerAndSource();
            Log.d(ConfigurationFragment.class.getSimpleName(), "locations");
            return true;
        } else if (preference.getKey().equals("tags")) {
            tagService.searchTags();
            Log.d(ConfigurationFragment.class.getSimpleName(), "tags");
            return true;
        } else if (preference.getKey().equals("messages")) {
            messageService.searchMessagesByReceiverOrSender();
            Log.d(ConfigurationFragment.class.getSimpleName(), "messages");
            return true;
        }
        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("currency")) {
            userService.updateCurrency(listPreferenceCurrency.getValue());
            Log.d(ConfigurationFragment.class.getSimpleName(), "currency:" + listPreferenceCurrency.getValue());
        } else if (key.equals("language")) {
            userService.updateLanguage(listPreferenceLanguage.getValue());
            Log.d(ConfigurationFragment.class.getSimpleName(), "language:" + listPreferenceLanguage.getValue());
        } else if (key.equals("logout")) {
            authenticationService.logout();
            Log.d(ConfigurationFragment.class.getSimpleName(), "logout");
        } else if (key.equals("measure")) {
            userService.updateMeasure(listPreferenceMeasure.getValue());
            Log.d(ConfigurationFragment.class.getSimpleName(), "measure:" + listPreferenceMeasure.getValue());
        } else if (key.equals("notificationByDevice")) {
            userService.updateNotifyByDevice(checkBoxPreferenceNotificationByDevice.isChecked());
            Log.d(ConfigurationFragment.class.getSimpleName(), "notificationByDevice");
        } else if (key.equals("notificationByEmail")) {
            userService.updateNotifyByEmail(checkBoxPreferenceNotificationByEmail.isChecked());
            Log.d(ConfigurationFragment.class.getSimpleName(), "notificationByEmail");
        } else if (key.equals("track")) {
            userService.updateTrack(checkBoxPreferenceTrack.isChecked());
            Log.d(ConfigurationFragment.class.getSimpleName(), "track");
        }
    }



   /*protected void onSaveClick()
    {
        Log.d("SettingFragment","onSaveClick");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete your stored user account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete local file
                        Toast.makeText(parentContext , "Yes clicked", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Toast.makeText(parentContext , "No clicked", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }*/


    protected void onPrivacyPolicyAndDataProtection() {
        Log.d(ConfigurationFragment.class.getSimpleName(), "onPrivacyPolicyAndDataProtection()");
        String text = "";
        try {
            String fileName = getResources().getString(R.string.privacy_policy_and_data_protection);
            InputStream is = this.getActivity().getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            text = new String(buffer);
        } catch (IOException e) {
            Log.d(ConfigurationFragment.class.getSimpleName(), "File not found");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text)
                .setPositiveButton("Okay", (dialog, id) -> {
                    userService.updateTrack(true);
                })
                .show();
    }

    protected void onTermsAndConditions() {
        Log.d(ConfigurationFragment.class.getSimpleName(), "onTermsAndConditions");
        String text = "";
        try {
            String fileName = getResources().getString(R.string.terms_and_conditions);
            InputStream is = this.getActivity().getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            text = new String(buffer);
        } catch (IOException e) {
            Log.d(ConfigurationFragment.class.getSimpleName(), "File not found");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text)
                .setPositiveButton("Okay", (dialog, id) -> {
                    userService.updateTerm(true);
                })
                .show();
    }

   /* public void setNavigationProfile() {
        //Set Navigation Profile
        View headerLayout = ((NavigationView) this.getActivity().findViewById(R.id.drawer_layout_public)).getHeaderView(0);
        ImageView imgAvatar = (ImageView) headerLayout.findViewById(R.id.nav_head_imgAvatar);
        TextView txtAliasOrName = (TextView) headerLayout.findViewById(R.id.nav_head_txtAliasOrName);
        TextView txtDescription = (TextView) headerLayout.findViewById(R.id.nav_head_txtDescription);
        imgAvatar.setImageBitmap(ImageHelper.IconBitmap(authenticationUser.getAvatar()));
        txtAliasOrName.setText(authenticationUser.getName());
        txtDescription.setText(authenticationUser.getDescription());
    }*/

    @Override
    public void onUserAvailable(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserCurrency(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserLanguage(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserLocation(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserTerm(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserTrack(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserNotifyByDevice(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUserNotifyByEmail(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude) {
        userService.updateLocation(altitude, latitude, longitude);
    }

    @Override
    public void onPositionRequestFail(String errorMessage) {
        Toast.makeText(this.getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPositionRequestPermission() {

    }

    @Override
    public void onTagSearch(int code, String message,Tags tags) {
        if (code==HttpURLConnection.HTTP_OK) {
            SerializeHelper.saveObject(getContext(), tags);
            Toast.makeText(this.getActivity(), "Tags " + tags.size(), Toast.LENGTH_LONG).show();
        } else if (code==HttpURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAssetsBySeller(int code, String message,Assets assets) {
        if (code==HttpURLConnection.HTTP_OK) {
            assets.setType(Assets.MY_ASSETS_FILE_NAME);
            SerializeHelper.saveObject(getContext(), assets);
            Toast.makeText(this.getActivity(), "Assets " + assets.size(), Toast.LENGTH_LONG).show();
        } else if (code==HttpURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onLocationSearchByBuyerAndDestination(Locations locations) {

    }

    @Override
    public void onLocationSearchByBuyerAndSource(Locations locations) {

    }

    @Override
    public void onLocationSearchByDestinationAndSeller(Locations locations) {

    }

    @Override
    public void onLocationSearchBySellerAndSource(Locations locations) {
        locations.setType(Locations.LOCATIONS_FILE_NAME);
        SerializeHelper.saveObject(getContext(), locations);
        Toast.makeText(this.getActivity(), "Locations " + locations.size(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationSearch(Locations locations) {

    }

    @Override
    public void onLocationSearchByGeocode(Location location) {

    }

    @Override
    public void onLocationSearchByPlace(Location location) {

    }

    @Override
    public void onLocationSearchByRangeInKilometers(Locations locations) {

    }

    @Override
    public void onLocationSearchByRangeInMiles(Locations locations) {

    }


    @Override
    public void onUserRead(int code, String message, AuthenticationUser authenticationUser) {
        SerializeHelper.saveObject(getContext(), authenticationUser);
        Toast.makeText(this.getActivity(), "AuthenticationUser", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onMessageSearchByReceiverOrSender(int code, String message, Messages messages) {
        if (code==HttpURLConnection.HTTP_OK) {
            SerializeHelper.saveObject(getContext(), messages);
            Toast.makeText(this.getActivity(), "messages", Toast.LENGTH_LONG).show();
        } else if (code==HttpURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMessageSearch(int code, String message, Messages messages) {

    }

    @Override
    public void onTransactionSearchByBuyer(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearchByBuyerOrSeller(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearchBySeller(int code, String message, Transactions transactions) {

    }
}
