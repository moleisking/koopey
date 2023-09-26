package com.koopey.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;
import com.koopey.R;
import com.koopey.model.Location;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.PositionService;
import com.koopey.service.TagService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class PublicActivity extends AppCompatActivity implements
        DrawerLayout.DrawerListener, PositionService.PositionListener, NavigationView.OnNavigationItemSelectedListener {

    public interface PublicActivityListener {
        void onLocationRequestSuccess(Location location);
    }

    private List<PublicActivityListener> publicActivityListeners = new ArrayList<>();

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private AppBarConfiguration appBarConfiguration;
    public AuthenticationService authenticationService;
    // public AuthenticationUser authenticationUser;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    public Tags tags;
    public TagService tagService;
    private NavController navigationController;

    protected void exit() {
        this.finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        Places.initialize(getApplicationContext(), getGoogleAPIKey());
        Places.createClient(this);

        drawerLayout = findViewById(R.id.drawer_layout_public);
        navigationView = findViewById(R.id.drawer_toggle);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);

        navigationController = Navigation.findNavController(this, R.id.fragment_public);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        appBarConfiguration =
                new AppBarConfiguration.Builder(
                        R.id.navigation_login, R.id.navigation_register, R.id.navigation_about)
                        .setOpenableLayout(drawerLayout)
                        .build();

        NavigationUI.setupWithNavController(toolbar, navigationController, appBarConfiguration);
        NavigationUI.setupActionBarWithNavController(this, navigationController, appBarConfiguration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        startPositionRequest();       

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navigationController = Navigation.findNavController(this, R.id.fragment_public);
        return NavigationUI.navigateUp(navigationController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer_public, menu);
        return true;
    }

    @Override
    public void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude) {
        Log.d(PublicActivity.class.getSimpleName(), latitude + " " + longitude);
        for (PublicActivityListener listener : publicActivityListeners) {
            listener.onLocationRequestSuccess(Location.builder()
                    .altitude(altitude)
                    .latitude(latitude)
                    .longitude(longitude).build());
        }
    }

    @Override
    public void onPositionRequestFail(String error) {
        Log.d(PublicActivity.class.getSimpleName(), error);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPositionRequestPermission() {
        Log.d(PublicActivity.class.getSimpleName(), "onPositionRequestPermission()");
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //not working
        Log.i(PublicActivity.class.getName(), item.getTitle().toString());
     /*   NavController navController = Navigation.findNavController(this, R.id.fragment_public);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);*/
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onClick(View v) {
        try {
            if (v.getId() == this.btnLogin.getId()) {
                this.onLoginClick(v);
            } else if (v.getId() == this.btnRegister.getId()) {
                this.onRegisterClick(v);
            }
        } catch (Exception ex) {
        }
    }



    protected void onLoginClick(View view) {
        // Reset errors text
        this.txtEmail.setError(null);
        this.txtPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = txtEmail.getText().toString().toLowerCase();
        String password = txtPassword.getText().toString();

        if (password == null || password.trim().equals("") || (password.length() < 4)) {
            // Check for a valid password, if the user entered one.
            this.txtPassword.setError(getString(R.string.error_invalid_password));
            this.txtPassword.requestFocus();
        } else if (email == null || email.trim().equals("")) {
            // Error email is empty
            this.txtEmail.setError(getString(R.string.error_field_required));
            this.txtEmail.requestFocus();
        } else if (!email.contains("@") && !email.contains(".")) {
            // Error text is not email
            this.txtEmail.setError(getString(R.string.error_invalid_email));
            this.txtEmail.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            showProgress(true);
            postAuthentication();
        }
    }

    protected void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }*/

    private void showPrivateActivity() {
        //Note* intent.putExtra("MyUser", myUser) creates TransactionTooLargeException
        Intent intent = new Intent(this, PrivateActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void showLoginFragment() {
       /* getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_login_frame, new LoginFragment())
                .addToBackStack("fragment_login")
                .commit();*/
    }

    public void showRegisterFragment() {
       /* getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_login_frame, new RegisterFragment())
                .addToBackStack("fragment_register")
                .commit();*/
    }

    public void showAboutFragment() {
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_login_frame, new AboutFragment())
                .addToBackStack("fragment_about")
                .commit();*/
    }

    public void hideKeyboard() {
        //getWindow().setSoftInputMode(
        //        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        //);
        View currentView = this.getCurrentFocus();
        if (currentView != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        drawerView.showContextMenu();
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        hideKeyboard();
        Log.i(PublicActivity.class.getName(), item.getTitle().toString());
        NavController navController = Navigation.findNavController(this, R.id.fragment_public);
        this.hideKeyboard();
        drawerLayout.closeDrawer(Gravity.LEFT);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);

    }

    public AuthenticationUser getAuthenticationUser() {
        authenticationService = new AuthenticationService(this);
        return authenticationService.getLocalAuthenticationUserFromFile();
    }

    public Tags getTags() {
        tagService = new TagService(this);
        tags = tagService.getLocalTagsFromFile();
        return tags;
    }

    public void setPublicActivityListener(PublicActivityListener publicActivityListener) {
        publicActivityListeners.add(publicActivityListener);
    }

    //Intent gpsService;
    public void startPositionRequest() {
        Log.i(PublicActivity.class.getSimpleName(), "startPositionRequest()");
        PositionService gpsService = new PositionService(this);
        gpsService.setPositionListeners(this);
        gpsService.startPositionRequest();
    }

    public static final int REQUEST_LOCATION = 198;

    private String getGoogleAPIKey() {
        String googleApiKey = "";
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            googleApiKey = bundle.getString("com.google.android.geo.API_KEY");
        } catch (Exception e) {
            Log.e(PublicActivity.class.getSimpleName(), "No googleApiKey found.");
        }
        return googleApiKey;
    }

}