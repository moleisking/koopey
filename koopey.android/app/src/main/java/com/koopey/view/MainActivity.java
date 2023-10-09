package com.koopey.view;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.navigation.NavigationView;
import com.koopey.R;
import com.koopey.helper.ImageHelper;
import com.koopey.model.Images;
import com.koopey.model.Location;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.PositionService;
import com.koopey.service.TagService;
import com.koopey.view.fragment.ConfigurationFragment;
import com.koopey.view.fragment.ConversationListFragment;
import com.koopey.view.fragment.ImageListFragment;
import com.koopey.view.fragment.LocationListFragment;
import com.koopey.view.fragment.LocationViewFragment;
import com.koopey.view.fragment.MessageListFragment;
import com.koopey.view.fragment.TransactionListFragment;
import com.koopey.view.fragment.UserViewFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        DrawerLayout.DrawerListener, PositionService.PositionListener, NavigationView.OnNavigationItemSelectedListener {

    public interface PublicActivityListener {
        void onLocationRequestSuccess(Location location);
    }

    private List<PublicActivityListener> publicActivityListeners = new ArrayList<>();

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private AppBarConfiguration appBarConfiguration;
    private AuthenticationService authenticationService;
    // public AuthenticationUser authenticationUser;
    private DrawerLayout drawerLayout;
    private ImageButton headerAvatar;
    private NavigationView navigationView;
    private Toolbar toolbar;
    public Tags tags;
    public TagService tagService;
   private TextView headerName, headerSummary;
    private NavController navigationController;

    public void exit() {
        this.finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        authenticationService = new AuthenticationService(this);
        Places.initialize(getApplicationContext(), getGoogleAPIKey());
        Places.createClient(this);

        drawerLayout = findViewById(R.id.drawer_layout_public);
        navigationView = findViewById(R.id.drawer_toggle);
        if (authenticationService.hasAuthenticationUserFile()) {
            navigationView.inflateMenu(R.menu.menu_drawer_private);
        } else {
            navigationView.inflateMenu(R.menu.menu_drawer_public);
        }
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar_public);
        setSupportActionBar(toolbar);

        navigationController = Navigation.findNavController(this, R.id.fragment_public);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

         headerAvatar = navigationView.getHeaderView(0).findViewById(R.id.headerAvatar);
        headerName = navigationView.getHeaderView(0).findViewById(R.id.headerName);
        headerSummary = navigationView.getHeaderView(0).findViewById(R.id.headerSummary);
        if (authenticationService.hasAuthenticationUserFile()) {
            AuthenticationUser authenticationUser = getAuthenticationUser();
            appBarConfiguration =
                    new AppBarConfiguration.Builder(
                            R.id.navigation_about, R.id.navigation_asset_search, R.id.navigation_assets,
                            R.id.navigation_configuration, R.id.navigation_conversations,
                            R.id.navigation_dashboard, R.id.navigation_messages, R.id.navigation_my_assets,
                            R.id.navigation_transaction_search, R.id.navigation_transactions)
                            .setOpenableLayout(drawerLayout)
                            .build();
            if (!authenticationUser.isEmptyAvatar()) {
                headerAvatar.setBackgroundColor(Color.TRANSPARENT);
                headerAvatar.setImageBitmap(ImageHelper.UriToBitmap(authenticationUser.getAvatar()));
            }
            headerName.setText(authenticationUser.getName());
            headerSummary.setText(authenticationUser.getDescription());
        } else {
            appBarConfiguration =
                    new AppBarConfiguration.Builder(
                            R.id.navigation_about, R.id.navigation_login, R.id.navigation_register)
                            .setOpenableLayout(drawerLayout)
                            .build();
            headerSummary.setVisibility(View.GONE);
        }

        NavigationUI.setupWithNavController(toolbar, navigationController, appBarConfiguration);
        NavigationUI.setupActionBarWithNavController(this, navigationController, appBarConfiguration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        startPositionRequest();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (authenticationService.hasAuthenticationUserFile()) {
            getMenuInflater().inflate(R.menu.menu_popup_private, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_popup_public, menu);
        }
        return true;
    }

    @Override
    public void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude) {
       /* Log.d(MainActivity.class.getSimpleName(), latitude + " " + longitude);
        location = Location.builder()
                .altitude(altitude)
                .latitude(latitude)
                .longitude(longitude).build();
        for (PublicActivityListener listener : publicActivityListeners) {
            listener.onLocationRequestSuccess(location);
        }*/
    }

    @Override
    public void onPositionRequestFail(String error) {
        Log.d(MainActivity.class.getSimpleName(), error);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPositionRequestPermission() {
        Log.d(MainActivity.class.getSimpleName(), "onPositionRequestPermission()");
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemContact) {
            showEmail();
        } else if (item.getItemId() == R.id.itemShare) {
            showWhatsApp();
        } else if (item.getItemId() == R.id.itemConfiguration) {
            Navigation.findNavController(this, R.id.fragment_public).navigate(R.id.navigation_configuration);
        } else if (item.getItemId() == R.id.itemContact) {
            showEmail();
        } else if (item.getItemId() == R.id.itemLogout) {
            authenticationService.logout();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else if (item.getItemId() == R.id.itemRefresh) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.toolbar_private);
            if (fragment != null) {
                if (fragment instanceof LocationViewFragment) {
                    //  ((LocationViewFragment) fragment).populateLocation();
                } else if (fragment instanceof ConversationListFragment) {
                    ((ConversationListFragment) fragment).syncConversations();
                } else if (fragment instanceof MessageListFragment) {
                    ((MessageListFragment) fragment).syncConversation();
                } else if (fragment instanceof LocationListFragment) {
                    // ((LocationListFragment) fragment).syncLocations();
                } else if (fragment instanceof TransactionListFragment) {
                    ((TransactionListFragment) fragment).populateTransactions();
                } else if (fragment instanceof UserViewFragment) {
                    ((UserViewFragment) fragment).populateUser();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navigationController = Navigation.findNavController(this, R.id.fragment_public);
        return NavigationUI.navigateUp(navigationController, appBarConfiguration) || super.onSupportNavigateUp();
    }



    public void showLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void showMyLocationListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new LocationListFragment())
                .addToBackStack("fragment_my_locations")
                .commit();
    }

    private void showEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, R.string.smtp_address);
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.smtp_subject);
        intent.putExtra(Intent.EXTRA_TEXT, R.string.smtp_content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

    private void showWhatsApp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        try {
            this.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT);
        }
    }

    public void showMessageListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new MessageListFragment())
                .addToBackStack("fragment_messages")
                .commit();
    }

    public void showImageListFragment(Images images) {
        this.getIntent().putExtra("images", images);
        this.getIntent().putExtra("showCreateButton", true);
        this.getIntent().putExtra("showUpdateButton", true);
        this.getIntent().putExtra("showDeleteButton", true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new ImageListFragment())
                .addToBackStack("fragment_images")
                .commit();
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
        Log.i(MainActivity.class.getName(), item.getTitle().toString());
        NavController navController = Navigation.findNavController(this, R.id.fragment_public);
        this.hideKeyboard();
        drawerLayout.closeDrawer(Gravity.LEFT);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);

    }

    public AuthenticationUser getAuthenticationUser() {
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
        Log.i(MainActivity.class.getSimpleName(), "startPositionRequest()");
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
            Log.e(MainActivity.class.getSimpleName(), "No googleApiKey found.");
        }
        return googleApiKey;
    }

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
        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static final int PERMISSION_REQUEST = 1004;

    @TargetApi(23)
    public void requestPermissions() {
        this.requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, INTERNET,
                READ_EXTERNAL_STORAGE, READ_PHONE_STATE, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
    }


}