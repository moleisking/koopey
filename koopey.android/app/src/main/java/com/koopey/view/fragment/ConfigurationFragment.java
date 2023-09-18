package com.koopey.view.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;


//import android.support.v4.app.Fragment;
//import android.support.v4.preference.PreferenceFragment;
//import android.support.v7.preference.PreferenceFragmentCompat;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;

import com.google.android.material.navigation.NavigationView;
import com.koopey.R;
import com.koopey.helper.ImageHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.GPSReceiver;
import com.koopey.controller.GetJSON;
import com.koopey.controller.PostJSON;
import com.koopey.model.Alert;
import com.koopey.model.Messages;

import com.koopey.model.Assets;
import com.koopey.model.Tags;
import com.koopey.model.Transactions;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.view.PrivateActivity;

public class ConfigurationFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener, GPSReceiver.OnGPSReceiverListener {

    private SharedPreferences sharedPreferences;
    AuthenticationService authenticationService;
    AuthenticationUser authenticationUser;
    private Context parentContext;
    private LatLng currentLatLng;
    private GPSReceiver gps;

    //Notifications
    private Preference prefNotificationEmail;
    private Preference prefNotificationScreen;

    //Default
    private Preference prefDefaultDistanceUnit;
    private Preference prefDefaultCurrency;

    //Account
    private Preference prefMyUserDelete;
    private Preference prefMyUserPasswordChange;

    //Refresh
    private Preference prefRefreshMyUser;
    private Preference prefRefreshMyProducts;
    private Preference prefRefreshMyTransactions;
    private Preference prefRefreshMessages;
    private Preference prefRefreshTags;
    private Preference prefRefreshLatLng;
    private Preference prefExit;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        super.onCreate(savedInstanceState);

        authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();

        addPreferencesFromResource(R.xml.preference_setting);

        //Set PrivateActivity visible and invisible items
        getActivity().setTitle(getResources().getString(R.string.label_configuration));

        //Initialize objects
        parentContext = this.getActivity();
        sharedPreferences = getPreferenceScreen().getSharedPreferences();


        //Start GPS sensor
        currentLatLng = new LatLng(0.0d, 0.0d);
        gps = new GPSReceiver(getActivity());
        gps.delegate = this;
        gps.Start();


