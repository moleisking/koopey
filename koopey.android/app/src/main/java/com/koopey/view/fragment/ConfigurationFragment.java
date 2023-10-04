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
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.google.android.material.navigation.NavigationView;
import com.koopey.R;
import com.koopey.helper.ImageHelper;

import com.koopey.model.Assets;
import com.koopey.model.Messages;

import com.koopey.model.Tags;
import com.koopey.model.Transactions;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.PositionService;
import com.koopey.service.UserService;
import com.koopey.view.MainActivity;

public class ConfigurationFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener, UserService.UserConfigurationListener {

    private SharedPreferences sharedPreferences;
    private AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser;
    private Context parentContext;
    private LatLng currentLatLng;
    private PositionService positionService;

    private CheckBoxPreference checkBoxPreferenceTrack , checkBoxPreferenceNotificationByEmail,checkBoxPreferenceNotificationByDevice;

    private ListPreference listPreferenceLanguage , listPreferenceMeasure , listPreferenceCurrency ;

    private UserService userService;



    //Refresh
    private Preference prefRefreshMyUser;
    private Preference prefRefreshMyProducts;
    private Preference prefRefreshMyTransactions;
    private Preference prefRefreshMessages;
    private Preference prefRefreshTags;
    private Preference prefRefreshLatLng;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
        userService = new UserService(getContext());
        positionService = new PositionService(getContext());

        setPreferencesFromResource(R.xml.configuration, rootKey);


        checkBoxPreferenceTrack = findPreference("track");
        checkBoxPreferenceNotificationByDevice = findPreference("notificationByDevice");
        checkBoxPreferenceNotificationByEmail = findPreference("notificationByEmail");
        listPreferenceCurrency = findPreference("currency");
        listPreferenceLanguage = findPreference("language");
        listPreferenceMeasure = findPreference("measure");

        checkBoxPreferenceTrack.setChecked(authenticationUser.isTrack());
        checkBoxPreferenceNotificationByDevice.setChecked(authenticationUser.isNotifyByDevice());
        checkBoxPreferenceNotificationByEmail.setChecked(authenticationUser.isNotifyByEmail());
        listPreferenceCurrency.setValue(authenticationUser.getCurrency());
        listPreferenceLanguage.setValue(authenticationUser.getLanguage());
        listPreferenceMeasure.setValue(authenticationUser.getMeasure());


        //Initialize objects
        parentContext = this.getActivity();
        sharedPreferences = getPreferenceScreen().getSharedPreferences();




        try {
            //Set build version value
            PackageInfo packageInfo = this.getActivity().getPackageManager().getPackageInfo(this.getActivity().getPackageName(), 0);
            String version = packageInfo.versionName;
            Preference pref = findPreference("preference_build_version");
            pref.setSummary(version);

            //Set action listeners
            //Notifications
           /* prefNotificationEmail = findPreference("preference_notification_email");
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
            });*/
            //Default
           /* prefDefaultDistanceUnit = findPreference("preference_default_distance_unit");
            prefDefaultDistanceUnit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onDefaultDistanceUnit();
                    return true;
                }
            });*/

           /* prefDefaultCurrency = findPreference("preference_default_currency");
            prefDefaultCurrency.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onDefaultCurrency();
                    return true;
                }
            });*/
            //Account

           /* prefMyUserDelete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    onMyUserDelete();
                    return true;
                }
            });*/
           /* pref*MyUserPasswordChange = findPreference("preference_my_user_password_change");
            prefMyUserPasswordChange.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //   onPasswordChange();
                    return true;
                }
            });*/
            //Synchronize section
            prefRefreshMyUser = findPreference("preference_refresh_my_user");
            prefRefreshMyUser.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //  onMyUserRefresh();
                    return true;
                }
            });
            prefRefreshMyProducts = findPreference("preference_refresh_my_products");
            prefRefreshMyProducts.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    // onRefreshMyProducts();
                    return true;
                }
            });
            prefRefreshMyTransactions = findPreference("preference_refresh_my_transactions");
            prefRefreshMyTransactions.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //  onRefreshMyTransactions();
                    return true;
                }
            });
            prefRefreshMessages = findPreference("preference_refresh_messages");
            prefRefreshMessages.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //  onRefreshMessages();
                    return true;
                }
            });
            prefRefreshTags = findPreference("preference_refresh_tags");
            prefRefreshTags.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //   onRefreshTags();
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
                    ((MainActivity) getActivity()).exit();
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
                    ((MainActivity) getActivity()).showAboutFragment();
                    return true;
                }
            });
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("exit")) {
            ((MainActivity) getActivity()).exit();
            Log.d(ConfigurationFragment.class.getSimpleName(), "exit");
        } else if (key.equals("changePassword")) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.toolbar_private, new PasswordUpdateFragment())
                    .addToBackStack("configurations")
                    .commit();
            Log.d(ConfigurationFragment.class.getSimpleName(), "changePassword");
        } else if (key.equals("currency")) {
            userService.updateUserCurrency(listPreferenceCurrency.getValue());
            Log.d(ConfigurationFragment.class.getSimpleName(), "currency:" + listPreferenceCurrency.getValue());
        } else if (key.equals("language")) {
            userService.updateUserLanguage(listPreferenceLanguage.getValue());
            Log.d(ConfigurationFragment.class.getSimpleName(), "language:" + listPreferenceLanguage.getValue());
        } else if (key.equals("logout")) {
            authenticationService.logout();
            Log.d(ConfigurationFragment.class.getSimpleName(), "logout");
        } else if (key.equals("measure")) {
            userService.updateUserMeasure(listPreferenceMeasure.getValue());
            Log.d(ConfigurationFragment.class.getSimpleName(), "measure:" + listPreferenceMeasure.getValue());
        } else if (key.equals("notificationByDevice")) {
            userService.updateUserNotifyByDevice(checkBoxPreferenceNotificationByDevice.isChecked());
            Log.d(ConfigurationFragment.class.getSimpleName(), "notificationByDevice");
        } else if (key.equals("notificationByEmail")) {
            userService.updateUserNotifyByEmail(checkBoxPreferenceNotificationByEmail.isChecked());
            Log.d(ConfigurationFragment.class.getSimpleName(), "notificationByEmail");
        } else if (key.equals("track")) {
            userService.updateUserTrack(checkBoxPreferenceTrack.isChecked());
            Log.d(ConfigurationFragment.class.getSimpleName(), "track");
        }
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

/*    protected void onNotificationEmail() {
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
    }*/


    protected void onDefaultCurrency() {
        final String currency[] = new String[]{"usd", "eur", "gbp"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose the default currency unit?")
                .setItems(currency, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        authenticationUser.setCurrency(currency[which]);
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

            // Read the entire location into a local byte buffer.
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

            // Read the entire location into a local byte buffer.
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
        View headerLayout = ((NavigationView) this.getActivity().findViewById(R.id.drawer_layout_public)).getHeaderView(0);
        ImageView imgAvatar = (ImageView) headerLayout.findViewById(R.id.nav_head_imgAvatar);
        TextView txtAliasOrName = (TextView) headerLayout.findViewById(R.id.nav_head_txtAliasOrName);
        TextView txtDescription = (TextView) headerLayout.findViewById(R.id.nav_head_txtDescription);
        imgAvatar.setImageBitmap(ImageHelper.IconBitmap(authenticationUser.getAvatar()));
        txtAliasOrName.setText(authenticationUser.getName());
        txtDescription.setText(authenticationUser.getDescription());
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
}