        try {
            //Set build version value
            PackageInfo packageInfo = this.getActivity().getPackageManager().getPackageInfo(this.getActivity().getPackageName(), 0);
            String version = packageInfo.versionName;
            Preference pref = findPreference("preference_build_version");
            pref.setSummary(version);

            //Set action listeners
            //Notifications
            prefNotificationEmail = findPreference("preference_notification_email");
            prefNotificationEmail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onNotificationEmail();
                    return true;
                }
            });
            prefNotificationScreen = findPreference("preference_notification_screen");
            prefNotificationScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onNotificationScreen();
                    return true;
                }
            });
            //Default
            prefDefaultDistanceUnit = findPreference("preference_default_distance_unit");
            prefDefaultDistanceUnit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onDefaultDistanceUnit();
                    return true;
                }
            });

            prefDefaultCurrency = findPreference("preference_default_currency");
            prefDefaultCurrency.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onDefaultCurrency();
                    return true;
                }
            });
            //Account
            prefMyUserDelete = findPreference("preference_my_user_delete");
            prefMyUserDelete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onMyUserDelete();
                    return true;
                }
            });
            prefMyUserPasswordChange = findPreference("preference_my_user_password_change");
            prefMyUserPasswordChange.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onPasswordChange();
                    return true;
                }
            });
            //Synchronize section
            prefRefreshMyUser = findPreference("preference_refresh_my_user");
            prefRefreshMyUser.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onMyUserRefresh();
                    return true;
                }
            });
            prefRefreshMyProducts = findPreference("preference_refresh_my_products");
            prefRefreshMyProducts.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onRefreshMyProducts();
                    return true;
                }
            });
            prefRefreshMyTransactions = findPreference("preference_refresh_my_transactions");
            prefRefreshMyTransactions.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onRefreshMyTransactions();
                    return true;
                }
            });
            prefRefreshMessages = findPreference("preference_refresh_messages");
            prefRefreshMessages.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onRefreshMessages();
                    return true;
                }
            });
            prefRefreshTags = findPreference("preference_refresh_tags");
            prefRefreshTags.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onRefreshTags();
                    return true;
                }
            });
            prefRefreshLatLng = findPreference("preference_refresh_location");
            prefRefreshLatLng.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onRefreshCurrentLatLng();
                    return true;
                }
            });
            //Troubleshoot
            Preference prefExit = findPreference("preference_exit");
            prefExit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    ((PrivateActivity) getActivity()).exit();
                    return true;
                }
            });


            //About section
            Preference prefTermsAndConditions = findPreference("preference_terms_and_conditions");
            prefTermsAndConditions.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onTermsAndConditions();
                    return true;
                }
            });

            Preference prefPrivacyPolicyAndDataProtection = findPreference("preference_privacy_policy_and_data_protection");
            prefPrivacyPolicyAndDataProtection.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onPrivacyPolicyAndDataProtection();
                    return true;
                }
            });

            Preference prefAboutUs = findPreference("preference_about_us");
            prefAboutUs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    ((PrivateActivity) getActivity()).showAboutFragment();
                    return true;
                }
            });
        } catch (Exception ex) {
            Log.d(ConfigurationFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onGPSConnectionResolutionRequest(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this.getActivity(), GPSReceiver.OnGPSReceiverListener.CONNECTION_FAILURE_RESOLUTION_REQUEST);
        } catch (Exception ex) {
            Log.d("Account:onGPSConFail", ex.getMessage());
        }
    }

    @Override
    public void onGPSWarning(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGPSPositionResult(LatLng position) {
        try {
            this.currentLatLng = position;
            Log.d("Search:GPSPosRes", position.toString());
            gps.Stop();
        } catch (Exception ex) {
            Log.d("Search:GPSPosRes:Err", ex.getMessage());
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //Fired when actual value changes
        if (key.equals("pref_item_privacy_policy_and_data_protection")) {
            Log.d("onSharedPrefChange", "pref_item_privacy_policy_and_data_protection");
        } else if (key.equals("pref_item_terms_and_conditions")) {
            Log.d("onSharedPrefChange", "pref_item_terms_and_conditions");
        } else if (key.equals("pref_item_email_notification")) {
            Log.d("onSharedPrefChange", "pref_item_terms_and_conditions");
        } else if (key.equals("pref_item_screen_notification")) {
            Log.d("onSharedPrefChange", "pref_item_screen_notification");
        } else if (key.equals("pref_item_distance_unit")) {
            Log.d("onSharedPrefChange", "pref_item_distance_unit");
        }

       /* Log.d("PreferenceChanged",key);
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText());
        }*/
    }

    /*private void onDefaultResultClick(View v)
    {
        // Is the button now checked?
        int iselected = ((RadioGroup) v).getCheckedRadioButtonId();
        View checked = v.findViewById(iselected);

        // Check which radio button was clicked
        //if (checked.getId() ==  R.id.radMap)
        //{
        //        //ShowMessage("Save Map option");
        //}
        //else if (checked.getId() ==  R.id.radList)
        //{
        //    //ShowMessage("Save List option");
        //}
    }*/

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

    protected void onNotificationEmail() {
        if (prefNotificationEmail.isEnabled()) {
            sharedPreferences.edit().putString("preference_notification_email", "true");
            sharedPreferences.edit().apply();
        } else {
            sharedPreferences.edit().putString("preference_notification_email", "false");
            sharedPreferences.edit().apply();
        }
    }

    protected void onNotificationScreen() {
        if (prefNotificationScreen.isEnabled()) {
            sharedPreferences.edit().putString("preference_notification_screen", "true");
            sharedPreferences.edit().apply();
        } else {
            sharedPreferences.edit().putString("preference_notification_screen", "false");
            sharedPreferences.edit().apply();
        }
    }

    protected void onMyUserDelete() {
        //Note*Also deletes my products
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete your stored account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete local file
                        ((PrivateActivity) getActivity()).deleteFile(Messages.MESSAGES_FILE_NAME);
                        ((PrivateActivity) getActivity()).deleteFile(AuthenticationUser.AUTH_USER_FILE_NAME);
                        ((PrivateActivity) getActivity()).deleteFile(Assets.MY_ASSETS_FILE_NAME);
                        ((PrivateActivity) getActivity()).deleteFile(Assets.ASSET_SEARCH_RESULTS_FILE_NAME);
                        ((PrivateActivity) getActivity()).deleteFile(Assets.ASSET_WATCH_LIST_FILE_NAME);
                        ((PrivateActivity) getActivity()).deleteFile(Tags.TAGS_FILE_NAME);
                        ((PrivateActivity) getActivity()).deleteFile(Transactions.TRANSACTIONS_FILE_NAME);
                        Toast.makeText(parentContext, "Your stored user account has been deleted", Toast.LENGTH_LONG).show();
                        ((PrivateActivity) getActivity()).showLoginActivity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .show();
    }

    protected void onDefaultCurrency() {
        final String currency[] = new String[]{"usd", "eur", "gbp"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose the default currency unit?")
                .setItems(currency, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        authenticationUser.currency = currency[which];
                        sharedPreferences.edit().putString("default_currency", currency[which]);
                    }
                })
                .show();
        sharedPreferences.edit().commit();
    }

    protected void onDefaultDistanceUnit() {
        final String distance[] = new String[]{"mi", "km"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose the default distance unit?")
                .setItems(distance, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences.edit().putString("default_distance_unit", distance[which]);
                    }
                })
                .show();
        sharedPreferences.edit().commit();
    }


    protected void onRefreshCurrentLatLng() {
        Log.d("Setting:Position", currentLatLng.toString());
    }





    /*protected void onNotificationClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rad1km:
                if (checked)
                    break;
            case R.id.rad5km:
                if (checked)
                    break;
        }
    }*/

    protected void onPrivacyPolicyAndDataProtection() {
        Log.d("Setting:onTerm&Con", "Started");
        String text = "";
        try {
            String fileName = getResources().getString(R.string.file_privacy_policy_and_data_protection);
            InputStream is = this.getActivity().getAssets().open(fileName);

            // Read the entire asset into a local byte buffer.
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            text = new String(buffer);
            Log.d("Setting:onPriv&Pol", text);
        } catch (IOException e) {
            // Should never happen!
            Log.d("Setting:onPriv&Pol", "File not found");
            throw new RuntimeException(e);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(parentContext , "Stored Account Deleted", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    protected void onTermsAndConditions() {
        Log.d("Setting:onTerm&Con", "Started");
        String text = "";
        try {
            String fileName = getResources().getString(R.string.file_terms_and_conditions);
            InputStream is = this.getActivity().getAssets().open(fileName);

            // Read the entire asset into a local byte buffer.
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            text = new String(buffer);
            Log.d("Setting:onTerm&Con", text);
        } catch (IOException e) {
            // Should never happen!
            Log.d("Setting:onTerm&Con", "File not found");
            throw new RuntimeException(e);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(parentContext , "Stored Account Deleted", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void setNavigationProfile() {
        //Set Navigation Profile
        View headerLayout = ((NavigationView) this.getActivity().findViewById(R.id.drawer_layout_private)).getHeaderView(0);
        ImageView imgAvatar = (ImageView) headerLayout.findViewById(R.id.nav_head_imgAvatar);
        TextView txtAliasOrName = (TextView) headerLayout.findViewById(R.id.nav_head_txtAliasOrName);
        TextView txtDescription = (TextView) headerLayout.findViewById(R.id.nav_head_txtDescription);
        imgAvatar.setImageBitmap(ImageHelper.IconBitmap(authenticationUser.avatar));
        txtAliasOrName.setText(authenticationUser.name);
        txtDescription.setText(authenticationUser.description);
    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        switch (preference.getKey()) {
            case "preference_notification_email": {
                Log.d(ConfigurationFragment.class.getName(), "preference_notification_email");
                break;
            }
            case "preference_notification_screen": {
                Log.d(ConfigurationFragment.class.getName(), "preference_notification_screen");
                break;
            }
        }

        return false;
    }
}
